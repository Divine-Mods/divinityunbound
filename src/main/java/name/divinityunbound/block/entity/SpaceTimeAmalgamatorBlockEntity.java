package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.screen.SpaceTimeAmalgamatorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Iterator;
import java.util.List;

public class SpaceTimeAmalgamatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory, GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private static final int CELESTIUM_DUST_SLOT = 0;
    private static final int UNHOLY_DUST_SLOT = 1;
    private static final int SPACE_DUST_SLOT = 2;
//    private static final int FLUID_ITEM_SLOT = 3;
//    private static final int ENERGY_ITEM_SLOT = 4;
    private static final int CHECK_UPGRADE_TICKS = 20;

    private int speedCount = 0;
    private int quantityCount = 0;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int upgradeCheck = 0;
    public SpaceTimeAmalgamatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPACE_TIME_AMALGAMATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SpaceTimeAmalgamatorBlockEntity.this.progress;
                    case 1 -> SpaceTimeAmalgamatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SpaceTimeAmalgamatorBlockEntity.this.progress = value;
                    case 1 -> SpaceTimeAmalgamatorBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(5000000, Integer.MAX_VALUE, Integer.MAX_VALUE) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BUCKET / 81) * 64; // 1 Bucket = 81000 Droplets = 1000mB || *64 ==> 64,000mB = 64 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("space_time_amalgamator.progress", progress);
        nbt.putLong(("space_time_amalgamator.energy"), energyStorage.amount);
        nbt.put("space_time_amalgamator.variant", fluidStorage.variant.toNbt());
        nbt.putLong("space_time_amalgamator.fluid_amount", fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("space_time_amalgamator.progress");
        energyStorage.amount = nbt.getLong("space_time_amalgamator.energy");
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("space_time_amalgamator.variant"));
        fluidStorage.amount = nbt.getLong("space_time_amalgamator.fluid_amount");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Space Time Amalgamator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SpaceTimeAmalgamatorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        //fillUpOnEnergy();

        if (hasRecipe()) {
            increaseCraftProgress();
            extractEnergy();
            markDirty(world, pos, state);

            if (hasCraftingFinished()) {
                craftItem();
                produceFluid();
                resetProgress();
            }
        }
        else {
            resetProgress();
        }
    }

    private boolean hasRecipe() {
        return  hasItemInInputSlot() && hasEnoughEnergyToCraft()
                && hasEnoughFluidStorageToCraft();
    }

    private void craftItem() {
        this.removeStack(CELESTIUM_DUST_SLOT, 1);
        this.removeStack(UNHOLY_DUST_SLOT, 1);
        this.removeStack(SPACE_DUST_SLOT, 1);
    }

    private void extractFluid() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.extract(FluidVariant.of(ModFluids.STILL_SPACE_TIME), 500, transaction);
            transaction.commit();
        }
    }

    private void produceFluid() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.insert(FluidVariant.of(ModFluids.STILL_SPACE_TIME),
                    500, transaction);
            transaction.commit();
        }
    }

    private void extractEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(250000L, transaction);
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

    private boolean hasEnergyItemInEnergySlot(int energyItemSlot) {
        return this.getStack(energyItemSlot).getItem() == ModItems.SPACE_FUEL;
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.energyStorage.amount >= 32L * this.maxProgress;
    }

    private boolean hasEnoughFluidStorageToCraft() {
        return this.fluidStorage.getCapacity() - this.fluidStorage.amount >= 500; // mB amount!
    }

    private boolean hasItemInInputSlot() {
        return !this.getStack(CELESTIUM_DUST_SLOT).isEmpty()
                && !this.getStack(UNHOLY_DUST_SLOT).isEmpty()
                && !this.getStack(SPACE_DUST_SLOT).isEmpty()
                && this.getStack(CELESTIUM_DUST_SLOT).getItem().equals(ModItems.CELESTIUM_DUST)
                && this.getStack(UNHOLY_DUST_SLOT).getItem().equals(ModItems.UNHOLY_DUST)
                && this.getStack(SPACE_DUST_SLOT).getItem().equals(ModItems.SPACE_DUST);
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
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return true;
//        Direction localDir = this.getWorld().getBlockState(pos).get(SpaceTimeAmalgamatorBlock.FACING);
//
//        if(side == Direction.DOWN) {
//            return false;
//        }
//
//        if(side == Direction.UP) {
//            return slot == INPUT_SLOT;
//        }
//
//        return switch (localDir) {
//            default -> //NORTH
//                    side.getOpposite() == Direction.NORTH && slot == INPUT_SLOT ||
//                            side.getOpposite() == Direction.WEST && slot == INPUT_SLOT;
//            case EAST ->
//                    side.rotateYClockwise() == Direction.NORTH && slot == INPUT_SLOT ||
//                            side.rotateYClockwise() == Direction.WEST && slot == INPUT_SLOT;
//            case SOUTH ->
//                    side == Direction.NORTH && slot == INPUT_SLOT ||
//                            side == Direction.WEST && slot == INPUT_SLOT;
//            case WEST ->
//                    side.rotateYCounterclockwise() == Direction.NORTH && slot == INPUT_SLOT ||
//                            side.rotateYCounterclockwise() == Direction.WEST && slot == INPUT_SLOT;
//        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return true;
//        Direction localDir = this.getWorld().getBlockState(this.pos).get(SpaceTimeAmalgamatorBlock.FACING);
//
//        if(side == Direction.UP) {
//            return false;
//        }
//
//        // Down extract 2
//        if(side == Direction.DOWN) {
//            return slot == OUTPUT_SLOT;
//        }
//
//        // bottom extract 2
//        // right extract 2
//        return switch (localDir) {
//            default ->  side.getOpposite() == Direction.SOUTH && slot == OUTPUT_SLOT ||
//                    side.getOpposite() == Direction.EAST && slot == OUTPUT_SLOT;
//
//            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
//                    side.rotateYClockwise() == Direction.EAST && slot == OUTPUT_SLOT;
//
//            case SOUTH ->   side == Direction.SOUTH && slot == OUTPUT_SLOT ||
//                    side == Direction.EAST && slot == OUTPUT_SLOT;
//
//            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
//                    side.rotateYCounterclockwise() == Direction.EAST && slot == OUTPUT_SLOT;
//        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
}
