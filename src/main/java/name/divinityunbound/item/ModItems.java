package name.divinityunbound.item;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.item.custom.*;
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
    public static final Item CELESTIUM_DUST = registerItem("celestium_dust", new Item(new FabricItemSettings()));
    public static final Item GRAIN_OF_TIME = registerItem("grain_of_time", new GrainOfTimeItem(new FabricItemSettings()));
    public static final Item CHRONOS_CLOCK = registerItem("chronos_clock", new Item(new FabricItemSettings()));
    public static final Item UNHOLY_DUST = registerItem("unholy_dust", new Item(new FabricItemSettings()));
    public static final Item WILDERSUNG_STRING = registerItem("wildersung_string", new Item(new FabricItemSettings()));
    public static final Item TIME_FORGED_INGOT = registerItem("time_forged_ingot", new Item(new FabricItemSettings()));
    public static final Item UNHOLY_INGOT = registerItem("unholy_ingot", new Item(new FabricItemSettings()));
    public static final Item QUANTITY_CORE = registerItem("quantity_core", new Item(new FabricItemSettings()));
    public static final Item SPEED_CORE = registerItem("speed_core", new Item(new FabricItemSettings()));

    public static final Item MAGIC_CELESTITE_DETECTOR = registerItem("magic_celestite_detector",
            new MagicOreDetectorItem(new FabricItemSettings().maxDamage(64)));
    public static final Item UNHOLY_WAND = registerItem("unholy_wand",
            new UnholyWandItem(new FabricItemSettings().maxDamage(32)));

    public static final Item DIVINE_WAND_OF_FLIGHT = registerItem("divine_wand_of_flight",
            new DivineWandOfFlightItem(new FabricItemSettings().maxDamage(16)));

    public static final Item CELESTITE_PICKAXE = registerItem("celestite_pickaxe",
            new PickaxeItem(ModToolMaterial.CELESTITE, 2, 1.5f, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_AXE = registerItem("celestite_axe",
            new AxeItem(ModToolMaterial.CELESTITE, 8, 1.5f, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_SHOVEL = registerItem("celestite_shovel",
            new ShovelItem(ModToolMaterial.CELESTITE, 1, 1.5f, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_SWORD = registerItem("celestite_sword",
            new SwordItem(ModToolMaterial.CELESTITE, 6, 2f, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_HOE = registerItem("celestite_hoe",
            new HoeItem(ModToolMaterial.CELESTITE, 1, 1.5f, new FabricItemSettings().maxCount(1)));


    public static final Item CELESTITE_HELMET = registerItem("celestite_helmet",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.HELMET, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_CHESTPLATE = registerItem("celestite_chestplate",
            new ModArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_LEGGINGS = registerItem("celestite_leggings",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_BOOTS = registerItem("celestite_boots",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)));


    public static final Item CELESTITE_COAL_FOCUS = registerItem("celestite_coal_focus", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_IRON_FOCUS = registerItem("celestite_iron_focus", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_GOLD_FOCUS = registerItem("celestite_gold_focus", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_DIAMOND_FOCUS = registerItem("celestite_diamond_focus", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_NETHERITE_FOCUS = registerItem("celestite_netherite_focus", new Item(new FabricItemSettings().maxCount(1)));



    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RAW_CELESTITE);
        entries.add(CELESTITE);
        entries.add(CELESTITE_NUGGET);
        entries.add(CELESTIUM_DUST);
        entries.add(GRAIN_OF_TIME);
        entries.add(CHRONOS_CLOCK);
        entries.add(UNHOLY_DUST);
        entries.add(WILDERSUNG_STRING);
        entries.add(TIME_FORGED_INGOT);
        entries.add(UNHOLY_INGOT);
        entries.add(SPEED_CORE);
        entries.add(QUANTITY_CORE);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DivinityUnbound.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DivinityUnbound.LOGGER.info("Registering Mod Items for " + DivinityUnbound.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
