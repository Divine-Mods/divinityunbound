package name.divinityunbound.datagen;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

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
                .add(ModBlocks.DEEPSLATE_CELESTITE_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.CELESTITE_BLOCK)
                .add(ModBlocks.RAW_CELESTITE_BLOCK)
                .add(ModBlocks.CELESTITE_ORE)
                .add(ModBlocks.DEEPSLATE_CELESTITE_ORE);
    }
}
