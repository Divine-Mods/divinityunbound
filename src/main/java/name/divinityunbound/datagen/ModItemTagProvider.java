package name.divinityunbound.datagen;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.CELESTITE_HELMET, ModItems.CELESTITE_CHESTPLATE, ModItems.CELESTITE_LEGGINGS, ModItems.CELESTITE_BOOTS)
                .add(ModItems.TIME_FORGED_HELMET, ModItems.TIME_FORGED_CHESTPLATE, ModItems.TIME_FORGED_LEGGINGS, ModItems.TIME_FORGED_BOOTS)
                .add(ModItems.SPACE_FORGED_HELMET, ModItems.SPACE_FORGED_CHESTPLATE, ModItems.SPACE_FORGED_LEGGINGS, ModItems.SPACE_FORGED_BOOTS);

        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.WILDERSUNG_PLANKS.asItem())
                .add(ModBlocks.CELESTITE_INFUSED_WILDERSUNG_PLANKS.asItem());

        getOrCreateTagBuilder(ItemTags.BREAKS_DECORATED_POTS)
                .add(ModItems.CELESTITE_PICKAXE)
                .add(ModItems.CELESTITE_AXE)
                .add(ModItems.CELESTITE_SWORD)
                .add(ModItems.CELESTITE_SHOVEL)
                .add(ModItems.CELESTITE_HOE)
                .add(ModItems.TIME_FORGED_PICKAXE)
                .add(ModItems.TIME_FORGED_AXE)
                .add(ModItems.TIME_FORGED_SWORD)
                .add(ModItems.TIME_FORGED_SHOVEL)
                .add(ModItems.TIME_FORGED_HOE)
                .add(ModItems.SPACE_FORGED_PICKAXE)
                .add(ModItems.SPACE_FORGED_AXE)
                .add(ModItems.SPACE_FORGED_SWORD)
                .add(ModItems.SPACE_FORGED_SHOVEL)
                .add(ModItems.SPACE_FORGED_HOE);

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.CELESTITE_SWORD)
                .add(ModItems.TIME_FORGED_SWORD)
                .add(ModItems.SPACE_FORGED_SWORD);

        getOrCreateTagBuilder(ItemTags.TOOLS)
                .add(ModItems.CELESTITE_PICKAXE)
                .add(ModItems.CELESTITE_AXE)
                .add(ModItems.CELESTITE_SWORD)
                .add(ModItems.CELESTITE_SHOVEL)
                .add(ModItems.CELESTITE_HOE)
                .add(ModItems.TIME_FORGED_PICKAXE)
                .add(ModItems.TIME_FORGED_AXE)
                .add(ModItems.TIME_FORGED_SWORD)
                .add(ModItems.TIME_FORGED_SHOVEL)
                .add(ModItems.TIME_FORGED_HOE)
                .add(ModItems.SPACE_FORGED_PICKAXE)
                .add(ModItems.SPACE_FORGED_AXE)
                .add(ModItems.SPACE_FORGED_SWORD)
                .add(ModItems.SPACE_FORGED_SHOVEL)
                .add(ModItems.SPACE_FORGED_HOE);

        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.WILDERSUNG_LOG.asItem())
                .add(ModBlocks.WILDERSUNG_WOOD.asItem())
                .add(ModBlocks.STRIPPED_WILDERSUNG_LOG.asItem())
                .add(ModBlocks.STRIPPED_WILDERSUNG_WOOD.asItem());
    }
}
