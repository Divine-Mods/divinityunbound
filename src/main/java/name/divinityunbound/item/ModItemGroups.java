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

                        entries.add(ModBlocks.CELESTITE_BLOCK);
                        entries.add(ModBlocks.RAW_CELESTITE_BLOCK);

                        entries.add(ModBlocks.CELESTITE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_CELESTITE_ORE);

                        entries.add(ModItems.CELESTIUM_DUST);
                        entries.add(ModItems.GRAIN_OF_TIME);
                        entries.add(ModItems.CHRONOS_CLOCK);
                        entries.add(ModItems.UNHOLY_DUST);
                        entries.add(ModItems.SPACE_DUST);
                        entries.add(ModItems.WILDERSUNG_STRING);
                        entries.add(ModItems.TIME_FORGED_INGOT);
                        entries.add(ModItems.UNHOLY_INGOT);
                        entries.add(ModItems.SPACE_FORGED_INGOT);
                        entries.add(ModItems.SPACE_FUEL);
                        entries.add(ModItems.SPACE_TIME_INGOT);
                        entries.add(ModItems.SPEED_CORE);
                        entries.add(ModItems.QUANTITY_CORE);
                        entries.add(ModItems.MOB_CORE);
                        entries.add(ModItems.IMPORT_CARD);
                        entries.add(ModItems.EXPORT_CARD);
                        entries.add(ModItems.DIVINE_TORCH);

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
                        entries.add(ModBlocks.SAND_OF_TIME);

                        /* Block Entities */
                        entries.add(ModBlocks.GENERATION_STATION);
                        entries.add(ModBlocks.MYSTIC_CHRONOGRAPH);
                        entries.add(ModBlocks.CHRONOS_TIME_ACCUMULATOR);
                        entries.add(ModBlocks.DIVINE_REPLICATOR);
                        entries.add(ModBlocks.SPACE_SIPHON);
                        entries.add(ModBlocks.SPEED_UPGRADE);
                        entries.add(ModBlocks.QUANTITY_UPGRADE);
                        entries.add(ModBlocks.UNHOLY_SILENCER);
                        entries.add(ModBlocks.SPACE_TIME_EVAPORATOR);
                        entries.add(ModBlocks.SPACE_TIME_AMALGAMATOR);
                        entries.add(ModBlocks.WORMHOLE_TRANSPORTER);
                        entries.add(ModBlocks.ITEM_TRASHCAN);
                        entries.add(ModBlocks.FLUID_TRASHCAN);
                        entries.add(ModBlocks.ENERGY_TRASHCAN);

                        entries.add(ModBlocks.SPACE_TIME_FURNACE);

                        /* Foci */
                        entries.add(ModItems.CELESTITE_COAL_FOCUS);
                        entries.add(ModItems.CELESTITE_IRON_FOCUS);
                        entries.add(ModItems.CELESTITE_GOLD_FOCUS);
                        entries.add(ModItems.CELESTITE_DIAMOND_FOCUS);
                        entries.add(ModItems.CELESTITE_NETHERITE_FOCUS);

                    }).build());
    public static void registerItemGroups() {
        DivinityUnbound.LOGGER.info("Registering Item Groups for " + DivinityUnbound.MOD_ID);
    }
}
