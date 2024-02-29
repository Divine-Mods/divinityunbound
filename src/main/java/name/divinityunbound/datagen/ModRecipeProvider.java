package name.divinityunbound.datagen;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.Arrays;
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
        offerSmelting(exporter, Arrays.asList(ModItems.UNHOLY_DUST), RecipeCategory.MISC, ModItems.UNHOLY_INGOT, 0.7f, 200, "celestite");
        offerBlasting(exporter, Arrays.asList(ModItems.UNHOLY_DUST), RecipeCategory.MISC, ModItems.UNHOLY_INGOT, 0.7f, 100, "celestite");
        offerSmelting(exporter, Arrays.asList(ModBlocks.SAND_OF_TIME), RecipeCategory.MISC, ModBlocks.FROZEN_TIME_GLASS, 0.7f, 200, "celestite");
        offerBlasting(exporter, Arrays.asList(ModBlocks.SAND_OF_TIME), RecipeCategory.MISC, ModBlocks.FROZEN_TIME_GLASS, 0.7f, 100, "celestite");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.CELESTITE, RecipeCategory.DECORATIONS, ModBlocks.CELESTITE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.RAW_CELESTITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_CELESTITE_BLOCK);
        //offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.CELESTITE_NUGGET, RecipeCategory.MISC, ModItems.CELESTITE);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.WILDERSUNG_LOG, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.WILDERSUNG_WOOD, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.STRIPPED_WILDERSUNG_LOG, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.STRIPPED_WILDERSUNG_WOOD, "celestite",4);
        offer2x2CompactingRecipe(exporter, RecipeCategory.MISC, ModItems.WILDERSUNG_STRING, ModBlocks.WILDERSUNG_LEAVES);
        offer2x2CompactingRecipe(exporter, RecipeCategory.MISC, ModBlocks.SAND_OF_TIME, ModItems.GRAIN_OF_TIME);

        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.CELESTITE_HELMET, ModItems.CELESTITE);
        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.CELESTITE_CHESTPLATE, ModItems.CELESTITE);
        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.CELESTITE_LEGGINGS, ModItems.CELESTITE);
        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.CELESTITE_BOOTS, ModItems.CELESTITE);

//        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.TIME_FORGED_HELMET, ModItems.TIME_FORGED_INGOT);
//        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.TIME_FORGED_CHESTPLATE, ModItems.TIME_FORGED_INGOT);
//        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.TIME_FORGED_LEGGINGS, ModItems.TIME_FORGED_INGOT);
//        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.TIME_FORGED_BOOTS, ModItems.TIME_FORGED_INGOT);

//        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_FORGED_HELMET, ModItems.SPACE_FORGED_INGOT);
//        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_FORGED_CHESTPLATE, ModItems.SPACE_FORGED_INGOT);
//        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_FORGED_LEGGINGS, ModItems.SPACE_FORGED_INGOT);
//        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_FORGED_BOOTS, ModItems.SPACE_FORGED_INGOT);

//        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_TIME_HELMET, ModItems.SPACE_TIME_INGOT);
//        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_TIME, ModItems.SPACE_TIME_INGOT);
//        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_TIME_LEGGINGS, ModItems.SPACE_TIME_INGOT);
//        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_TIME_BOOTS, ModItems.SPACE_TIME_INGOT);

        offerPickaxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.CELESTITE_PICKAXE, ModItems.CELESTITE);
        offerAxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.CELESTITE_AXE, ModItems.CELESTITE);
        offerShovelRecipe(exporter, RecipeCategory.TOOLS, ModItems.CELESTITE_SHOVEL, ModItems.CELESTITE);
        offerSwordRecipe(exporter, RecipeCategory.TOOLS, ModItems.CELESTITE_SWORD, ModItems.CELESTITE);
        offerHoeRecipe(exporter, RecipeCategory.TOOLS, ModItems.CELESTITE_HOE, ModItems.CELESTITE);

        offerPickaxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.TIME_FORGED_PICKAXE, ModItems.TIME_FORGED_INGOT);
        offerAxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.TIME_FORGED_AXE, ModItems.TIME_FORGED_INGOT);
        offerShovelRecipe(exporter, RecipeCategory.TOOLS, ModItems.TIME_FORGED_SHOVEL, ModItems.TIME_FORGED_INGOT);
        offerSwordRecipe(exporter, RecipeCategory.TOOLS, ModItems.TIME_FORGED_SWORD, ModItems.TIME_FORGED_INGOT);
        offerHoeRecipe(exporter, RecipeCategory.TOOLS, ModItems.TIME_FORGED_HOE, ModItems.TIME_FORGED_INGOT);

//        offerPickaxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_PICKAXE, ModItems.SPACE_FORGED_INGOT);
//        offerAxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_AXE, ModItems.SPACE_FORGED_INGOT);
//        offerShovelRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED, ModItems.SPACE_FORGED_INGOT);
//        offerSwordRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_SWORD, ModItems.SPACE_FORGED_INGOT);
//        offerHoeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_HOE, ModItems.SPACE_FORGED_INGOT);
//
//        offerPickaxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_PICKAXE, ModItems.SPACE_TIME_INGOT);
//        offerAxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_AXE, ModItems.SPACE_TIME_INGOT);
//        offerShovelRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_SHOVEL, ModItems.SPACE_TIME_INGOT);
//        offerSwordRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_SWORD, ModItems.SPACE_TIME_INGOT);
//        offerHoeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_HOE, ModItems.SPACE_TIME_INGOT);

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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CELESTITE_INFUSED_STONE, 1)
                .input(ModItems.CELESTITE)
                .input(Blocks.STONE)
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem((ModItems.CELESTITE)))
                .criterion(hasItem(Blocks.STONE), conditionsFromItem((Blocks.STONE)))
                .offerTo(exporter, new Identifier("celestite_infused_stone"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SPACE_FUEL, 1)
                .input(ModItems.SPACE_DUST)
                .input(Items.COAL)
                .criterion(hasItem(ModItems.SPACE_DUST), conditionsFromItem((ModItems.SPACE_DUST)))
                .criterion(hasItem(Items.COAL), conditionsFromItem((Items.COAL)))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.SPACE_FUEL)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SPACE_TIME_INGOT, 3)
                .input(ModItems.TIME_FORGED_INGOT)
                .input(ModItems.SPACE_FORGED_INGOT)
                .input(Items.NETHERITE_INGOT)
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem((ModItems.TIME_FORGED_INGOT)))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem((ModItems.SPACE_FORGED_INGOT)))
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem((Items.NETHERITE_INGOT)))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.SPACE_TIME_INGOT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE, 1)
                .pattern("GGG")
                .pattern("GCG")
                .pattern("GGG")
                .input('G', Items.GOLD_INGOT)
                .input('C', ModBlocks.CELESTITE_INFUSED_STONE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(ModBlocks.CELESTITE_INFUSED_STONE), conditionsFromItem(ModBlocks.CELESTITE_INFUSED_STONE))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CHRONOS_CLOCK, 1)
                .pattern(" C ")
                .pattern("BGB")
                .pattern(" C ")
                .input('G', Items.CLOCK)
                .input('B', ModBlocks.CELESTITE_BLOCK)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .criterion(hasItem(ModBlocks.CELESTITE_BLOCK), conditionsFromItem(ModBlocks.CELESTITE_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CHRONOS_CLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CHRONOS_TIME_ACCUMULATOR, 1)
                .pattern("SSS")
                .pattern("SCS")
                .pattern("SSS")
                .input('S', ModBlocks.CELESTITE_INFUSED_STONE)
                .input('C', ModItems.CHRONOS_CLOCK)
                .criterion(hasItem(ModBlocks.CELESTITE_INFUSED_STONE), conditionsFromItem(ModBlocks.CELESTITE_INFUSED_STONE))
                .criterion(hasItem(ModItems.CHRONOS_CLOCK), conditionsFromItem(ModItems.CHRONOS_CLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.CHRONOS_TIME_ACCUMULATOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TIME_FORGED_INGOT, 1)
                .pattern("DUD")
                .pattern("UCU")
                .pattern("DUD")
                .input('C', ModItems.CELESTITE)
                .input('D', ModItems.CELESTIUM_DUST)
                .input('U', ModItems.UNHOLY_DUST)
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .criterion(hasItem(ModItems.CELESTIUM_DUST), conditionsFromItem(ModItems.CELESTIUM_DUST))
                .criterion(hasItem(ModItems.UNHOLY_DUST), conditionsFromItem(ModItems.UNHOLY_DUST))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TIME_FORGED_INGOT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SPACE_FORGED_INGOT, 1)
                .pattern("SUS")
                .pattern("UCU")
                .pattern("SUS")
                .input('C', ModItems.CELESTITE)
                .input('S', ModItems.SPACE_DUST)
                .input('U', ModItems.UNHOLY_DUST)
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .criterion(hasItem(ModItems.SPACE_DUST), conditionsFromItem(ModItems.SPACE_DUST))
                .criterion(hasItem(ModItems.UNHOLY_DUST), conditionsFromItem(ModItems.UNHOLY_DUST))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.SPACE_FORGED_INGOT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.UNHOLY_WAND, 1)
                .pattern(" DC")
                .pattern(" TD")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('C', ModItems.CHRONOS_CLOCK)
                .input('D', ModItems.UNHOLY_DUST)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(ModItems.CHRONOS_CLOCK), conditionsFromItem(ModItems.CHRONOS_CLOCK))
                .criterion(hasItem(ModItems.UNHOLY_DUST), conditionsFromItem(ModItems.UNHOLY_DUST))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.UNHOLY_WAND)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_CAPTURING, 1)
                .pattern(" GD")
                .pattern(" TG")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('G', Items.GREEN_DYE)
                .input('D', Items.DIAMOND_BLOCK)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.GREEN_DYE), conditionsFromItem(Items.GREEN_DYE))
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_CAPTURING)));

        // TODO: Add generation station, space siphon, armor, and tool recipes

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DIVINE_WAND_OF_FLIGHT, 1)
                .pattern(" GE")
                .pattern(" TG")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('G', Items.GOLD_BLOCK)
                .input('E', Items.ELYTRA)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.GOLD_BLOCK), conditionsFromItem(Items.GOLD_BLOCK))
                .criterion(hasItem(Items.ELYTRA), conditionsFromItem(Items.ELYTRA))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.DIVINE_WAND_OF_FLIGHT)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MYSTIC_CHRONOGRAPH, 1)
                .pattern("TTT")
                .pattern("TCT")
                .pattern("TTT")
                .input('C', ModItems.CHRONOS_CLOCK)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .criterion(hasItem(ModItems.CHRONOS_CLOCK), conditionsFromItem(ModItems.CHRONOS_CLOCK))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.MYSTIC_CHRONOGRAPH)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DIVINE_REPLICATOR, 1)
                .pattern("FCF")
                .pattern("TMT")
                .pattern("FTF")
                .input('M', ModItems.MOB_CORE)
                .input('F', Items.CHORUS_FRUIT)
                .input('C', ModItems.CHRONOS_CLOCK)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .criterion(hasItem(ModItems.MOB_CORE), conditionsFromItem(ModItems.MOB_CORE))
                .criterion(hasItem(ModItems.CHRONOS_CLOCK), conditionsFromItem(ModItems.CHRONOS_CLOCK))
                .criterion(hasItem(Items.CHORUS_FRUIT), conditionsFromItem(Items.CHORUS_FRUIT))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.DIVINE_REPLICATOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MOB_CORE, 1)
                .pattern("UUU")
                .pattern("TRT")
                .pattern("UUU")
                .input('U', ModItems.UNHOLY_INGOT)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('R', Items.ROTTEN_FLESH)
                .criterion(hasItem(ModItems.UNHOLY_INGOT), conditionsFromItem(ModItems.UNHOLY_INGOT))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.MOB_CORE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SPEED_CORE, 1)
                .pattern("UUU")
                .pattern("TLT")
                .pattern("UUU")
                .input('U', ModItems.UNHOLY_INGOT)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('L', Items.LIME_DYE)
                .criterion(hasItem(ModItems.UNHOLY_INGOT), conditionsFromItem(ModItems.UNHOLY_INGOT))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(Items.LIME_DYE), conditionsFromItem(Items.LIME_DYE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.SPEED_CORE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.QUANTITY_CORE, 1)
                .pattern("UUU")
                .pattern("TLT")
                .pattern("UUU")
                .input('U', ModItems.UNHOLY_INGOT)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('L', Items.LIGHT_BLUE_DYE)
                .criterion(hasItem(ModItems.UNHOLY_INGOT), conditionsFromItem(ModItems.UNHOLY_INGOT))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(Items.LIME_DYE), conditionsFromItem(Items.LIME_DYE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.QUANTITY_CORE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SPEED_UPGRADE, 1)
                .pattern("EEE")
                .pattern("TST")
                .pattern("EEE")
                .input('S', ModItems.SPEED_CORE)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('E', Blocks.END_STONE_BRICKS)
                .criterion(hasItem(ModItems.SPEED_CORE), conditionsFromItem(ModItems.SPEED_CORE))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(Blocks.END_STONE_BRICKS), conditionsFromItem(Blocks.END_STONE_BRICKS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.SPEED_UPGRADE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.QUANTITY_UPGRADE, 1)
                .pattern("EEE")
                .pattern("TQT")
                .pattern("EEE")
                .input('Q', ModItems.QUANTITY_CORE)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('E', Blocks.END_STONE_BRICKS)
                .criterion(hasItem(ModItems.QUANTITY_CORE), conditionsFromItem(ModItems.QUANTITY_CORE))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(Blocks.END_STONE_BRICKS), conditionsFromItem(Blocks.END_STONE_BRICKS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.QUANTITY_UPGRADE)));

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

    public static void offerHelmetRecipe(RecipeExporter exporter, RecipeCategory category,
                                             ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .pattern("###")
                .pattern("# #")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerChestplateRecipe(RecipeExporter exporter, RecipeCategory category,
                                             ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerLeggingsRecipe(RecipeExporter exporter, RecipeCategory category,
                                             ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerBootsRecipe(RecipeExporter exporter, RecipeCategory category,
                                             ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .pattern("# #")
                .pattern("# #")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerPickaxeRecipe(RecipeExporter exporter, RecipeCategory category,
                                        ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .input(Character.valueOf('S'), Items.STICK)
                .pattern("###")
                .pattern(" S ")
                .pattern(" S ")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerAxeRecipe(RecipeExporter exporter, RecipeCategory category,
                                          ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .input(Character.valueOf('S'), Items.STICK)
                .pattern("##")
                .pattern("#S")
                .pattern(" S")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerShovelRecipe(RecipeExporter exporter, RecipeCategory category,
                                      ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .input(Character.valueOf('S'), Items.STICK)
                .pattern("#")
                .pattern("S")
                .pattern("S")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerSwordRecipe(RecipeExporter exporter, RecipeCategory category,
                                      ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .input(Character.valueOf('S'), Items.STICK)
                .pattern("#")
                .pattern("#")
                .pattern("S")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerHoeRecipe(RecipeExporter exporter, RecipeCategory category,
                                      ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('#'), input)
                .input(Character.valueOf('S'), Items.STICK)
                .pattern("##")
                .pattern(" S")
                .pattern(" S")
                .criterion(RecipeProvider.hasItem(input),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }
}
