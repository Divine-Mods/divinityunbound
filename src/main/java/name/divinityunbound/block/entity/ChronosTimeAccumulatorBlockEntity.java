package name.divinityunbound.block.entity;

import name.divinityunbound.item.ModItems;
import name.divinityunbound.screen.ChronosTimeAccumulatorScreenHandler;
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

public class ChronosTimeAccumulatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final int OUTPUT_SLOT = 0;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    public ChronosTimeAccumulatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHRONOS_TIME_ACCUMULATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ChronosTimeAccumulatorBlockEntity.this.progress;
                    case 1 -> ChronosTimeAccumulatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ChronosTimeAccumulatorBlockEntity.this.progress = value;
                    case 1 -> ChronosTimeAccumulatorBlockEntity.this.maxProgress = value;
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
        nbt.putInt("chronos_time_accumulator.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("chronos_time_accumulator.progress");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Chronos Time Accumulator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ChronosTimeAccumulatorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        if (isOutputSlotEmptyOrReceivable()) {
            this.increaseCraftProgress();
            markDirty(world, pos, state);
            if (hasCraftingFinished()) {
                this.craftItem();
                this.resetProgress();
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

    private void craftItem() {
        ItemStack item = new ItemStack(ModItems.GRAIN_OF_TIME, getStack(OUTPUT_SLOT).getCount() + 1);

        this.setStack(OUTPUT_SLOT, item);
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
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
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return slot == OUTPUT_SLOT && direction == Direction.DOWN;
    }
}
