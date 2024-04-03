package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.custom.DivineReplicatorBlock;
import name.divinityunbound.block.custom.ZeusBatteryBlock;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.screen.SpaceTimeAmalgamatorScreenHandler;
import name.divinityunbound.screen.ZeusBatteryScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.FilteringStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
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
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.InfiniteEnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Iterator;
import java.util.List;

public class ZeusBatteryBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int quantityCount = 0;
    public boolean isCreative = false;
    public ZeusBatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ZEUS_BATTERY_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ZeusBatteryBlockEntity.this.progress;
                    case 1 -> ZeusBatteryBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ZeusBatteryBlockEntity.this.progress = value;
                    case 1 -> ZeusBatteryBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };

        if (state.get(ZeusBatteryBlock.CREATIVE)) {
            isCreative = true;
            energyStorage = InfiniteEnergyStorage.INSTANCE;
        }
        else {
           energyStorage = new SimpleEnergyStorage(50000000, Integer.MAX_VALUE, Integer.MAX_VALUE) {
                @Override
                protected void onFinalCommit() {
                    markDirty();
                    getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
                }
            };
        }
    }

    public EnergyStorage energyStorage;
//    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(50000000, Integer.MAX_VALUE, Integer.MAX_VALUE) {
//        @Override
//        protected void onFinalCommit() {
//            markDirty();
//            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
//        }
//    };

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("zeus_battery.progress", progress);
        nbt.putInt("zeus_battery.quantityCount", quantityCount);
        if (!isCreative) {
            nbt.putLong(("zeus_battery.energy"), ((SimpleEnergyStorage)energyStorage).amount);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        progress = nbt.getInt("zeus_battery.progress");
        quantityCount = nbt.getInt("zeus_battery.quantityCount");
        if (!isCreative) {
            ((SimpleEnergyStorage)energyStorage).amount = nbt.getLong("zeus_battery.energy");
        }
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
        return Text.literal("Zeus Battery");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ZeusBatteryScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        findNeighborsAndPushEnergy();
    }

    private void findNeighborsAndPushEnergy() {
        for (Direction dir : Direction.values()) {
            EnergyStorage neighborEnergyStorage = findEnergyStorage(world, pos, dir);
            if (neighborEnergyStorage != null && neighborEnergyStorage.supportsInsertion()) {
                pushEnergyToAdjacentStorage(energyStorage, neighborEnergyStorage);
            }
        }
    }

    private EnergyStorage findEnergyStorage(World world, BlockPos pos, Direction direction) {
        return EnergyStorage.SIDED.find(world, pos.offset(direction), direction);
    }

    private void pushEnergyToAdjacentStorage(EnergyStorage source, EnergyStorage target) {
        try(Transaction transaction = Transaction.openOuter()) {
            long amountMoved = EnergyStorageUtil.move(
                    source, // from source
                    target, // into target
                    Integer.MAX_VALUE, // no limit on the amount
                    transaction // create a new transaction for this operation
            );
            transaction.commit();
        }
    }

    private void extractEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(25000L, transaction);
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
        this.quantityCount = 0;
    }
    public void increaseQuantity() {
        this.quantityCount++;
    }
}
