package name.divinityunbound.screen;

import name.divinityunbound.item.custom.FilterItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.Hashtable;

public class FilterItemScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private Hashtable<Integer, ItemStack> itemsToFilter;
    private FilterItem.MODE mode;

    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public FilterItemScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(syncId, playerInventory, new SimpleInventory(18));

        this.mode = packetByteBuf.readEnumConstant(FilterItem.MODE.class);
        this.itemsToFilter = FilterItem.readItemsToFilterBuf(packetByteBuf);
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public FilterItemScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.FILTER_ITEM_SCREEN_HANDLER, syncId);

        this.inventory = inventory;

        checkSize(inventory, 18);
        inventory.onOpen(playerInventory.player);

        int m;
        int l;

        // This inventory
        for (m = 0; m < 2; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 8, 17 + l * 18, 17 + m * 18) {
                    // This is executed on the client-side I believe
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }

                    @Override
                    public boolean canTakeItems(PlayerEntity playerEntity) {
                        return false;
                    }
                });
            }
        }

        // The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        // The player hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }



    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    public Hashtable<Integer, ItemStack> getItemsToFilter() {
        return this.itemsToFilter;
    }

    public FilterItem.MODE getMode() {
        return this.mode;
    }
}

