package name.divinityunbound.block;

import name.divinityunbound.DivinityUnbound;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
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
