package name.divinityunbound.item;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
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

                        entries.add(ModItems.MAGIC_CELESTITE_DETECTOR);
                        entries.add(ModItems.UNHOLY_WAND);
                        entries.add(ModItems.CELESTIUM_DUST);
                        entries.add(ModItems.GRAIN_OF_TIME);
                        entries.add(ModItems.CHRONOS_CLOCK);
                        entries.add(ModItems.UNHOLY_DUST);
                        entries.add(ModItems.WILDERSUNG_STRING);
                        entries.add(ModItems.TIME_FORGED_INGOT);

                        /* Tools */
                        entries.add(ModItems.CELESTITE_PICKAXE);
                        entries.add(ModItems.CELESTITE_AXE);
                        entries.add(ModItems.CELESTITE_SHOVEL);
                        entries.add(ModItems.CELESTITE_SWORD);
                        entries.add(ModItems.CELESTITE_HOE);

                        /* Armor */
                        entries.add(ModItems.CELESTITE_HELMET);
                        entries.add(ModItems.CELESTITE_CHESTPLATE);
                        entries.add(ModItems.CELESTITE_LEGGINGS);
                        entries.add(ModItems.CELESTITE_BOOTS);

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

                        /* Block Entities */
                        entries.add(ModBlocks.GENERATION_STATION);
                        entries.add(ModBlocks.MYSTIC_CHRONOGRAPH);
                        entries.add(ModBlocks.CHRONOS_TIME_ACCUMULATOR);

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
