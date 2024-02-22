package name.divinityunbound.datagen;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static List<ItemConvertible> CELESTITE_SMELTABLES = List.of(ModItems.RAW_CELESTITE,
            ModBlocks.CELESTITE_ORE, ModBlocks.DEEPSLATE_CELESTITE_ORE);
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter, CELESTITE_SMELTABLES, RecipeCategory.MISC, ModItems.CELESTITE, 0.7f, 200, "celestite");
        offerBlasting(exporter, CELESTITE_SMELTABLES, RecipeCategory.MISC, ModItems.CELESTITE, 0.7f, 100, "celestite");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.CELESTITE, RecipeCategory.DECORATIONS, ModBlocks.CELESTITE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.RAW_CELESTITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_CELESTITE_BLOCK);
        //offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.CELESTITE_NUGGET, RecipeCategory.MISC, ModItems.CELESTITE);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.WILDERSUNG_LOG, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.WILDERSUNG_WOOD, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.STRIPPED_WILDERSUNG_LOG, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.STRIPPED_WILDERSUNG_WOOD, "celestite",4);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE, 1)
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .input('N', ModItems.CELESTITE_NUGGET)
                .criterion(hasItem(ModItems.CELESTITE_NUGGET), conditionsFromItem(ModItems.CELESTITE_NUGGET))
                .offerTo(exporter, new Identifier("celestite_from_celestite_nugget"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE_NUGGET, 9)
                        .input(ModItems.CELESTITE)
                                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem((ModItems.CELESTITE)))
                                        .offerTo(exporter, new Identifier("celestite_nuggets_from_celestite"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CELESTITE_ORE, 1)
                .pattern("SSS")
                .pattern("SCS")
                .pattern("SSS")
                .input('S', Items.STONE)
                .input('C', ModItems.RAW_CELESTITE)
                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                .criterion(hasItem(ModItems.RAW_CELESTITE), conditionsFromItem(ModItems.RAW_CELESTITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.CELESTITE_ORE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE_COAL_FOCUS, 1)
                .pattern("CGC")
                .pattern("GBG")
                .pattern("CGC")
                .input('G', Items.GOLD_INGOT)
                .input('B', Items.COAL_BLOCK)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.COAL_BLOCK), conditionsFromItem(Items.COAL_BLOCK))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CELESTITE_COAL_FOCUS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE_IRON_FOCUS, 1)
                .pattern("CGC")
                .pattern("GBG")
                .pattern("CGC")
                .input('G', Items.GOLD_INGOT)
                .input('B', Items.IRON_BLOCK)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.IRON_BLOCK), conditionsFromItem(Items.IRON_BLOCK))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CELESTITE_IRON_FOCUS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE_GOLD_FOCUS, 1)
                .pattern("CGC")
                .pattern("GBG")
                .pattern("CGC")
                .input('G', Items.GOLD_INGOT)
                .input('B', Items.GOLD_BLOCK)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.GOLD_BLOCK), conditionsFromItem(Items.GOLD_BLOCK))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CELESTITE_GOLD_FOCUS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE_DIAMOND_FOCUS, 1)
                .pattern("CGC")
                .pattern("GBG")
                .pattern("CGC")
                .input('G', Items.GOLD_INGOT)
                .input('B', Items.DIAMOND_BLOCK)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CELESTITE_DIAMOND_FOCUS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE_NETHERITE_FOCUS, 1)
                .pattern("CGC")
                .pattern("GBG")
                .pattern("CGC")
                .input('G', Items.GOLD_INGOT)
                .input('B', Items.NETHERITE_BLOCK)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.NETHERITE_BLOCK), conditionsFromItem(Items.NETHERITE_BLOCK))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CELESTITE_NETHERITE_FOCUS)));
    }
}
