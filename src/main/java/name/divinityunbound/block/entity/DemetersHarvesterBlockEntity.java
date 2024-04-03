package name.divinityunbound.block.entity;

import name.divinityunbound.block.custom.DivineReplicatorBlock;
import name.divinityunbound.screen.DemetersHarvesterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.List;

public class DemetersHarvesterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final int DEFAULT_RANGE = 8;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedCount = 0;
    private int quantityCount = 0;
    private int rangeCount = 0;
    public DemetersHarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEMETERS_HARVESTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DemetersHarvesterBlockEntity.this.progress;
                    case 1 -> DemetersHarvesterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DemetersHarvesterBlockEntity.this.progress = value;
                    case 1 -> DemetersHarvesterBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public final SimpleInventory internalHoeInventory = new SimpleInventory(1) {
        @Override
        public boolean isValid(int slot, ItemStack stack) {
            if (slot == 0) {
                return stack.isIn(ItemTags.HOES);
            }
            return false;
        }

        @Override
        public void markDirty() {
            DemetersHarvesterBlockEntity.this.markDirty();
        }
    };
    public final SimpleInventory internalInventory = new SimpleInventory(9) {
        @Override
        public boolean isValid(int slot, ItemStack stack) {
            return !stack.isEmpty() && Block.getBlockFromItem(stack.getItem()) instanceof CropBlock;
        }

        @Override
        public void markDirty() {
            DemetersHarvesterBlockEntity.this.markDirty();
        }
    };
    public final InventoryStorage inventoryHoeWrapper = InventoryStorage.of(internalHoeInventory, null);
    public final InventoryStorage inventoryWrapper = InventoryStorage.of(internalInventory, null);

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(500000, Integer.MAX_VALUE, Integer.MAX_VALUE) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("demeters_harvester.internalHoeInventory", internalHoeInventory.toNbtList());
        nbt.put("demeters_harvester.internalInventory", internalInventory.toNbtList());
        nbt.putInt("demeters_harvester.progress", progress);
        nbt.putInt("demeters_harvester.speedCount", speedCount);
        nbt.putInt("demeters_harvester.quantityCount", quantityCount);
        nbt.putInt("demeters_harvester.rangeCount", rangeCount);
        nbt.putLong(("demeters_harvester.energy"), energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        internalHoeInventory.readNbtList((NbtList) nbt.get("demeters_harvester.internalHoeInventory"));
        internalInventory.readNbtList((NbtList) nbt.get("demeters_harvester.internalInventory"));
        progress = nbt.getInt("demeters_harvester.progress");
        speedCount = nbt.getInt("demeters_harvester.speedCount");
        quantityCount = nbt.getInt("demeters_harvester.quantityCount");
        rangeCount = nbt.getInt("demeters_harvester.rangeCount");
        energyStorage.amount = nbt.getLong("demeters_harvester.energy");
    }

    public void setEnergyAmount(long amount) {
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.insert(amount, transaction);
            transaction.commit();
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Demeters Harvester");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DemetersHarvesterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    private int cooldown = 0;
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (!state.get(DivineReplicatorBlock.ENABLED)) return;

        int range = (DEFAULT_RANGE + (rangeCount * 2)) / 2;
        if (hasItemInHoeSlot() && hasEnoughEnergyToCraft()) {
            if (cooldown > (60 - (speedCount * 6))) {
                harvestCrops(world, pos, range);
                plantCrops(world, pos, range);
                cooldown = 0;
            }
            else {
                cooldown++;
            }

            extractEnergy();
        }
    }


    private void harvestCrops(World world, BlockPos pos, int range) {
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                BlockPos offsetPos = pos.add(i, 0, j);
                BlockState blockState = world.getBlockState(offsetPos);
                if (blockState.getBlock() instanceof CropBlock) {
                    if (((CropBlock) blockState.getBlock()).getAge(blockState) ==
                            ((CropBlock) blockState.getBlock()).getMaxAge()) {
                        ((ModifiableWorld) world).breakBlock(offsetPos, true);
                        damageHoe();
                    }
                }
                else if (blockState.getBlock().equals(Blocks.MELON) ||
                        blockState.getBlock().equals(Blocks.PUMPKIN)) {
                    ((ModifiableWorld) world).breakBlock(offsetPos, true);
                    damageHoe();
                }
            }
        }
    }

    private void damageHoe() {
        if(internalHoeInventory.getStack(0).damage(1, world.getRandom(), null)) {
            internalHoeInventory.getStack(0).setCount(0);
        }
    }

    private void plantCrops(World world, BlockPos pos, int range) {
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                BlockPos offsetPos = pos.add(i, -1, j);
                BlockState blockState = world.getBlockState(offsetPos);
                if (blockState.getBlock().equals(Blocks.FARMLAND)) {
                    BlockState blockStateAbove = world.getBlockState(offsetPos.up());
                    if (blockStateAbove.isAir()) {
                        ItemStack seeds = getSeeds();
                        if (!seeds.isEmpty()) {
                            BlockState defaultBlockState = Block.getBlockFromItem(seeds.getItem()).getDefaultState();
                            world.setBlockState(offsetPos.up(), defaultBlockState, Block.NOTIFY_ALL);
                            world.emitGameEvent(GameEvent.BLOCK_PLACE, offsetPos.up(), GameEvent.Emitter.of(defaultBlockState));
                            seeds.decrement(1);
                        }
                    }
                }
            }
        }
    }

    private ItemStack getSeeds() {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < internalInventory.size(); i++) {
            itemStack = internalInventory.getStack(i);
            if (!itemStack.isEmpty() && Block.getBlockFromItem(itemStack.getItem()) instanceof CropBlock) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean hasItemInHoeSlot() {
        return !internalHoeInventory.getStack(0).isEmpty() && internalHoeInventory.getStack(0).isIn(ItemTags.HOES);
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.energyStorage.amount >= 333L;
    }

    private void extractEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(333L, transaction);
            transaction.commit();
        }
    }

    private void fillUpOnEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            if (this.energyStorage.getCapacity() - this.energyStorage.getAmount() <= 64) {
                this.energyStorage.insert(
                        this.energyStorage.getCapacity() - this.energyStorage.getAmount(),
                        transaction);
            }
            else {
                this.energyStorage.insert(64, transaction);
            }
            transaction.commit();
        }
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    private void resetProgress() {
        this.progress = 0;
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
        this.rangeCount = 0;
    }
    public void increaseSpeed() {
        this.speedCount++;
    }
    public void increaseQuantity() {
        this.quantityCount++;
    }
    public void increaseRange() {
        this.rangeCount++;
    }
}
