package name.divinityunbound.item;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.item.custom.MagicOreDetectorItem;
import name.divinityunbound.item.custom.ModArmorItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item RAW_CELESTITE = registerItem("raw_celestite", new Item(new FabricItemSettings()));
    public static final Item CELESTITE = registerItem("celestite", new Item(new FabricItemSettings()));
    public static final Item CELESTITE_NUGGET = registerItem("celestite_nugget", new Item(new FabricItemSettings()));

    public static final Item MAGIC_CELESTITE_DETECTOR = registerItem("magic_celestite_detector",
            new MagicOreDetectorItem(new FabricItemSettings().maxDamage(64)));

    public static final Item CELESTITE_PICKAXE = registerItem("celestite_pickaxe",
            new PickaxeItem(ModToolMaterial.CELESTITE, 2, 1.5f, new FabricItemSettings()));
    public static final Item CELESTITE_AXE = registerItem("celestite_axe",
            new AxeItem(ModToolMaterial.CELESTITE, 8, 1.5f, new FabricItemSettings()));
    public static final Item CELESTITE_SHOVEL = registerItem("celestite_shovel",
            new ShovelItem(ModToolMaterial.CELESTITE, 1, 1.5f, new FabricItemSettings()));
    public static final Item CELESTITE_SWORD = registerItem("celestite_sword",
            new SwordItem(ModToolMaterial.CELESTITE, 6, 2f, new FabricItemSettings()));
    public static final Item CELESTITE_HOE = registerItem("celestite_hoe",
            new HoeItem(ModToolMaterial.CELESTITE, 1, 1.5f, new FabricItemSettings()));


    public static final Item CELESTITE_HELMET = registerItem("celestite_helmet",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item CELESTITE_CHESTPLATE = registerItem("celestite_chestplate",
            new ModArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item CELESTITE_LEGGINGS = registerItem("celestite_leggings",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item CELESTITE_BOOTS = registerItem("celestite_boots",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.BOOTS, new FabricItemSettings()));



    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RAW_CELESTITE);
        entries.add(CELESTITE);
        entries.add(CELESTITE_NUGGET);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DivinityUnbound.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DivinityUnbound.LOGGER.info("Registering Mod Items for " + DivinityUnbound.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
