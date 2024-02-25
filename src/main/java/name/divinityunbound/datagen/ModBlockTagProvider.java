package name.divinityunbound.datagen;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.Blocks.MAGIC_ORE_DETECTOR_DETECTABLE_BLOCKS)
                .add(ModBlocks.CELESTITE_ORE)
                .add(ModBlocks.DEEPSLATE_CELESTITE_ORE);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.CELESTITE_BLOCK)
                .add(ModBlocks.RAW_CELESTITE_BLOCK)
                .add(ModBlocks.CELESTITE_ORE)
                .add(ModBlocks.DEEPSLATE_CELESTITE_ORE)
                .add(ModBlocks.CELESTITE_INFUSED_STONE)
                .add(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE)
                .add(ModBlocks.CHRONOS_TIME_ACCUMULATOR)
                .add(ModBlocks.GENERATION_STATION)
                .add(ModBlocks.MYSTIC_CHRONOGRAPH);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.CELESTITE_BLOCK)
                .add(ModBlocks.RAW_CELESTITE_BLOCK)
                .add(ModBlocks.CELESTITE_ORE)
                .add(ModBlocks.DEEPSLATE_CELESTITE_ORE)
                .add(ModBlocks.CELESTITE_INFUSED_STONE)
                .add(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE)
                .add(ModBlocks.CHRONOS_TIME_ACCUMULATOR)
                .add(ModBlocks.GENERATION_STATION)
                .add(ModBlocks.MYSTIC_CHRONOGRAPH);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.WILDERSUNG_LOG)
                .add(ModBlocks.WILDERSUNG_WOOD)
                .add(ModBlocks.STRIPPED_WILDERSUNG_LOG)
                .add(ModBlocks.STRIPPED_WILDERSUNG_WOOD)
                .add(ModBlocks.WILDERSUNG_PLANKS)
                .add(ModBlocks.CELESTITE_INFUSED_WILDERSUNG_PLANKS);

        getOrCreateTagBuilder(BlockTags.LEAVES)
                .add(ModBlocks.WILDERSUNG_LEAVES);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.WILDERSUNG_LOG)
                .add(ModBlocks.WILDERSUNG_WOOD)
                .add(ModBlocks.STRIPPED_WILDERSUNG_LOG)
                .add(ModBlocks.STRIPPED_WILDERSUNG_WOOD);
    }
}
