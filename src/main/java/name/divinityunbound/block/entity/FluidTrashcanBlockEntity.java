package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.screen.FluidTrashcanScreenHandler;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
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

public class FluidTrashcanBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    private static final int CHECK_UPGRADE_TICKS = 20;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(0, ItemStack.EMPTY);
    private int speedCount = 0;
    private int quantityCount = 0;
    private int upgradeCheck = 0;
    public FluidTrashcanBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_TRASHCAN_BLOCK_ENTITY, pos, state);
    }

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
        nbt.put("fluid_trashcan.variant", this.fluidStorage.variant.toNbt());
        nbt.putLong("fluid_trashcan.fluid_amount", this.fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("fluid_trashcan.variant"));
        this.fluidStorage.amount = nbt.getLong("fluid_trashcan.fluid_amount");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Fluid Trashcan");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FluidTrashcanScreenHandler(syncId, playerInventory, this);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        if (this.fluidStorage.getAmount() > 0) {
            try(Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.extract(
                        this.fluidStorage.getResource(),
                        this.fluidStorage.getAmount(),
                        transaction);
                transaction.commit();
            }
            markDirty(world, pos, state);
        }
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

}
