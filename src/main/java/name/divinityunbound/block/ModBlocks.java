package name.divinityunbound.block;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.custom.GenerationStationBlock;
import name.divinityunbound.block.custom.MysticChronographBlock;
import name.divinityunbound.world.tree.ModSaplingGenerators;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block CELESTITE_BLOCK = registerBlock("celestite_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    public static final Block RAW_CELESTITE_BLOCK = registerBlock("raw_celestite_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK)));

    public static final Block CELESTITE_ORE = registerBlock("celestite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), FabricBlockSettings.copyOf(Blocks.STONE).strength(2f)));
    public static final Block DEEPSLATE_CELESTITE_ORE = registerBlock("deepslate_celestite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), FabricBlockSettings.copyOf(Blocks.DEEPSLATE).strength(4f)));
//    public static final Block NETHER_CELESTITE_ORE = registerBlock("nether_celestite_ore",
//            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), FabricBlockSettings.copyOf(Blocks.NETHERRACK).strength(1.5f)));
//    public static final Block END_STONE_CELESTITE_ORE = registerBlock("end_stone_celestite_ore",
//            new ExperienceDroppingBlock(UniformIntProvider.create(4, 7), FabricBlockSettings.copyOf(Blocks.END_STONE).strength(4f)));

    public static final Block WILDERSUNG_LOG = registerBlock("wildersung_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(4f)));
    public static final Block WILDERSUNG_WOOD = registerBlock("wildersung_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(4f)));
    public static final Block STRIPPED_WILDERSUNG_LOG = registerBlock("stripped_wildersung_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(4f)));
    public static final Block STRIPPED_WILDERSUNG_WOOD = registerBlock("stripped_wildersung_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(4f)));

    public static final Block WILDERSUNG_PLANKS = registerBlock("wildersung_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(4f)));

    public static final Block CELESTITE_INFUSED_WILDERSUNG_PLANKS = registerBlock("celestite_infused_wildersung_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(5f)));
    public static final Block CELESTITE_INFUSED_STONE = registerBlock("celestite_infused_stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(5f)));
    public static final Block GOLDEN_CELESTITE_INFUSED_STONE = registerBlock("golden_celestite_infused_stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(5f)));
    public static final Block WILDERSUNG_LEAVES = registerBlock("wildersung_leaves",
            new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(4f).nonOpaque()));

    public static final Block WILDERSUNG_SAPLING = registerBlock("wildersung_sapling",
            new SaplingBlock(ModSaplingGenerators.WILDERSUNG, FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));

    public static final Block GENERATION_STATION = registerBlock("generation_station",
            new GenerationStationBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block MYSTIC_CHRONOGRAPH = registerBlock("mystic_chronograph",
            new MysticChronographBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block CHRONOS_TIME_ACCUMULATOR = registerBlock("chronos_time_accumulator",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(5f)));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(DivinityUnbound.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(DivinityUnbound.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        DivinityUnbound.LOGGER.info("Registering ModBlocks for " + DivinityUnbound.MOD_ID);
    }
}
