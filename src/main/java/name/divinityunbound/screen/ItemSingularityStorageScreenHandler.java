package name.divinityunbound.screen;

import name.divinityunbound.block.entity.ItemSingularityStorageBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ItemSingularityStorageScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final ItemSingularityStorageBlockEntity blockEntity;

    public ItemSingularityStorageScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()), new ArrayPropertyDelegate(3));
    }

    public ItemSingularityStorageScreenHandler(int syncId, PlayerInventory playerInventory,
                                               BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.ITEM_SINGULARITY_STORAGE_SCREEN_HANDLER, syncId);
        checkSize(((ItemSingularityStorageBlockEntity) blockEntity).internalInventory, 1);
        this.inventory = ((ItemSingularityStorageBlockEntity) blockEntity).internalInventory;
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((ItemSingularityStorageBlockEntity) blockEntity);

        // Item Slot
        this.addSlot(new Slot(inventory, 0, 80, 32));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }



    public void setPropertyDelegate(int index, int value) {
        this.propertyDelegate.set(index, value);
    }

    public boolean getPropertyDelegate(int index) {
        return this.propertyDelegate.get(index) == 0 ? false : true;
    }

    public int getItemCount() {
        return propertyDelegate.get(2);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
