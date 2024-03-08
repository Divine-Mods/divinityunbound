package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.screen.ItemTrashcanScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
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

public class ItemTrashcanBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final int INPUT_SLOT = 0;
    private static final int CHECK_UPGRADE_TICKS = 20;

    private int speedCount = 0;
    private int quantityCount = 0;
    private int upgradeCheck = 0;
    public ItemTrashcanBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_TRASHCAN_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Item Trashcan");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ItemTrashcanScreenHandler(syncId, playerInventory, this);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        if (!this.getStack(INPUT_SLOT).isEmpty()) {
            this.removeStack(INPUT_SLOT);
            markDirty(world, pos, state);
        }
    }

    private boolean isInputSlotEmptyOrReceivable() {
        return this.getStack(INPUT_SLOT).isEmpty() || this.getStack(INPUT_SLOT).getCount() < this.getStack(INPUT_SLOT).getMaxCount();
    }

    private boolean canInsertAmountIntoInputSlot() {
        return this.getStack(INPUT_SLOT).getCount() + 1 + quantityCount <= getStack(INPUT_SLOT).getMaxCount();
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
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return false;
    }
}
