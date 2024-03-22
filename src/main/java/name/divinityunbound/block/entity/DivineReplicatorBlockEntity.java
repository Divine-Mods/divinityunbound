package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.custom.DivineReplicatorBlock;
import name.divinityunbound.block.custom.KnowledgeExtractorBlock;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.item.custom.WandOfCapturingItem;
import name.divinityunbound.recipe.GenerationStationRecipe;
import name.divinityunbound.screen.DivineReplicatorScreenHandler;
import name.divinityunbound.screen.GenerationStationScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static name.divinityunbound.networking.ModMessages.DR_SPAWN_TYPE;

public class DivineReplicatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private static final int WAND_SLOT = 1;
    private static final int FUEL_SLOT = 0;
    private static final int NO_AI_SLOT = 2;

    private int speedCount = 0;
    private int quantityCount = 0;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private SPAWN_TYPE spawnType = SPAWN_TYPE.EXACT_MATCH;

    public DivineReplicatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DIVINE_REPLICATOR_BLOCK_ENTITY, pos, state);
//        ServerPlayNetworking.registerGlobalReceiver(DR_SPAWN_TYPE,
//                (server, player, handler, buf, responseSender) -> {
//                    DivineReplicatorBlockEntity.SPAWN_TYPE spawnType = buf.readEnumConstant(DivineReplicatorBlockEntity.SPAWN_TYPE.class);
//                    BlockPos position = buf.readBlockPos();
//                    ServerWorld world = player.getServerWorld();
//                    this.setSpawnType(spawnType);
//                });
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DivineReplicatorBlockEntity.this.progress;
                    case 1 -> DivineReplicatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DivineReplicatorBlockEntity.this.progress = value;
                    case 1 -> DivineReplicatorBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("divine_replicator.progress", progress);
        nbt.putInt("divine_replicator.speedCount", speedCount);
        nbt.putInt("divine_replicator.quantityCount", quantityCount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("divine_replicator.progress");
        speedCount = nbt.getInt("divine_replicator.speedCount");
        quantityCount = nbt.getInt("divine_replicator.quantityCount");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Divine Replicator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DivineReplicatorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        if (state.get(DivineReplicatorBlock.ENABLED).booleanValue()
                && this.hasValidWand() && this.hasFuel()) {
            for (int i = 0; i <= speedCount; i++) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);
                if (hasCraftingFinished()) {
                    for (int j = 0; j <= quantityCount; j++) {
                        this.spawnMob(world, pos);
                        this.spendFuel();
                    }
                    this.resetProgress();
                }
            }
        }
        else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void spawnMob(World world, BlockPos pos) {
        ItemStack item = this.getStack(WAND_SLOT);
        if (item.getNbt() != null) {
            if (item.getNbt().contains("entity")) {
                //EntityType type = EntityType.fromNbt(item.getNbt()).orElse(null);
                //    Entity entity = type.create(world);
                Entity entity = EntityType.getEntityFromNbt(item.getSubNbt("entity"), world).get();
                entity.setUuid(UUID.randomUUID());
                if (this.getSpawnType().equals(SPAWN_TYPE.SIMILAR)) {
                    entity = EntityType.get(
                            String.valueOf(item.getSubNbt("entity").get("id")))
                            .orElse(entity.getType()).create(world);
                }

                if (entity instanceof LivingEntity) {
                    if (this.getStack(NO_AI_SLOT).getItem().equals(Items.DRAGON_HEAD)) {
                        ((MobEntity)entity).setAiDisabled(true);
                    }
                    // ((MobEntity)entity).setSilent(true);

                    entity.refreshPositionAndAngles(pos.getX() + 0.5,
                            pos.getY() + 1, pos.getZ() + 0.5,
                            world.getRandom().nextFloat() * 360.0F, 0.0F);
                    boolean spawned = world.spawnEntity(entity);

                }
            }
        }
    }

    private void spendFuel() {
        ItemStack stack = getStack(FUEL_SLOT);
        stack.setCount(stack.getCount() - 1);
    }

    private boolean hasFuel() {
        return getStack(FUEL_SLOT).getCount() > 0 && getStack(FUEL_SLOT).getItem().equals(ModItems.SPACE_FUEL);
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    public void resetUpgrades() {
        this.speedCount = 0;
        this.quantityCount = 0;
    }

    public void increaseSpeed() {
        this.speedCount++;
    }

    public void increaseQuantity() {
        this.quantityCount++;
    }

    private boolean hasValidWand() {
        return this.getStack(WAND_SLOT).getItem().equals(ModItems.WAND_OF_CAPTURING) &&
                this.getStack(WAND_SLOT).getNbt() != null;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return slot == FUEL_SLOT;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return slot == WAND_SLOT;
    }

    public void setSpawnType(SPAWN_TYPE spawnType) {
        this.spawnType = spawnType;
    }

    public SPAWN_TYPE getSpawnType() {
        return this.spawnType;
    }

    public static enum SPAWN_TYPE {
        EXACT_MATCH,
        SIMILAR
    }
}
