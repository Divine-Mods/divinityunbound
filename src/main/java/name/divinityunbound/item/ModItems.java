package name.divinityunbound.item;

import com.mojang.serialization.Lifecycle;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ModItems {

    /* Items */
    public static final Item RAW_CELESTITE = registerItem("raw_celestite", new Item(new FabricItemSettings()));
    public static final Item RAW_EXPERIENCE = registerItem("raw_experience", new Item(new FabricItemSettings()));
    public static final Item CELESTITE = registerItem("celestite", new Item(new FabricItemSettings()));
    public static final Item CELESTITE_NUGGET = registerItem("celestite_nugget", new Item(new FabricItemSettings()));
    public static final Item CELESTIUM_DUST = registerItem("celestium_dust", new Item(new FabricItemSettings()));
    public static final Item SPACE_DUST = registerItem("space_dust", new Item(new FabricItemSettings()));
    public static final Item GRAIN_OF_TIME = registerItem("grain_of_time", new GrainOfTimeItem(new FabricItemSettings()));
    public static final Item CHRONOS_CLOCK = registerItem("chronos_clock", new Item(new FabricItemSettings()));
    public static final Item UNHOLY_DUST = registerItem("unholy_dust", new Item(new FabricItemSettings()));
    public static final Item CONDENSED_MATTER_DUST = registerItem("condensed_matter_dust", new Item(new FabricItemSettings()));
    public static final Item DIVINE_TIME_DUST = registerItem("divine_time_dust", new Item(new FabricItemSettings()));
    public static final Item DIVINE_SPACE_DUST = registerItem("divine_space_dust", new Item(new FabricItemSettings()));
    public static final Item WILDERSUNG_STRING = registerItem("wildersung_string", new Item(new FabricItemSettings()));
    public static final Item TIME_FORGED_INGOT = registerItem("time_forged_ingot", new Item(new FabricItemSettings()));
    public static final Item UNHOLY_INGOT = registerItem("unholy_ingot", new Item(new FabricItemSettings()));
    public static final Item SPACE_FORGED_INGOT = registerItem("space_forged_ingot", new Item(new FabricItemSettings()));
    public static final Item EXPERIENCE_INGOT = registerItem("experience_ingot", new Item(new FabricItemSettings()));
    public static final Item SPACE_FUEL = registerItem("space_fuel", new Item(new FabricItemSettings()));
    public static final Item SPACE_TIME_INGOT = registerItem("space_time_ingot", new Item(new FabricItemSettings()));
    public static final Item QUANTITY_CORE = registerItem("quantity_core", new Item(new FabricItemSettings()));
    public static final Item SPEED_CORE = registerItem("speed_core", new Item(new FabricItemSettings()));
    public static final Item RANGE_CORE = registerItem("range_core", new Item(new FabricItemSettings()));
    public static final Item MOB_CORE = registerItem("mob_core", new Item(new FabricItemSettings()));
    public static final Item GOLD_BAND = registerItem("gold_band", new Item(new FabricItemSettings()));
    public static final Item BATTERY_CORE = registerItem("battery_core", new Item(new FabricItemSettings()));
    public static final Item IMPORT_CARD = registerItem("import_card", new IOCardItem(new FabricItemSettings()));
    public static final Item EXPORT_CARD = registerItem("export_card", new IOCardItem(new FabricItemSettings()));
    public static final Item FILTER_ITEM = registerItem("filter_item", new FilterItem(new FabricItemSettings()));

    public static final Item DIVINE_TORCH = Items.register(
            new VerticallyAttachableBlockItem(ModBlocks.DIVINE_TORCH,
                    ModBlocks.WALL_DIVINE_TORCH,
                    new FabricItemSettings(), Direction.DOWN));

    public static final Item SAND_OF_TIME = Items.register(new SandOfTimeItem(ModBlocks.SAND_OF_TIME, new FabricItemSettings()));
    /* Wands */
//    public static final Item MAGIC_CELESTITE_DETECTOR = registerItem("magic_celestite_detector",
//            new MagicOreDetectorItem(new FabricItemSettings().maxDamage(64)));
    public static final Item WAND_BINDING = registerItem("wand_binding",
            new Item(new FabricItemSettings()));
    public static final Item UNHOLY_WAND = registerItem("unholy_wand",
            new UnholyWandItem(new FabricItemSettings().maxDamage(32)));

    public static final Item DIVINE_WAND_OF_FLIGHT = registerItem("divine_wand_of_flight",
            new DivineWandOfFlightItem(new FabricItemSettings().maxDamage(32)));

    public static final Item WAND_OF_CAPTURING = registerItem("wand_of_capturing",
            new WandOfCapturingItem(new FabricItemSettings().maxCount(1)));

    public static final Item WAND_OF_RESPIRATION = registerItem("wand_of_respiration",
            new WandOfRespirationItem(new FabricItemSettings().maxCount(16)));

    public static final Item WAND_OF_TELEPORTATION = registerItem("wand_of_teleportation",
            new WandOfTeleportationItem(new FabricItemSettings().maxDamage(128)));

    public static final Item WAND_OF_THE_ARCHER = registerItem("wand_of_the_archer",
            new WandOfTheArcherItem(new FabricItemSettings().maxDamage(384)));

    public static final Item WAND_OF_CELEBRATION = registerItem("wand_of_celebration",
            new WandOfCelebrationItem(new FabricItemSettings().maxDamage(128)));

    public static final Item WAND_OF_FIRE_BENDING = registerItem("wand_of_fire_bending",
            new WandOfFireBendingItem(Fluids.LAVA,new FabricItemSettings().maxDamage(128)));

    public static final Item WAND_OF_MAGNETIZATION = registerItem("wand_of_magnetization",
            new WandOfMagnetizationItem(new FabricItemSettings()));

    public static final Item WAND_OF_HEALING = registerItem("wand_of_healing",
            new WandOfHealingItem(new FabricItemSettings().maxDamage(8)));

    public static final Item GREATER_WAND_OF_HEALING = registerItem("greater_wand_of_healing",
            new GreaterWandOfHealingItem(new FabricItemSettings().maxDamage(8)));

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

    public static final Item SPACE_FORGED_PICKAXE = registerItem("space_forged_pickaxe",
            new PickaxeItem(ModToolMaterial.SPACE_FORGED, 1, -2.8F, new FabricItemSettings().maxCount(1)));
    public static final Item SPACE_FORGED_AXE = registerItem("space_forged_axe",
            new AxeItem(ModToolMaterial.SPACE_FORGED, 5.0F, -2.8F, new FabricItemSettings().maxCount(1)));
    public static final Item SPACE_FORGED_SHOVEL = registerItem("space_forged_shovel",
            new ShovelItem(ModToolMaterial.SPACE_FORGED, 1.5F, -3.0F, new FabricItemSettings().maxCount(1)));
    public static final Item SPACE_FORGED_SWORD = registerItem("space_forged_sword",
            new SwordItem(ModToolMaterial.SPACE_FORGED, 3, -2.2F, new FabricItemSettings().maxCount(1)));
    public static final Item SPACE_FORGED_HOE = registerItem("space_forged_hoe",
            new HoeItem(ModToolMaterial.SPACE_FORGED, -4, 0.0F, new FabricItemSettings().maxCount(1)));
    public static final Item SPACE_TIME_PICKAXE = registerItem("space_time_pickaxe",
            new PickaxeItem(ModToolMaterial.SPACE_TIME, 1, -2.8F, new FabricItemSettings()));
    public static final Item SPACE_TIME_AXE = registerItem("space_time_axe",
            new AxeItem(ModToolMaterial.SPACE_TIME, 5.0F, -2.8F, new FabricItemSettings()));
    public static final Item SPACE_TIME_SHOVEL = registerItem("space_time_shovel",
            new ShovelItem(ModToolMaterial.SPACE_TIME, 1.5F, -3.0F, new FabricItemSettings()));
    public static final Item SPACE_TIME_SWORD = registerItem("space_time_sword",
            new SwordItem(ModToolMaterial.SPACE_TIME, 3, -2.2F, new FabricItemSettings()));
    public static final Item SPACE_TIME_HOE = registerItem("space_time_hoe",
            new HoeItem(ModToolMaterial.SPACE_TIME, -4, 0.0F, new FabricItemSettings()));


    public static final Item CELESTITE_PAXEL = registerItem("celestite_paxel",
            new PaxelItem(1, -2.8F, ModToolMaterial.CELESTITE, new FabricItemSettings()));

    public static final Item TIME_FORGED_PAXEL = registerItem("time_forged_paxel",
            new PaxelItem(1, -2.8F, ModToolMaterial.TIME_FORGED, new FabricItemSettings()));

    public static final Item SPACE_FORGED_PAXEL = registerItem("space_forged_paxel",
            new PaxelItem(1, -2.8F, ModToolMaterial.SPACE_FORGED, new FabricItemSettings()));

    public static final Item SPACE_TIME_PAXEL = registerItem("space_time_paxel",
            new PaxelItem(1, -2.8F, ModToolMaterial.SPACE_TIME, new FabricItemSettings()));

    /* Armor */
    public static final Item CELESTITE_HELMET = registerItem("celestite_helmet",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.HELMET, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_CHESTPLATE = registerItem("celestite_chestplate",
            new ModArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_LEGGINGS = registerItem("celestite_leggings",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxCount(1)));
    public static final Item CELESTITE_BOOTS = registerItem("celestite_boots",
            new ArmorItem(ModArmorMaterials.CELESTITE, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)));

    public static final Item TIME_FORGED_HELMET = registerItem("time_forged_helmet",
            new ArmorItem(ModArmorMaterials.TIME_FORGED, ArmorItem.Type.HELMET, new FabricItemSettings().maxCount(1)));
    public static final Item TIME_FORGED_CHESTPLATE = registerItem("time_forged_chestplate",
            new ModArmorItem(ModArmorMaterials.TIME_FORGED, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxCount(1)));
    public static final Item TIME_FORGED_LEGGINGS = registerItem("time_forged_leggings",
            new ArmorItem(ModArmorMaterials.TIME_FORGED, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxCount(1)));
    public static final Item TIME_FORGED_BOOTS = registerItem("time_forged_boots",
            new ArmorItem(ModArmorMaterials.TIME_FORGED, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)));

    public static final Item SPACE_FORGED_HELMET = registerItem("space_forged_helmet",
            new ArmorItem(ModArmorMaterials.SPACE_FORGED, ArmorItem.Type.HELMET, new FabricItemSettings().maxCount(1)));
    public static final Item SPACE_FORGED_CHESTPLATE = registerItem("space_forged_chestplate",
            new ModArmorItem(ModArmorMaterials.SPACE_FORGED, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxCount(1)));
    public static final Item SPACE_FORGED_LEGGINGS = registerItem("space_forged_leggings",
            new ArmorItem(ModArmorMaterials.SPACE_FORGED, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxCount(1)));
    public static final Item SPACE_FORGED_BOOTS = registerItem("space_forged_boots",
            new ArmorItem(ModArmorMaterials.SPACE_FORGED, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)));

    public static final Item EXPERIENCE_HELMET = registerItem("experience_helmet",
            new ArmorItem(ModArmorMaterials.EXPERIENCE, ArmorItem.Type.HELMET, new FabricItemSettings().maxCount(1)));
    public static final Item EXPERIENCE_CHESTPLATE = registerItem("experience_chestplate",
            new ModArmorItem(ModArmorMaterials.EXPERIENCE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxCount(1)));
    public static final Item EXPERIENCE_LEGGINGS = registerItem("experience_leggings",
            new ArmorItem(ModArmorMaterials.EXPERIENCE, ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxCount(1)));
    public static final Item EXPERIENCE_BOOTS = registerItem("experience_boots",
            new ArmorItem(ModArmorMaterials.EXPERIENCE, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)));

    public static final Item HERMES_BOOTS = registerItem("hermes_boots",
            new HermesBootsArmorItem(ModArmorMaterials.HERMES, ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1)));

    public static final Item PROTEUS_TRIDENT = registerItem("proteus_trident",
            new TridentItem(new Item.Settings().maxDamage(250)));


    /* Foci */
    // TODO: Add foci for some few building blocks
    public static final Item CELESTITE_COAL_FOCUS = registerItem("celestite_coal_focus", new Item(new FabricItemSettings().maxDamage(128)));
    public static final Item CELESTITE_REDSTONE_FOCUS = registerItem("celestite_redstone_focus", new Item(new FabricItemSettings().maxDamage(128)));
    public static final Item CELESTITE_LAPIS_LAZULI_FOCUS = registerItem("celestite_lapis_lazuli_focus", new Item(new FabricItemSettings().maxDamage(128)));
    public static final Item CELESTITE_IRON_FOCUS = registerItem("celestite_iron_focus", new Item(new FabricItemSettings().maxDamage(64)));
    public static final Item CELESTITE_GOLD_FOCUS = registerItem("celestite_gold_focus", new Item(new FabricItemSettings().maxDamage(64)));
    public static final Item CELESTITE_DIAMOND_FOCUS = registerItem("celestite_diamond_focus", new Item(new FabricItemSettings().maxDamage(32)));
    public static final Item CELESTITE_NETHERITE_FOCUS = registerItem("celestite_netherite_focus", new Item(new FabricItemSettings().maxDamage(16)));

    /* Crops */
    public static final Item ETHEREAL_CRYSTAL_GREENS = registerItem("ethereal_crystal_greens",
            new Item(new FabricItemSettings().food(ModFoodComponents.ETHEREAL_CRYSTAL_GREENS)));
    public static final Item ETHEREAL_CRYSTAL_GREENS_SALAD = registerItem("ethereal_crystal_greens_salad",
            new Item(new FabricItemSettings().food(ModFoodComponents.ETHEREAL_CRYSTAL_GREENS_SALAD)));
    public static final Item ETHEREAL_CRYSTAL_GREENS_SEEDS = registerItem("ethereal_crystal_greens_seeds",
            new AliasedBlockItem(ModBlocks.ETHEREAL_CRYSTAL_GREENS, new FabricItemSettings()));
    /* Blocks */
    // TODO: Fix broken animated item model for space siphon
    //RegistryEntry.Reference reference = overrideItem("space_siphon", new SpaceSiphonItem(ModBlocks.SPACE_SIPHON, new FabricItemSettings()));
    //public static final Item SPACE_SIPHON_ITEM = Registries.ITEM.get(new Identifier("space_siphon"));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RAW_CELESTITE);
        entries.add(RAW_EXPERIENCE);
        entries.add(CELESTITE);
        entries.add(CELESTITE_NUGGET);
        entries.add(CELESTIUM_DUST);
        entries.add(GRAIN_OF_TIME);
        entries.add(CHRONOS_CLOCK);
        entries.add(UNHOLY_DUST);
        entries.add(SPACE_DUST);
        entries.add(CONDENSED_MATTER_DUST);
        entries.add(DIVINE_SPACE_DUST);
        entries.add(DIVINE_TIME_DUST);
        entries.add(WILDERSUNG_STRING);
        entries.add(TIME_FORGED_INGOT);
        entries.add(SPACE_FORGED_INGOT);
        entries.add(UNHOLY_INGOT);
        entries.add(EXPERIENCE_INGOT);
        entries.add(SPEED_CORE);
        entries.add(QUANTITY_CORE);
        entries.add(MOB_CORE);
        entries.add(WAND_BINDING);
        entries.add(GOLD_BAND);
        entries.add(BATTERY_CORE);
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
