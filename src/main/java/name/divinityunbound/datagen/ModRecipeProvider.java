package name.divinityunbound.datagen;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.fluid.ModFluids;
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
import net.minecraft.potion.Potions;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static List<ItemConvertible> CELESTITE_SMELTABLES = List.of(ModItems.RAW_CELESTITE,
            ModBlocks.CELESTITE_ORE, ModBlocks.DEEPSLATE_CELESTITE_ORE);

    private static List<ItemConvertible> EXPERIECE_SMELTABLES = List.of(ModItems.RAW_EXPERIENCE,
            ModBlocks.EXPERIENCE_ORE, ModBlocks.DEEPSLATE_EXPERIENCE_ORE);
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter, CELESTITE_SMELTABLES, RecipeCategory.MISC, ModItems.CELESTITE, 0.7f, 200, "celestite");
        offerBlasting(exporter, CELESTITE_SMELTABLES, RecipeCategory.MISC, ModItems.CELESTITE, 0.7f, 100, "celestite");
        offerSmelting(exporter, EXPERIECE_SMELTABLES, RecipeCategory.MISC, ModItems.EXPERIENCE_INGOT, 0.7f, 200, "celestite");
        offerBlasting(exporter, EXPERIECE_SMELTABLES, RecipeCategory.MISC, ModItems.EXPERIENCE_INGOT, 0.7f, 100, "celestite");
        offerSmelting(exporter, Arrays.asList(ModItems.UNHOLY_DUST), RecipeCategory.MISC, ModItems.UNHOLY_INGOT, 0.7f, 200, "celestite");
        offerBlasting(exporter, Arrays.asList(ModItems.UNHOLY_DUST), RecipeCategory.MISC, ModItems.UNHOLY_INGOT, 0.7f, 100, "celestite");
        offerSmelting(exporter, Arrays.asList(ModItems.SAND_OF_TIME), RecipeCategory.MISC, ModBlocks.FROZEN_TIME_GLASS, 0.7f, 200, "celestite");
        offerBlasting(exporter, Arrays.asList(ModItems.SAND_OF_TIME), RecipeCategory.MISC, ModBlocks.FROZEN_TIME_GLASS, 0.7f, 100, "celestite");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.CELESTITE, RecipeCategory.DECORATIONS, ModBlocks.CELESTITE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.RAW_CELESTITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_CELESTITE_BLOCK);
        //offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.CELESTITE_NUGGET, RecipeCategory.MISC, ModItems.CELESTITE);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.WILDERSUNG_LOG, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.WILDERSUNG_WOOD, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.STRIPPED_WILDERSUNG_LOG, "celestite",4);
        offerShapelessRecipe(exporter, ModBlocks.WILDERSUNG_PLANKS, ModBlocks.STRIPPED_WILDERSUNG_WOOD, "celestite",4);
        offer2x2CompactingRecipe(exporter, RecipeCategory.MISC, ModItems.WILDERSUNG_STRING, ModBlocks.WILDERSUNG_LEAVES);
        //offer2x2CompactingRecipe(exporter, RecipeCategory.MISC, ModBlocks.SAND_OF_TIME, ModItems.GRAIN_OF_TIME);
        offer2x2CompactingRecipe(exporter, RecipeCategory.MISC, ModBlocks.WILDERSUNG_SILK, ModItems.WILDERSUNG_STRING);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.GRAIN_OF_TIME, RecipeCategory.BUILDING_BLOCKS, ModItems.SAND_OF_TIME);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.CELESTIUM_DUST, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CELESTIUM_DUST_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.UNHOLY_DUST, RecipeCategory.BUILDING_BLOCKS, ModBlocks.UNHOLY_DUST_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.SPACE_DUST, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPACE_DUST_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.CONDENSED_MATTER_DUST, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CONDENSED_MATTER_DUST_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.DIVINE_TIME_DUST, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIVINE_TIME_DUST_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.DIVINE_SPACE_DUST, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIVINE_SPACE_DUST_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.TIME_FORGED_INGOT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIME_FORGED_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.SPACE_FORGED_INGOT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPACE_FORGED_BLOCK);

        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.CELESTITE_HELMET, ModItems.CELESTITE);
        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.CELESTITE_CHESTPLATE, ModItems.CELESTITE);
        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.CELESTITE_LEGGINGS, ModItems.CELESTITE);
        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.CELESTITE_BOOTS, ModItems.CELESTITE);

        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.TIME_FORGED_HELMET, ModItems.TIME_FORGED_INGOT);
        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.TIME_FORGED_CHESTPLATE, ModItems.TIME_FORGED_INGOT);
        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.TIME_FORGED_LEGGINGS, ModItems.TIME_FORGED_INGOT);
        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.TIME_FORGED_BOOTS, ModItems.TIME_FORGED_INGOT);

        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_FORGED_HELMET, ModItems.SPACE_FORGED_INGOT);
        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_FORGED_CHESTPLATE, ModItems.SPACE_FORGED_INGOT);
        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_FORGED_LEGGINGS, ModItems.SPACE_FORGED_INGOT);
        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_FORGED_BOOTS, ModItems.SPACE_FORGED_INGOT);

//        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_TIME_HELMET, ModItems.SPACE_TIME_INGOT);
//        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_TIME, ModItems.SPACE_TIME_INGOT);
//        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_TIME_LEGGINGS, ModItems.SPACE_TIME_INGOT);
//        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.SPACE_TIME_BOOTS, ModItems.SPACE_TIME_INGOT);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HERMES_BOOTS, 1)
                .pattern("F F")
                .pattern("E E")
                .pattern("T T")
                .input('F', Items.FEATHER)
                .input('E', ModItems.EXPERIENCE_INGOT)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .criterion(hasItem(Items.FEATHER), conditionsFromItem(Items.FEATHER))
                .criterion(hasItem(ModItems.EXPERIENCE_INGOT), conditionsFromItem(ModItems.EXPERIENCE_INGOT))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.HERMES_BOOTS)));

        offerHelmetRecipe(exporter, RecipeCategory.COMBAT, ModItems.EXPERIENCE_HELMET, ModItems.EXPERIENCE_INGOT);
        offerChestplateRecipe(exporter, RecipeCategory.COMBAT, ModItems.EXPERIENCE_CHESTPLATE, ModItems.EXPERIENCE_INGOT);
        offerLeggingsRecipe(exporter, RecipeCategory.COMBAT, ModItems.EXPERIENCE_LEGGINGS, ModItems.EXPERIENCE_INGOT);
        offerBootsRecipe(exporter, RecipeCategory.COMBAT, ModItems.EXPERIENCE_BOOTS, ModItems.EXPERIENCE_INGOT);

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

        offerPickaxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_PICKAXE, ModItems.SPACE_FORGED_INGOT);
        offerAxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_AXE, ModItems.SPACE_FORGED_INGOT);
        offerShovelRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_SHOVEL, ModItems.SPACE_FORGED_INGOT);
        offerSwordRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_SWORD, ModItems.SPACE_FORGED_INGOT);
        offerHoeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_HOE, ModItems.SPACE_FORGED_INGOT);

        offerPickaxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_PICKAXE, ModItems.SPACE_TIME_INGOT);
        offerAxeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_AXE, ModItems.SPACE_TIME_INGOT);
        offerShovelRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_SHOVEL, ModItems.SPACE_TIME_INGOT);
        offerSwordRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_SWORD, ModItems.SPACE_TIME_INGOT);
        offerHoeRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_HOE, ModItems.SPACE_TIME_INGOT);

        offerPaxelRecipe(exporter, RecipeCategory.TOOLS, ModItems.CELESTITE_PAXEL,
                ModItems.CELESTITE_PICKAXE, ModItems.CELESTITE_AXE, ModItems.CELESTITE_SHOVEL);
        offerPaxelRecipe(exporter, RecipeCategory.TOOLS, ModItems.TIME_FORGED_PAXEL,
                ModItems.TIME_FORGED_PICKAXE, ModItems.TIME_FORGED_AXE, ModItems.TIME_FORGED_SHOVEL);
        offerPaxelRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_FORGED_PAXEL,
                ModItems.SPACE_FORGED_PICKAXE, ModItems.SPACE_FORGED_AXE, ModItems.SPACE_FORGED_SHOVEL);
        offerPaxelRecipe(exporter, RecipeCategory.TOOLS, ModItems.SPACE_TIME_PAXEL,
                ModItems.SPACE_TIME_PICKAXE, ModItems.SPACE_TIME_AXE, ModItems.SPACE_TIME_SHOVEL);

        // TODO: Add recipes for:
        // Space Time Furnace

        // TODO: Make some helper methods to clean up these Json Builders
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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FILTER_ITEM, 4)
                .input(ModItems.GRAIN_OF_TIME)
                .input(Items.PAPER)
                .criterion(hasItem(ModItems.GRAIN_OF_TIME), conditionsFromItem((ModItems.GRAIN_OF_TIME)))
                .criterion(hasItem(Items.PAPER), conditionsFromItem((Items.PAPER)))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.FILTER_ITEM)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CELESTITE_INFUSED_STONE, 1)
                .input(ModItems.CELESTITE)
                .input(Blocks.STONE)
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem((ModItems.CELESTITE)))
                .criterion(hasItem(Blocks.STONE), conditionsFromItem((Blocks.STONE)))
                .offerTo(exporter, new Identifier("celestite_infused_stone"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WILDERSUNG_SAPLING, 1)
                .input(Items.OAK_SAPLING)
                .input(ModItems.CELESTITE)
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem((ModItems.CELESTITE)))
                .criterion(hasItem(Items.OAK_SAPLING), conditionsFromItem((Items.OAK_SAPLING)))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.WILDERSUNG_SAPLING)));

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DIVINE_TORCH, 4)
                .pattern("C")
                .pattern("S")
                .input('S', Items.STICK)
                .input('C', ModItems.CELESTIUM_DUST)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.CELESTIUM_DUST), conditionsFromItem(ModItems.CELESTIUM_DUST))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.DIVINE_TORCH)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FROZEN_TIME_LAMP, 1)
                .pattern(" F ")
                .pattern("FGF")
                .pattern(" F ")
                .input('G', Items.GLOWSTONE_DUST)
                .input('F', ModBlocks.FROZEN_TIME_GLASS)
                .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                .criterion(hasItem(ModBlocks.FROZEN_TIME_GLASS), conditionsFromItem(ModBlocks.FROZEN_TIME_GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FROZEN_TIME_LAMP)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ARCANE_FLOOR_LAMP, 1)
                .pattern("FGF")
                .input('G', Items.GLOWSTONE_DUST)
                .input('F', ModBlocks.FROZEN_TIME_GLASS)
                .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                .criterion(hasItem(ModBlocks.FROZEN_TIME_GLASS), conditionsFromItem(ModBlocks.FROZEN_TIME_GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.ARCANE_FLOOR_LAMP)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.UNHOLY_GRASS_BLOCK, 2)
                .pattern("UUU")
                .pattern("UGU")
                .pattern("UUU")
                .input('U', ModBlocks.UNHOLY_DUST_BLOCK)
                .input('G', Blocks.GRASS_BLOCK)
                .criterion(hasItem(ModBlocks.UNHOLY_DUST_BLOCK), conditionsFromItem(ModBlocks.UNHOLY_DUST_BLOCK))
                .criterion(hasItem(Blocks.GRASS_BLOCK), conditionsFromItem(Blocks.GRASS_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.UNHOLY_GRASS_BLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PROTEUS_CONTROLLER_BLOCK, 1)
                .pattern(" S ")
                .pattern(" C ")
                .pattern("GGG")
                .input('G', Items.GOLD_INGOT)
                .input('C', ModItems.CELESTITE)
                .input('S', ModItems.SPACE_TIME_INGOT)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .criterion(hasItem(ModItems.SPACE_TIME_INGOT), conditionsFromItem(ModItems.SPACE_TIME_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PROTEUS_CONTROLLER_BLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CELESTIAL_GLASS, 4)
                .pattern(" C ")
                .pattern("EFE")
                .pattern(" C ")
                .input('E', Items.ENDER_PEARL)
                .input('F', ModBlocks.FROZEN_TIME_GLASS)
                .input('C', ModItems.CELESTIUM_DUST)
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .criterion(hasItem(ModItems.CELESTIUM_DUST), conditionsFromItem(ModItems.CELESTIUM_DUST))
                .criterion(hasItem(ModBlocks.FROZEN_TIME_GLASS), conditionsFromItem(ModBlocks.FROZEN_TIME_GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.CELESTIAL_GLASS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DARK_CELESTIAL_GLASS, 2)
                .pattern(" A ")
                .pattern("ACA")
                .pattern(" A ")
                .input('A', Items.AMETHYST_SHARD)
                .input('C', ModBlocks.CELESTIAL_GLASS)
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .criterion(hasItem(ModBlocks.CELESTIAL_GLASS), conditionsFromItem(ModBlocks.CELESTIAL_GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.DARK_CELESTIAL_GLASS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WITHERED_GLASS, 4)
                .pattern("GOG")
                .pattern("OBO")
                .pattern("GOG")
                .input('B', Items.BLACK_DYE)
                .input('O', Blocks.OBSIDIAN)
                .input('G', ModBlocks.FROZEN_TIME_GLASS)
                .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
                .criterion(hasItem(Blocks.OBSIDIAN), conditionsFromItem(Blocks.OBSIDIAN))
                .criterion(hasItem(ModBlocks.FROZEN_TIME_GLASS), conditionsFromItem(ModBlocks.FROZEN_TIME_GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.WITHERED_GLASS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.IMPORT_CARD, 3)
                .pattern("CCC")
                .pattern("CUC")
                .pattern("CBC")
                .input('C', Items.CLAY)
                .input('B', Items.BLUE_DYE)
                .input('U', ModItems.UNHOLY_DUST)
                .criterion(hasItem(Items.CLAY), conditionsFromItem(Items.CLAY))
                .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
                .criterion(hasItem(ModItems.UNHOLY_DUST), conditionsFromItem(ModItems.UNHOLY_DUST))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.IMPORT_CARD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EXPORT_CARD, 3)
                .pattern("CCC")
                .pattern("CUC")
                .pattern("COC")
                .input('C', Items.CLAY)
                .input('O', Items.ORANGE_DYE)
                .input('U', ModItems.UNHOLY_DUST)
                .criterion(hasItem(Items.CLAY), conditionsFromItem(Items.CLAY))
                .criterion(hasItem(Items.ORANGE_DYE), conditionsFromItem(Items.ORANGE_DYE))
                .criterion(hasItem(ModItems.UNHOLY_DUST), conditionsFromItem(ModItems.UNHOLY_DUST))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.EXPORT_CARD)));

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
                .pattern("GSG")
                .pattern("SCS")
                .pattern("GSG")
                .input('G', ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE)
                .input('S', ModBlocks.CELESTITE_INFUSED_STONE)
                .input('C', ModItems.CHRONOS_CLOCK)
                .criterion(hasItem(ModBlocks.CELESTITE_INFUSED_STONE), conditionsFromItem(ModBlocks.CELESTITE_INFUSED_STONE))
                .criterion(hasItem(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE), conditionsFromItem(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE))
                .criterion(hasItem(ModItems.CHRONOS_CLOCK), conditionsFromItem(ModItems.CHRONOS_CLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.CHRONOS_TIME_ACCUMULATOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ITEM_TRASHCAN, 1)
                .pattern("BBB")
                .pattern("GLG")
                .pattern("BGB")
                .input('G', Blocks.GREEN_TERRACOTTA)
                .input('B', Blocks.BLACK_TERRACOTTA)
                .input('L', Items.LAVA_BUCKET)
                .criterion(hasItem(Blocks.GREEN_TERRACOTTA), conditionsFromItem(Blocks.GREEN_TERRACOTTA))
                .criterion(hasItem(Blocks.BLACK_TERRACOTTA), conditionsFromItem(Blocks.BLACK_TERRACOTTA))
                .criterion(hasItem(Items.LAVA_BUCKET), conditionsFromItem(Items.LAVA_BUCKET))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.ITEM_TRASHCAN)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FLUID_TRASHCAN, 1)
                .pattern("BBB")
                .pattern("ULU")
                .pattern("BUB")
                .input('U', Blocks.BLUE_TERRACOTTA)
                .input('B', Blocks.BLACK_TERRACOTTA)
                .input('L', Items.LAVA_BUCKET)
                .criterion(hasItem(Blocks.BLUE_TERRACOTTA), conditionsFromItem(Blocks.BLUE_TERRACOTTA))
                .criterion(hasItem(Blocks.BLACK_TERRACOTTA), conditionsFromItem(Blocks.BLACK_TERRACOTTA))
                .criterion(hasItem(Items.LAVA_BUCKET), conditionsFromItem(Items.LAVA_BUCKET))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FLUID_TRASHCAN)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ENERGY_TRASHCAN, 1)
                .pattern("BBB")
                .pattern("RLR")
                .pattern("BRB")
                .input('R', Blocks.RED_TERRACOTTA)
                .input('B', Blocks.BLACK_TERRACOTTA)
                .input('L', Items.LAVA_BUCKET)
                .criterion(hasItem(Blocks.RED_TERRACOTTA), conditionsFromItem(Blocks.RED_TERRACOTTA))
                .criterion(hasItem(Blocks.BLACK_TERRACOTTA), conditionsFromItem(Blocks.BLACK_TERRACOTTA))
                .criterion(hasItem(Items.LAVA_BUCKET), conditionsFromItem(Items.LAVA_BUCKET))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.ENERGY_TRASHCAN)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.KNOWLEDGE_EXTRACTOR, 1)
                .pattern("SBS")
                .pattern("G G")
                .pattern("WGW")
                .input('S', Blocks.BOOKSHELF)
                .input('G', Blocks.GLASS)
                .input('W', Blocks.OAK_PLANKS)
                .input('B', Items.BOOK)
                .criterion(hasItem(Blocks.BOOKSHELF), conditionsFromItem(Blocks.BOOKSHELF))
                .criterion(hasItem(Blocks.GLASS), conditionsFromItem(Blocks.GLASS))
                .criterion(hasItem(Blocks.OAK_PLANKS), conditionsFromItem(Blocks.OAK_PLANKS))
                .criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.KNOWLEDGE_EXTRACTOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.COAL_GENERATOR, 1)
                .pattern("SLS")
                .pattern("TIT")
                .pattern("TTT")
                .input('S', Items.STICK)
                .input('L', Blocks.OAK_LOG)
                .input('T', Blocks.STONE)
                .input('I', Items.IRON_BARS)
                .criterion(hasItem(Blocks.OAK_LOG), conditionsFromItem(Blocks.OAK_LOG))
                .criterion(hasItem(Blocks.STONE), conditionsFromItem(Blocks.STONE))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.IRON_BARS), conditionsFromItem(Items.IRON_BARS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.COAL_GENERATOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.HALLOWED_FLUID_TANK, 1)
                .pattern("GBG")
                .pattern("BMB")
                .pattern("GBG")
                .input('B', Blocks.POLISHED_BLACKSTONE)
                .input('M', ModBlocks.MINI_GLASS)
                .input('G', ModItems.GOLD_BAND)
                .criterion(hasItem(Blocks.POLISHED_BLACKSTONE), conditionsFromItem(Blocks.POLISHED_BLACKSTONE))
                .criterion(hasItem(ModBlocks.MINI_GLASS), conditionsFromItem(ModBlocks.MINI_GLASS))
                .criterion(hasItem(ModItems.GOLD_BAND), conditionsFromItem(ModItems.GOLD_BAND))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.HALLOWED_FLUID_TANK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MINI_GLASS, 4)
                .pattern("GGG")
                .pattern("GTG")
                .pattern("GGG")
                .input('T', ModBlocks.FROZEN_TIME_GLASS)
                .input('G', Items.GLASS)
                .criterion(hasItem(ModBlocks.FROZEN_TIME_GLASS), conditionsFromItem(ModBlocks.FROZEN_TIME_GLASS))
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.MINI_GLASS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GOLD_BAND, 4)
                .pattern(" G ")
                .pattern("GCG")
                .pattern(" G ")
                .input('C', ModItems.CELESTITE)
                .input('G', Items.GOLD_INGOT)
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GOLD_BAND)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BATTERY_CORE, 2)
                .pattern("B")
                .pattern("C")
                .pattern("B")
                .input('C', ModBlocks.CELESTITE_BLOCK)
                .input('B', Blocks.COAL_BLOCK)
                .criterion(hasItem(ModBlocks.CELESTITE_BLOCK), conditionsFromItem(ModBlocks.CELESTITE_BLOCK))
                .criterion(hasItem(Blocks.COAL_BLOCK), conditionsFromItem(Blocks.COAL_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.BATTERY_CORE)));

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
                .offerTo(exporter, new Identifier("craft_time_forged_ingot"));

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
                .offerTo(exporter, new Identifier("craft_space_forged_ingot"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_BINDING, 1)
                .pattern("U U")
                .pattern(" T ")
                .pattern("U U")
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('U', ModItems.UNHOLY_INGOT)
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(ModItems.UNHOLY_INGOT), conditionsFromItem(ModItems.UNHOLY_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_BINDING)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.UNHOLY_WAND, 1)
                .pattern(" UC")
                .pattern(" WU")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('W', ModItems.WAND_BINDING)
                .input('C', ModItems.CHRONOS_CLOCK)
                .input('U', ModItems.UNHOLY_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.WAND_BINDING), conditionsFromItem(ModItems.WAND_BINDING))
                .criterion(hasItem(ModItems.CHRONOS_CLOCK), conditionsFromItem(ModItems.CHRONOS_CLOCK))
                .criterion(hasItem(ModItems.UNHOLY_DUST), conditionsFromItem(ModItems.UNHOLY_DUST))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.UNHOLY_WAND)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_CAPTURING, 1)
                .pattern(" GD")
                .pattern(" WG")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('G', Items.GREEN_DYE)
                .input('D', Items.DIAMOND_BLOCK)
                .input('W', ModItems.WAND_BINDING)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.GREEN_DYE), conditionsFromItem(Items.GREEN_DYE))
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK))
                .criterion(hasItem(ModItems.WAND_BINDING), conditionsFromItem(ModItems.WAND_BINDING))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_CAPTURING)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DIVINE_WAND_OF_FLIGHT, 1)
                .pattern(" GE")
                .pattern(" TG")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('G', Items.GOLD_BLOCK)
                .input('E', Items.ELYTRA)
                .input('T', ModItems.SPACE_FORGED_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.GOLD_BLOCK), conditionsFromItem(Items.GOLD_BLOCK))
                .criterion(hasItem(Items.ELYTRA), conditionsFromItem(Items.ELYTRA))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.DIVINE_WAND_OF_FLIGHT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_RESPIRATION, 1)
                .pattern(" LP")
                .pattern(" WL")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('L', Items.LAPIS_BLOCK)
                .input('P', Items.PUFFERFISH)
                .input('W', ModItems.WAND_BINDING)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.LAPIS_BLOCK), conditionsFromItem(Items.LAPIS_BLOCK))
                .criterion(hasItem(Items.PUFFERFISH), conditionsFromItem(Items.PUFFERFISH))
                .criterion(hasItem(ModItems.WAND_BINDING), conditionsFromItem(ModItems.WAND_BINDING))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_RESPIRATION)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_TELEPORTATION, 1)
                .pattern(" DE")
                .pattern(" TD")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('D', Items.DIAMOND_BLOCK)
                .input('E', Items.ENDER_PEARL)
                .input('T', ModItems.SPACE_FORGED_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK))
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_TELEPORTATION)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_THE_ARCHER, 1)
                .pattern(" BA")
                .pattern(" TB")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('A', Items.ARROW)
                .input('B', Items.BOW)
                .input('T', ModItems.SPACE_FORGED_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.BOW), conditionsFromItem(Items.BOW))
                .criterion(hasItem(Items.ARROW), conditionsFromItem(Items.ARROW))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_THE_ARCHER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_CELEBRATION, 1)
                .pattern(" GP")
                .pattern(" TG")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('G', Items.GUNPOWDER)
                .input('P', Items.PAPER)
                .input('T', ModItems.SPACE_FORGED_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.GUNPOWDER), conditionsFromItem(Items.GUNPOWDER))
                .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_CELEBRATION)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_FIRE_BENDING, 1)
                .pattern(" RB")
                .pattern(" WR")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('B', Items.LAVA_BUCKET)
                .input('R', Items.REDSTONE_BLOCK)
                .input('W', ModItems.WAND_BINDING)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.LAVA_BUCKET), conditionsFromItem(Items.LAVA_BUCKET))
                .criterion(hasItem(Items.REDSTONE_BLOCK), conditionsFromItem(Items.REDSTONE_BLOCK))
                .criterion(hasItem(ModItems.WAND_BINDING), conditionsFromItem(ModItems.WAND_BINDING))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_FIRE_BENDING)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_MAGNETIZATION, 1)
                .pattern(" RI")
                .pattern("BWR")
                .pattern("IB ")
                .input('I', Items.IRON_INGOT)
                .input('R', Items.RED_DYE)
                .input('B', Items.BLUE_DYE)
                .input('W', ModItems.WAND_BINDING)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
                .criterion(hasItem(ModItems.WAND_BINDING), conditionsFromItem(ModItems.WAND_BINDING))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_MAGNETIZATION)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND_OF_HEALING, 1)
                .pattern(" PG")
                .pattern(" WP")
                .pattern("S  ")
                .input('G', Items.GOLDEN_APPLE)
                .input('P', Items.PINK_DYE)
                .input('S', Items.STICK)
                .input('W', ModItems.WAND_BINDING)
                .criterion(hasItem(Items.GOLDEN_APPLE), conditionsFromItem(Items.GOLDEN_APPLE))
                .criterion(hasItem(Items.PINK_DYE), conditionsFromItem(Items.PINK_DYE))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.WAND_BINDING), conditionsFromItem(ModItems.WAND_BINDING))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND_OF_HEALING)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GREATER_WAND_OF_HEALING, 1)
                .pattern(" PG")
                .pattern(" WP")
                .pattern("S  ")
                .input('G', Items.ENCHANTED_GOLDEN_APPLE)
                .input('P', Items.PINK_DYE)
                .input('S', Items.STICK)
                .input('W', ModItems.WAND_BINDING)
                .criterion(hasItem(Items.GOLDEN_APPLE), conditionsFromItem(Items.ENCHANTED_GOLDEN_APPLE))
                .criterion(hasItem(Items.PINK_DYE), conditionsFromItem(Items.PINK_DYE))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.WAND_BINDING), conditionsFromItem(ModItems.WAND_BINDING))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GREATER_WAND_OF_HEALING)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MYSTIC_CHRONOGRAPH, 1)
                .pattern("STS")
                .pattern("TCT")
                .pattern("STS")
                .input('C', ModItems.CHRONOS_CLOCK)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('S', ModItems.SPACE_TIME_INGOT)
                .criterion(hasItem(ModItems.CHRONOS_CLOCK), conditionsFromItem(ModItems.CHRONOS_CLOCK))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(ModItems.SPACE_TIME_INGOT), conditionsFromItem(ModItems.SPACE_TIME_INGOT))
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MOB_ATTRACTOR, 1)
                .pattern("RMR")
                .pattern("SGS")
                .pattern("RSR")
                .input('G', Blocks.GRASS_BLOCK)
                .input('R', Items.RED_DYE)
                .input('S', ModItems.SPACE_FORGED_INGOT)
                .input('M', ModItems.MOB_CORE)
                .criterion(hasItem(ModItems.MOB_CORE), conditionsFromItem(ModItems.MOB_CORE))
                .criterion(hasItem(Blocks.GRASS_BLOCK), conditionsFromItem(Blocks.GRASS_BLOCK))
                .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.MOB_ATTRACTOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GENERATION_STATION, 1)
                .pattern("TFT")
                .pattern("GCG")
                .pattern("SFS")
                .input('C', ModBlocks.CELESTITE_BLOCK)
                .input('F', ModBlocks.FROZEN_TIME_GLASS)
                .input('G', Blocks.GOLD_BLOCK)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('S', ModItems.SPACE_FORGED_INGOT)
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(ModBlocks.CELESTITE_BLOCK), conditionsFromItem(ModBlocks.CELESTITE_BLOCK))
                .criterion(hasItem(ModBlocks.FROZEN_TIME_GLASS), conditionsFromItem(ModBlocks.FROZEN_TIME_GLASS))
                .criterion(hasItem(Blocks.GOLD_BLOCK), conditionsFromItem(Blocks.GOLD_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.GENERATION_STATION)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SPACE_SIPHON, 1)
                .pattern("TST")
                .pattern("SCS")
                .pattern(" H ")
                .input('H', Items.HOPPER)
                .input('C', ModBlocks.CELESTITE_BLOCK)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('S', ModItems.SPACE_FORGED_INGOT)
                .criterion(hasItem(ModBlocks.CELESTITE_BLOCK), conditionsFromItem(ModBlocks.CELESTITE_BLOCK))
                .criterion(hasItem(Items.HOPPER), conditionsFromItem(Items.HOPPER))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.SPACE_SIPHON)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.UNHOLY_SILENCER, 1)
                .pattern("UDU")
                .pattern("WMW")
                .pattern("UIU")
                .input('W', Items.WITHER_SKELETON_SKULL)
                .input('D', Items.DIAMOND_SWORD)
                .input('I', Items.IRON_BLOCK)
                .input('M', ModItems.MOB_CORE)
                .input('U', ModItems.UNHOLY_INGOT)
                .criterion(hasItem(Items.WITHER_SKELETON_SKULL), conditionsFromItem(Items.WITHER_SKELETON_SKULL))
                .criterion(hasItem(Items.DIAMOND_SWORD), conditionsFromItem(Items.DIAMOND_SWORD))
                .criterion(hasItem(Items.IRON_BLOCK), conditionsFromItem(Items.IRON_BLOCK))
                .criterion(hasItem(ModItems.MOB_CORE), conditionsFromItem(ModItems.MOB_CORE))
                .criterion(hasItem(ModItems.UNHOLY_INGOT), conditionsFromItem(ModItems.UNHOLY_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.UNHOLY_SILENCER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WORMHOLE_TRANSPORTER, 2)
                .pattern("CS ")
                .pattern("COE")
                .pattern("CT ")
                .input('C', Blocks.CRYING_OBSIDIAN)
                .input('O', Blocks.OBSIDIAN)
                .input('E', Items.ENDER_PEARL)
                .input('S', ModItems.SPACE_FORGED_INGOT)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .criterion(hasItem(Blocks.CRYING_OBSIDIAN), conditionsFromItem(Blocks.CRYING_OBSIDIAN))
                .criterion(hasItem(Blocks.OBSIDIAN), conditionsFromItem(Blocks.OBSIDIAN))
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.WORMHOLE_TRANSPORTER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SPACE_TIME_EVAPORATOR, 1)
                .pattern("SNS")
                .pattern(" B ")
                .pattern("SIS")
                .input('N', Items.NETHER_STAR)
                .input('B', ModFluids.SPACE_TIME_BUCKET)
                .input('S', ModItems.SPACE_TIME_INGOT)
                .input('I', ModItems.SPACE_FORGED_INGOT)
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .criterion(hasItem(ModFluids.SPACE_TIME_BUCKET), conditionsFromItem(ModFluids.SPACE_TIME_BUCKET))
                .criterion(hasItem(ModItems.SPACE_TIME_INGOT), conditionsFromItem(ModItems.SPACE_TIME_INGOT))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.SPACE_TIME_EVAPORATOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SPACE_TIME_AMALGAMATOR, 1)
                .pattern("SGT")
                .pattern("BBB")
                .pattern("W W")
                .input('G', Items.GLASS_BOTTLE)
                .input('S', ModItems.SPACE_FORGED_INGOT)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('B', Items.BLACKSTONE)
                .input('W', ModBlocks.WILDERSUNG_PLANKS)
                .criterion(hasItem(Items.GLASS_BOTTLE), conditionsFromItem(Items.GLASS_BOTTLE))
                .criterion(hasItem(Items.BLACKSTONE), conditionsFromItem(Items.BLACKSTONE))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .criterion(hasItem(ModBlocks.WILDERSUNG_PLANKS), conditionsFromItem(ModBlocks.WILDERSUNG_PLANKS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.SPACE_TIME_AMALGAMATOR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ZEUS_BATTERY, 1)
                .pattern(" I ")
                .pattern("CBC")
                .pattern("CiC")
                .input('I', Blocks.IRON_BLOCK)
                .input('i', Items.IRON_INGOT)
                .input('C', Blocks.COPPER_BLOCK)
                .input('B', ModItems.BATTERY_CORE)
                .criterion(hasItem(Blocks.IRON_BLOCK), conditionsFromItem(Blocks.IRON_BLOCK))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ModItems.BATTERY_CORE), conditionsFromItem(ModItems.BATTERY_CORE))
                .criterion(hasItem(Blocks.COPPER_BLOCK), conditionsFromItem(Blocks.COPPER_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.ZEUS_BATTERY)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DEMETERS_HARVESTER, 1)
                .pattern(" G ")
                .pattern("HPH")
                .pattern("CPZ")
                .input('G', Items.GOLDEN_APPLE)
                .input('H', Items.DIAMOND_HOE)
                .input('P', Blocks.QUARTZ_PILLAR)
                .input('C', Blocks.CHEST)
                .input('Z', ModBlocks.ZEUS_BATTERY)
                .criterion(hasItem(Items.GOLDEN_APPLE), conditionsFromItem(Items.GOLDEN_APPLE))
                .criterion(hasItem(Items.DIAMOND_HOE), conditionsFromItem(Items.DIAMOND_HOE))
                .criterion(hasItem(Blocks.QUARTZ_PILLAR), conditionsFromItem(Blocks.QUARTZ_PILLAR))
                .criterion(hasItem(Blocks.CHEST), conditionsFromItem(Blocks.CHEST))
                .criterion(hasItem(ModBlocks.ZEUS_BATTERY), conditionsFromItem(ModBlocks.ZEUS_BATTERY))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.DEMETERS_HARVESTER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PERSEPHONES_BLESSING, 1)
                .pattern("DRD")
                .pattern("MMM")
                .pattern("SES")
                .input('D', Items.OXEYE_DAISY)
                .input('R', Items.RED_TULIP)
                .input('M', Blocks.MOSS_CARPET)
                .input('S', ModItems.SPACE_TIME_INGOT)
                .input('E', ModItems.EXPERIENCE_INGOT)
                .criterion(hasItem(ModItems.SPACE_TIME_INGOT), conditionsFromItem(ModItems.SPACE_TIME_INGOT))
                .criterion(hasItem(ModItems.EXPERIENCE_INGOT), conditionsFromItem(ModItems.EXPERIENCE_INGOT))
                .criterion(hasItem(Items.OXEYE_DAISY), conditionsFromItem(Items.OXEYE_DAISY))
                .criterion(hasItem(Items.RED_TULIP), conditionsFromItem(Items.RED_TULIP))
                .criterion(hasItem(Blocks.MOSS_CARPET), conditionsFromItem(Blocks.MOSS_CARPET))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PERSEPHONES_BLESSING)));

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RANGE_CORE, 1)
                .pattern("UUU")
                .pattern("TRT")
                .pattern("UUU")
                .input('U', ModItems.UNHOLY_INGOT)
                .input('T', ModItems.TIME_FORGED_INGOT)
                .input('R', Items.RED_DYE)
                .criterion(hasItem(ModItems.UNHOLY_INGOT), conditionsFromItem(ModItems.UNHOLY_INGOT))
                .criterion(hasItem(ModItems.TIME_FORGED_INGOT), conditionsFromItem(ModItems.TIME_FORGED_INGOT))
                .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RANGE_CORE)));

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RANGE_UPGRADE, 1)
                .pattern("EEE")
                .pattern("SRS")
                .pattern("EEE")
                .input('R', ModItems.RANGE_CORE)
                .input('S', ModItems.SPACE_FORGED_INGOT)
                .input('E', Blocks.END_STONE_BRICKS)
                .criterion(hasItem(ModItems.RANGE_CORE), conditionsFromItem(ModItems.RANGE_CORE))
                .criterion(hasItem(ModItems.SPACE_FORGED_INGOT), conditionsFromItem(ModItems.SPACE_FORGED_INGOT))
                .criterion(hasItem(Blocks.END_STONE_BRICKS), conditionsFromItem(Blocks.END_STONE_BRICKS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.RANGE_UPGRADE)));

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE_REDSTONE_FOCUS, 1)
                .pattern("CGC")
                .pattern("GBG")
                .pattern("CGC")
                .input('G', Items.GOLD_INGOT)
                .input('B', Items.REDSTONE_BLOCK)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.REDSTONE_BLOCK), conditionsFromItem(Items.REDSTONE_BLOCK))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CELESTITE_REDSTONE_FOCUS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CELESTITE_LAPIS_LAZULI_FOCUS, 1)
                .pattern("CGC")
                .pattern("GBG")
                .pattern("CGC")
                .input('G', Items.GOLD_INGOT)
                .input('B', Items.LAPIS_BLOCK)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.LAPIS_BLOCK), conditionsFromItem(Items.LAPIS_BLOCK))
                .criterion(hasItem(ModItems.CELESTITE), conditionsFromItem(ModItems.CELESTITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CELESTITE_LAPIS_LAZULI_FOCUS)));

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
                .pattern("CNC")
                .pattern("GBG")
                .pattern("CGC")
                .input('G', Items.GOLD_INGOT)
                .input('B', Items.NETHERITE_BLOCK)
                .input('N', Items.NETHER_STAR)
                .input('C', ModItems.CELESTITE)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
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

    public static void offerPaxelRecipe(RecipeExporter exporter, RecipeCategory category,
                                          ItemConvertible output, ItemConvertible pickaxe,
                                          ItemConvertible axe, ItemConvertible shovel) {
        ShapedRecipeJsonBuilder.create(category, output, 1)
                .input(Character.valueOf('P'), pickaxe)
                .input(Character.valueOf('A'), axe)
                .input(Character.valueOf('S'), shovel)
                .input(Character.valueOf('#'), Items.STICK)
                .pattern("PAS")
                .pattern(" # ")
                .pattern(" # ")
                .criterion(RecipeProvider.hasItem(pickaxe),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(pickaxe))
                .criterion(RecipeProvider.hasItem(axe),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(axe))
                .criterion(RecipeProvider.hasItem(shovel),
                        (AdvancementCriterion)RecipeProvider.conditionsFromItem(shovel))
                .offerTo(exporter);
    }
}
