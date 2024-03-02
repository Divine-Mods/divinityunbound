package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.item.custom.WandOfCapturingItem;
import name.divinityunbound.recipe.GenerationStationRecipe;
import name.divinityunbound.screen.DivineReplicatorScreenHandler;
import name.divinityunbound.screen.GenerationStationScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class DivineReplicatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private static final int WAND_SLOT = 1;
    private static final int FUEL_SLOT = 0;

    private static final int CHECK_UPGRADE_TICKS = 20;

    private int speedCount = 0;
    private int quantityCount = 0;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int upgradeCheck = 0;

    public DivineReplicatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DIVINE_REPLICATOR_BLOCK_ENTITY, pos, state);
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
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("divine_replicator.progress");
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

        if (this.hasValidWand() && this.hasFuel()) {
            if (upgradeCheck >= CHECK_UPGRADE_TICKS) {
                countUpgrades(world, pos);
                upgradeCheck = 0;
            }
            upgradeCheck++;
            for (int i = 0; i <= speedCount; i++) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);
                if (hasCraftingFinished()) {
                    this.spawnMob(world, pos);
                    this.spendFuel();
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
            EntityType type = EntityType.fromNbt(item.getNbt()).orElse(null);
            Entity entity = type.create(world);
            if (entity != null) {
                for (int i = 0; i <= quantityCount; i++) {
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

    public static final List<BlockPos> UPGRADE_PROVIDER_OFFSETS = BlockPos.stream(-1, 0, -1, 1, 0, 1).filter((pos) -> {
        return Math.abs(pos.getX()) == 1 || Math.abs(pos.getZ()) == 1;
    }).map(BlockPos::toImmutable).toList();

    public void countUpgrades(World world, BlockPos pos) {
        speedCount = 0;
        quantityCount = 0;
        Iterator it = UPGRADE_PROVIDER_OFFSETS.iterator();
        while(it.hasNext()) {
            BlockPos blockPosOffset = (BlockPos)it.next();
            BlockPos calcPos = new BlockPos(pos.getX() + blockPosOffset.getX(),
                    pos.getY() + blockPosOffset.getY(), pos.getZ() + blockPosOffset.getZ());
            if(world.getBlockState(calcPos).getBlock().equals(ModBlocks.SPEED_UPGRADE)) {
                speedCount++;
            }
            else if(world.getBlockState(calcPos).getBlock().equals(ModBlocks.QUANTITY_UPGRADE)) {
                quantityCount++;
            }
        }
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
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
        return slot == FUEL_SLOT && direction == Direction.UP;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return slot == WAND_SLOT && direction == Direction.DOWN;
    }
}
