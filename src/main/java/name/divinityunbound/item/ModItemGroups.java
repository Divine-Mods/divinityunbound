package name.divinityunbound.item;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.fluid.ModFluids;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CELESTITE_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(DivinityUnbound.MOD_ID, "celestite"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.celestite"))
                    .icon(() -> new ItemStack(ModItems.CELESTITE)).entries((displayContext, entries) -> {
                        /* Items */
                        entries.add(ModItems.CELESTITE);
                        entries.add(ModItems.CELESTITE_NUGGET);
                        entries.add(ModItems.RAW_CELESTITE);
                        entries.add(ModItems.RAW_EXPERIENCE);

                        entries.add(ModBlocks.CELESTITE_BLOCK);
                        entries.add(ModBlocks.RAW_CELESTITE_BLOCK);

                        entries.add(ModBlocks.CELESTITE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_CELESTITE_ORE);
                        entries.add(ModBlocks.EXPERIENCE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_EXPERIENCE_ORE);

                        entries.add(ModItems.CELESTIUM_DUST);
                        entries.add(ModItems.GRAIN_OF_TIME);
                        entries.add(ModItems.CHRONOS_CLOCK);
                        entries.add(ModItems.UNHOLY_DUST);
                        entries.add(ModItems.SPACE_DUST);
                        entries.add(ModItems.CONDENSED_MATTER_DUST);
                        entries.add(ModItems.DIVINE_TIME_DUST);
                        entries.add(ModItems.DIVINE_SPACE_DUST);
                        entries.add(ModItems.WILDERSUNG_STRING);
                        entries.add(ModItems.TIME_FORGED_INGOT);
                        entries.add(ModItems.UNHOLY_INGOT);
                        entries.add(ModItems.EXPERIENCE_INGOT);
                        entries.add(ModItems.SPACE_FORGED_INGOT);
                        entries.add(ModItems.SPACE_FUEL);
                        entries.add(ModItems.SPACE_TIME_INGOT);
                        entries.add(ModItems.SPEED_CORE);
                        entries.add(ModItems.QUANTITY_CORE);
                        entries.add(ModItems.RANGE_CORE);
                        entries.add(ModItems.MOB_CORE);
                        entries.add(ModItems.IMPORT_CARD);
                        entries.add(ModItems.EXPORT_CARD);
                        entries.add(ModItems.DIVINE_TORCH);
                        entries.add(ModItems.SAND_OF_TIME);
                        entries.add(ModItems.GOLD_BAND);
                        entries.add(ModItems.BATTERY_CORE);
                        entries.add(ModItems.FILTER_ITEM);

                        entries.add(ModFluids.SPACE_TIME_BUCKET);

                        /* Wands */
                        //entries.add(ModItems.MAGIC_CELESTITE_DETECTOR);
                        entries.add(ModItems.WAND_BINDING);
                        entries.add(ModItems.UNHOLY_WAND);
                        entries.add(ModItems.DIVINE_WAND_OF_FLIGHT);
                        entries.add(ModItems.WAND_OF_CAPTURING);
                        entries.add(ModItems.WAND_OF_RESPIRATION);
                        entries.add(ModItems.WAND_OF_TELEPORTATION);
                        entries.add(ModItems.WAND_OF_THE_ARCHER);
                        entries.add(ModItems.WAND_OF_CELEBRATION);
                        entries.add(ModItems.WAND_OF_FIRE_BENDING);
                        entries.add(ModItems.WAND_OF_MAGNETIZATION);
                        entries.add(ModItems.WAND_OF_HEALING);
                        entries.add(ModItems.GREATER_WAND_OF_HEALING);

                        /* Tools */
                        entries.add(ModItems.CELESTITE_PICKAXE);
                        entries.add(ModItems.CELESTITE_AXE);
                        entries.add(ModItems.CELESTITE_SHOVEL);
                        entries.add(ModItems.CELESTITE_SWORD);
                        entries.add(ModItems.CELESTITE_HOE);

                        entries.add(ModItems.TIME_FORGED_PICKAXE);
                        entries.add(ModItems.TIME_FORGED_AXE);
                        entries.add(ModItems.TIME_FORGED_SHOVEL);
                        entries.add(ModItems.TIME_FORGED_SWORD);
                        entries.add(ModItems.TIME_FORGED_HOE);

                        entries.add(ModItems.SPACE_FORGED_PICKAXE);
                        entries.add(ModItems.SPACE_FORGED_AXE);
                        entries.add(ModItems.SPACE_FORGED_SHOVEL);
                        entries.add(ModItems.SPACE_FORGED_SWORD);
                        entries.add(ModItems.SPACE_FORGED_HOE);

                        entries.add(ModItems.SPACE_TIME_PICKAXE);
                        entries.add(ModItems.SPACE_TIME_AXE);
                        entries.add(ModItems.SPACE_TIME_SHOVEL);
                        entries.add(ModItems.SPACE_TIME_SWORD);
                        entries.add(ModItems.SPACE_TIME_HOE);

                        entries.add(ModItems.CELESTITE_PAXEL);
                        entries.add(ModItems.TIME_FORGED_PAXEL);
                        entries.add(ModItems.SPACE_FORGED_PAXEL);
                        entries.add(ModItems.SPACE_TIME_PAXEL);

                        //entries.add(ModItems.PROTEUS_TRIDENT);

                        /* Armor */
                        entries.add(ModItems.CELESTITE_HELMET);
                        entries.add(ModItems.CELESTITE_CHESTPLATE);
                        entries.add(ModItems.CELESTITE_LEGGINGS);
                        entries.add(ModItems.CELESTITE_BOOTS);

                        entries.add(ModItems.TIME_FORGED_HELMET);
                        entries.add(ModItems.TIME_FORGED_CHESTPLATE);
                        entries.add(ModItems.TIME_FORGED_LEGGINGS);
                        entries.add(ModItems.TIME_FORGED_BOOTS);

                        entries.add(ModItems.SPACE_FORGED_HELMET);
                        entries.add(ModItems.SPACE_FORGED_CHESTPLATE);
                        entries.add(ModItems.SPACE_FORGED_LEGGINGS);
                        entries.add(ModItems.SPACE_FORGED_BOOTS);

                        entries.add(ModItems.EXPERIENCE_HELMET);
                        entries.add(ModItems.EXPERIENCE_CHESTPLATE);
                        entries.add(ModItems.EXPERIENCE_LEGGINGS);
                        entries.add(ModItems.EXPERIENCE_BOOTS);

                        entries.add(ModItems.HERMES_BOOTS);

                        /* Wood */
                        entries.add(ModBlocks.WILDERSUNG_LOG);
                        entries.add(ModBlocks.WILDERSUNG_WOOD);
                        entries.add(ModBlocks.STRIPPED_WILDERSUNG_LOG);
                        entries.add(ModBlocks.STRIPPED_WILDERSUNG_WOOD);
                        entries.add(ModBlocks.WILDERSUNG_PLANKS);
                        entries.add(ModBlocks.CELESTITE_INFUSED_WILDERSUNG_PLANKS);
                        entries.add(ModBlocks.WILDERSUNG_LEAVES);
                        entries.add(ModBlocks.WILDERSUNG_SAPLING);

                        /* Blocks */
                        entries.add(ModBlocks.CELESTITE_INFUSED_STONE);
                        entries.add(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE);
                        entries.add(ModBlocks.FROZEN_TIME_GLASS);
                        entries.add(ModBlocks.FROZEN_TIME_LAMP);
                        entries.add(ModBlocks.ARCANE_FLOOR_LAMP);
                        //entries.add(ModBlocks.SAND_OF_TIME);
                        entries.add(ModBlocks.CELESTIUM_DUST_BLOCK);
                        entries.add(ModBlocks.UNHOLY_DUST_BLOCK);
                        entries.add(ModBlocks.SPACE_DUST_BLOCK);
                        entries.add(ModBlocks.CONDENSED_MATTER_DUST_BLOCK);
                        entries.add(ModBlocks.DIVINE_TIME_DUST_BLOCK);
                        entries.add(ModBlocks.DIVINE_SPACE_DUST_BLOCK);
                        entries.add(ModBlocks.CELESTIAL_GLASS);
                        entries.add(ModBlocks.DARK_CELESTIAL_GLASS);
                        entries.add(ModBlocks.MINI_GLASS);
                        entries.add(ModBlocks.WILDERSUNG_SILK);
                        entries.add(ModBlocks.WITHERED_GLASS);
                        entries.add(ModBlocks.UNHOLY_GRASS_BLOCK);
                        entries.add(ModBlocks.TIME_FORGED_BLOCK);
                        entries.add(ModBlocks.SPACE_FORGED_BLOCK);

                        /* Block Entities */
                        entries.add(ModBlocks.GENERATION_STATION);
                        entries.add(ModBlocks.MYSTIC_CHRONOGRAPH);
                        entries.add(ModBlocks.CHRONOS_TIME_ACCUMULATOR);
                        entries.add(ModBlocks.DIVINE_REPLICATOR);
                        entries.add(ModBlocks.SPACE_SIPHON);
                        entries.add(ModBlocks.SPEED_UPGRADE);
                        entries.add(ModBlocks.QUANTITY_UPGRADE);
                        entries.add(ModBlocks.RANGE_UPGRADE);
                        entries.add(ModBlocks.UNHOLY_SILENCER);
                        entries.add(ModBlocks.SPACE_TIME_EVAPORATOR);
                        entries.add(ModBlocks.SPACE_TIME_AMALGAMATOR);
                        entries.add(ModBlocks.WORMHOLE_TRANSPORTER);
                        entries.add(ModBlocks.ITEM_TRASHCAN);
                        entries.add(ModBlocks.FLUID_TRASHCAN);
                        entries.add(ModBlocks.ENERGY_TRASHCAN);
                        entries.add(ModBlocks.HALLOWED_FLUID_TANK);
                        entries.add(ModBlocks.KNOWLEDGE_EXTRACTOR);
                        entries.add(ModBlocks.COAL_GENERATOR);
                        entries.add(ModBlocks.MOB_ATTRACTOR);
                        entries.add(ModBlocks.ZEUS_BATTERY);
                        entries.add(ModBlocks.CREATIVE_ZEUS_BATTERY);
                        entries.add(ModBlocks.DEMETERS_HARVESTER);
                        entries.add(ModBlocks.PERSEPHONES_BLESSING);
                        entries.add(ModBlocks.PORTUNUS_PROPAGATOR);
                        entries.add(ModBlocks.VELES_GATHERER);
                        //entries.add(ModBlocks.ITEM_SINGULARITY_STORAGE);

                        //entries.add(ModBlocks.SPACE_TIME_FURNACE);

                        /* Multiblocks */
                        entries.add(ModBlocks.PROTEUS_CONTROLLER_BLOCK);
                        entries.add(ModBlocks.PROTEUS_CONVERTER_BLOCK);

                        /* Foci */
                        entries.add(ModItems.CELESTITE_COAL_FOCUS);
                        entries.add(ModItems.CELESTITE_REDSTONE_FOCUS);
                        entries.add(ModItems.CELESTITE_LAPIS_LAZULI_FOCUS);
                        entries.add(ModItems.CELESTITE_IRON_FOCUS);
                        entries.add(ModItems.CELESTITE_GOLD_FOCUS);
                        entries.add(ModItems.CELESTITE_DIAMOND_FOCUS);
                        entries.add(ModItems.CELESTITE_NETHERITE_FOCUS);

                        /* Crops */
                        entries.add(ModItems.ETHEREAL_CRYSTAL_GREENS);
                        entries.add(ModItems.ETHEREAL_CRYSTAL_GREENS_SEEDS);
                        entries.add(ModItems.ETHEREAL_CRYSTAL_GREENS_SALAD);

                    }).build());
    public static void registerItemGroups() {
        DivinityUnbound.LOGGER.info("Registering Item Groups for " + DivinityUnbound.MOD_ID);
    }
}
