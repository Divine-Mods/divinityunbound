package name.divinityunbound.item;

import com.mojang.serialization.Lifecycle;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModItems {

    /* Items */
    public static final Item RAW_CELESTITE = registerItem("raw_celestite", new Item(new FabricItemSettings()));
    public static final Item CELESTITE = registerItem("celestite", new Item(new FabricItemSettings()));
    public static final Item CELESTITE_NUGGET = registerItem("celestite_nugget", new Item(new FabricItemSettings()));
    public static final Item CELESTIUM_DUST = registerItem("celestium_dust", new Item(new FabricItemSettings()));
    public static final Item SPACE_DUST = registerItem("space_dust", new Item(new FabricItemSettings()));
    public static final Item GRAIN_OF_TIME = registerItem("grain_of_time", new GrainOfTimeItem(new FabricItemSettings()));
    public static final Item CHRONOS_CLOCK = registerItem("chronos_clock", new Item(new FabricItemSettings()));
    public static final Item UNHOLY_DUST = registerItem("unholy_dust", new Item(new FabricItemSettings()));
    public static final Item WILDERSUNG_STRING = registerItem("wildersung_string", new Item(new FabricItemSettings()));
    public static final Item TIME_FORGED_INGOT = registerItem("time_forged_ingot", new Item(new FabricItemSettings()));
    public static final Item UNHOLY_INGOT = registerItem("unholy_ingot", new Item(new FabricItemSettings()));
    public static final Item SPACE_FORGED_INGOT = registerItem("space_forged_ingot", new Item(new FabricItemSettings()));
    public static final Item SPACE_FUEL = registerItem("space_fuel", new Item(new FabricItemSettings()));
    public static final Item SPACE_TIME_INGOT = registerItem("space_time_ingot", new Item(new FabricItemSettings()));
    public static final Item QUANTITY_CORE = registerItem("quantity_core", new Item(new FabricItemSettings()));
    public static final Item SPEED_CORE = registerItem("speed_core", new Item(new FabricItemSettings()));
    public static final Item MOB_CORE = registerItem("mob_core", new Item(new FabricItemSettings()));

    /* Wands */
    public static final Item MAGIC_CELESTITE_DETECTOR = registerItem("magic_celestite_detector",
            new MagicOreDetectorItem(new FabricItemSettings().maxDamage(64)));
    public static final Item UNHOLY_WAND = registerItem("unholy_wand",
            new UnholyWandItem(new FabricItemSettings().maxDamage(32)));

    public static final Item DIVINE_WAND_OF_FLIGHT = registerItem("divine_wand_of_flight",
            new DivineWandOfFlightItem(new FabricItemSettings().maxDamage(32)));

    public static final Item WAND_OF_CAPTURING = registerItem("wand_of_capturing",
            new WandOfCapturingItem(new FabricItemSettings().maxCount(1)));

    /* Tools */

    public static final Item CELESTITE_PICKAXE = registerItem("celestite_pickaxe",
            new PickaxeItem(ModToolMaterial.CELESTITE, 1, -2.8F, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_AXE = registerItem("celestite_axe",
            new AxeItem(ModToolMaterial.CELESTITE, 5.0F, -3.0F, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_SHOVEL = registerItem("celestite_shovel",
            new ShovelItem(ModToolMaterial.CELESTITE, 1.5F, -3.0F, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_SWORD = registerItem("celestite_sword",
            new SwordItem(ModToolMaterial.CELESTITE, 3, -2.4F, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_HOE = registerItem("celestite_hoe",
            new HoeItem(ModToolMaterial.CELESTITE, -3, 0.0F, new FabricItemSettings().maxCount(1)));

    public static final Item TIME_FORGED_PICKAXE = registerItem("time_forged_pickaxe",
            new PickaxeItem(ModToolMaterial.TIME_FORGED, 1, -2.8F, new FabricItemSettings().maxCount(1)));
    public static final Item TIME_FORGED_AXE = registerItem("time_forged_axe",
            new AxeItem(ModToolMaterial.TIME_FORGED, 5.0F, -3.0F, new FabricItemSettings().maxCount(1)));
    public static final Item TIME_FORGED_SHOVEL = registerItem("time_forged_shovel",
            new ShovelItem(ModToolMaterial.TIME_FORGED, 1.5F, -3.0F, new FabricItemSettings().maxCount(1)));
    public static final Item TIME_FORGED_SWORD = registerItem("time_forged_sword",
            new SwordItem(ModToolMaterial.TIME_FORGED, 3, -2.4F, new FabricItemSettings().maxCount(1)));
    public static final Item TIME_FORGED_HOE = registerItem("time_forged_hoe",
            new HoeItem(ModToolMaterial.TIME_FORGED, -4, 0.0F, new FabricItemSettings().maxCount(1)));


    /* Armor */
    public static final Item CELESTITE_HELMET = registerItem("celestite_helmet",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.HELMET, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_CHESTPLATE = registerItem("celestite_chestplate",
            new ModArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_LEGGINGS = registerItem("celestite_leggings",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_BOOTS = registerItem("celestite_boots",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)));


    /* Foci */
    // TODO: Add foci for lapis, redstone, and maybe a few building blocks
    public static final Item CELESTITE_COAL_FOCUS = registerItem("celestite_coal_focus", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_IRON_FOCUS = registerItem("celestite_iron_focus", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_GOLD_FOCUS = registerItem("celestite_gold_focus", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_DIAMOND_FOCUS = registerItem("celestite_diamond_focus", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_NETHERITE_FOCUS = registerItem("celestite_netherite_focus", new Item(new FabricItemSettings().maxCount(1)));

    /* Blocks */
    // TODO: Fix broken animated item model for space siphon
    //RegistryEntry.Reference reference = overrideItem("space_siphon", new SpaceSiphonItem(ModBlocks.SPACE_SIPHON, new FabricItemSettings()));
    //public static final Item SPACE_SIPHON_ITEM = Registries.ITEM.get(new Identifier("space_siphon"));

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
        entries.add(MOB_CORE);
    }

    public static RegistryEntry.Reference overrideItem(String itemName, Item item) {
        return ((MutableRegistry)Registries.ITEM).add(RegistryKey.of(Registries.ITEM.getKey(), new Identifier(itemName)), item, Lifecycle.stable());
    }
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DivinityUnbound.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DivinityUnbound.LOGGER.info("Registering Mod Items for " + DivinityUnbound.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
