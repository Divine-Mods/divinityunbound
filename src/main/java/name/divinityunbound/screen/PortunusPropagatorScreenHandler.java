package name.divinityunbound.screen;

import name.divinityunbound.block.entity.PortunusPropagatorBlockEntity;
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

public class PortunusPropagatorScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final PortunusPropagatorBlockEntity blockEntity;

    public PortunusPropagatorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()), new ArrayPropertyDelegate(3));
    }

    public PortunusPropagatorScreenHandler(int syncId, PlayerInventory playerInventory,
                                           BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.PORTUNUS_PROPAGATOR_SCREEN_HANDLER, syncId);
        checkSize(((PortunusPropagatorBlockEntity) blockEntity).internalInventory, 9);
        this.inventory = ((PortunusPropagatorBlockEntity) blockEntity).internalInventory;
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((PortunusPropagatorBlockEntity) blockEntity);

        // Item Slots
        this.addSlot(new Slot(inventory, 0, 62, 17));
        this.addSlot(new Slot(inventory, 1, 80, 17));
        this.addSlot(new Slot(inventory, 2, 98, 17));
        this.addSlot(new Slot(inventory, 3, 62, 35));
        this.addSlot(new Slot(inventory, 4, 80, 35));
        this.addSlot(new Slot(inventory, 5, 98, 35));
        this.addSlot(new Slot(inventory, 6, 62, 53));
        this.addSlot(new Slot(inventory, 7, 80, 53));
        this.addSlot(new Slot(inventory, 8, 98, 53));

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
