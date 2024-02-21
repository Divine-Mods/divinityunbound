package name.divinityunbound.datagen;

import com.ibm.icu.text.Normalizer2;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CELESTITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_CELESTITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CELESTITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_CELESTITE_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CELESTITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_CELESTITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAGIC_CELESTITE_DETECTOR, Models.GENERATED);

        itemModelGenerator.register(ModItems.CELESTITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTITE_HOE, Models.HANDHELD);
    }
}
