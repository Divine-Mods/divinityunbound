package name.divinityunbound.item.custom;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.entity.DemetersHarvesterBlockEntity;
import name.divinityunbound.block.entity.ImplementedInventory;
import name.divinityunbound.screen.FilterItemScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/*
Modified code from Gitko01's VacuumHopper FilterItem
 */
public class FilterItem extends Item implements ImplementedInventory {
    public enum MODE {
        WHITELIST,
        BLACKLIST,
        INVALID
    }
    public FilterItem(Settings settings) {
        super(settings);
    }

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(18, ItemStack.EMPTY);
//    public final SimpleInventory internalInventory = new SimpleInventory(18) {
//    };
//    public final InventoryStorage inventoryWrapper = InventoryStorage.of(internalInventory, null);

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            ExtendedScreenHandlerFactory extendedScreenHandlerFactory = new ExtendedScreenHandlerFactory() {
                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new FilterItemScreenHandler(syncId, inv, getInv());
                }

                @Override
                public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                    buf.writeEnumConstant(getModeFromNbt(user.getStackInHand(hand)));
                    createItemsToFilterBuf(getItemsToFilterFromNbt(user.getStackInHand(hand)), buf);
                }

                @Override
                public Text getDisplayName() {
                    return Text.translatable(getTranslationKey());
                }
            };

            user.openHandledScreen(extendedScreenHandlerFactory);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public Inventory getInv() {
        return this;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }

    public static void saveModeNbt(ItemStack itemStack, MODE mode) {
        NbtCompound newNbt = itemStack.getNbt();
        if (newNbt == null) {
            newNbt = new NbtCompound();
        }

        newNbt.putInt(DivinityUnbound.MOD_ID + ".mode", mode.ordinal());

        itemStack.setNbt(newNbt);
    }

    public static MODE getModeFromNbt(ItemStack itemStack) {
        NbtCompound nbt = itemStack.getNbt();
        MODE mode = MODE.INVALID;

        try {
            mode = MODE.values()[nbt.getInt(DivinityUnbound.MOD_ID + ".mode")];
        } catch (Exception e) {
            // Set default mode
            saveModeNbt(itemStack, MODE.WHITELIST);
            mode = MODE.WHITELIST;
        }

        return mode;
    }

    public static Hashtable<Integer, ItemStack> readItemsToFilterBuf(PacketByteBuf buf) {
        int[] itemIndexes = buf.readIntArray();
        Hashtable<Integer, ItemStack> itemStacks = new Hashtable<>();

        for (int index : itemIndexes) {
            ItemStack is = buf.readItemStack();
            itemStacks.put(index, is);
        }

        return itemStacks;
    }

    public static void createItemsToFilterBuf(Hashtable<Integer, ItemStack> itemsToFilter, PacketByteBuf buf) {
        int[] indexes = new int[itemsToFilter.size()];
        List<ItemStack> itemStacks = new ArrayList<>();

        int count = 0;

        for (int i : itemsToFilter.keySet()) {
            indexes[count] = i;
            itemStacks.add(itemsToFilter.get(i));
            count = count + 1;
        }

        buf.writeIntArray(indexes);

        itemStacks.forEach(buf::writeItemStack);
    }

    public static Hashtable<Integer, ItemStack> getItemsToFilterFromNbt(ItemStack itemStackInHand) {
        NbtCompound nbt = itemStackInHand.getNbt();
        Hashtable<Integer, ItemStack> itemsToFilter = new Hashtable<>();

        try {
            int[] itemIndexes = nbt.getIntArray(DivinityUnbound.MOD_ID + ".itemsToFilterIndexes");

            for (int i : itemIndexes) {
                //ItemStack itemStack = new ItemStack(Item.byRawId(nbt.getInt(DivinityUnbound.MOD_ID + "." + i)));
                ItemStack itemStack = new ItemStack(Registries.ITEM.get(new Identifier(nbt.getString(DivinityUnbound.MOD_ID + "." + i))));
                itemsToFilter.put(i, itemStack);
            }

        } catch (Exception ignored) {
            // Set default value
            saveItemsToFilterToNbt(itemStackInHand, itemsToFilter);
        }

        return itemsToFilter;
    }

    public static void saveItemsToFilterToNbt(ItemStack itemStackInHand, Hashtable<Integer, ItemStack> itemsToFilter) {
        NbtCompound newNbt = itemStackInHand.getNbt();
        if (newNbt == null) {
            newNbt = new NbtCompound();
        }

        // Split items to filter up and save to nbt
        int[] indexes = new int[itemsToFilter.size()];

        int count = 0;

        for (int i : itemsToFilter.keySet()) {
            indexes[count] = i;

            //newNbt.putInt(DivinityUnbound.MOD_ID + "." + i, Item.getRawId(itemsToFilter.get(i).getItem()));

            DivinityUnbound.LOGGER.info("[Divinity Unbound] Saving " + Item.getRawId(itemsToFilter.get(i).getItem()) + " in slot " + i);
            DivinityUnbound.LOGGER.info("[Divinity Unbound] Saving " + itemsToFilter.get(i).getItem().getRegistryEntry().registryKey().getValue() + " in slot " + i);
            newNbt.putString(DivinityUnbound.MOD_ID + "." + i, String.valueOf(itemsToFilter.get(i).getItem().getRegistryEntry().registryKey().getValue()));

            count = count + 1;
        }

        newNbt.putIntArray(DivinityUnbound.MOD_ID + ".itemsToFilterIndexes", indexes);

        itemStackInHand.setNbt(newNbt);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            int lineCount = 4;

            Hashtable<Integer, ItemStack> itemsToFilter = getItemsToFilterFromNbt(stack);
            String[] items = new String[2];

            int count = 0;
            for (int key : itemsToFilter.keySet()) {
                if (count <= 1) {
                    items[count] = Text.translatable(itemsToFilter.get(key).getItem().getTranslationKey()).getString();
                    count++;
                } else {
                    break;
                }
            }

            if (items[0] == null) {
                items[0] = "empty";
            }
            if (items[1] == null) {
                items[1] = "empty";
            }
            if (stack.hasNbt()) {
                tooltip.add(Text.literal(getModeFromNbt(stack).toString()));
                tooltip.add(Text.literal("§7§o" + items[0] + ", " + items[1] + "..."));
            }
        } else {
            tooltip.add(Text.translatable("tooltip." + DivinityUnbound.MOD_ID + ".hold_shift"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

}
