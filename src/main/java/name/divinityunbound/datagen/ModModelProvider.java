package name.divinityunbound.datagen;

import com.ibm.icu.text.Normalizer2;
import dev.architectury.platform.Mod;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.item.client.SpaceSiphonItemModel;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.item.ArmorItem;

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
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.EXPERIENCE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_EXPERIENCE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CELESTITE_INFUSED_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FROZEN_TIME_GLASS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SAND_OF_TIME);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FROZEN_TIME_LAMP);
        blockStateModelGenerator.registerSimpleState(ModBlocks.DIVINE_TORCH);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.WALL_DIVINE_TORCH);

        blockStateModelGenerator.registerLog(ModBlocks.WILDERSUNG_LOG).log(ModBlocks.WILDERSUNG_LOG).wood(ModBlocks.WILDERSUNG_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_WILDERSUNG_LOG).log(ModBlocks.STRIPPED_WILDERSUNG_LOG).wood(ModBlocks.STRIPPED_WILDERSUNG_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.WILDERSUNG_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CELESTITE_INFUSED_WILDERSUNG_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.WILDERSUNG_LEAVES);
        blockStateModelGenerator.registerTintableCross(ModBlocks.WILDERSUNG_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        //blockStateModelGenerator.registerSimpleState(ModBlocks.GENERATION_STATION);
        //blockStateModelGenerator.registerSimpleState(ModBlocks.MYSTIC_CHRONOGRAPH);
        //blockStateModelGenerator.registerSimpleState(ModBlocks.DIVINE_REPLICATOR);
        blockStateModelGenerator.registerSimpleState(ModBlocks.SPEED_UPGRADE);
        blockStateModelGenerator.registerSimpleState(ModBlocks.QUANTITY_UPGRADE);
        //blockStateModelGenerator.registerSimpleState(ModBlocks.SPACE_SIPHON);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHRONOS_TIME_ACCUMULATOR);
        blockStateModelGenerator.registerSimpleState(ModBlocks.UNHOLY_SILENCER);
        //blockStateModelGenerator.registerSimpleState(ModBlocks.SPACE_TIME_EVAPORATOR);
        //blockStateModelGenerator.registerSimpleState(ModBlocks.SPACE_TIME_AMALGAMATOR);

        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.GENERATION_STATION);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.MYSTIC_CHRONOGRAPH);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.DIVINE_REPLICATOR);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.SPACE_TIME_EVAPORATOR);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.SPACE_TIME_AMALGAMATOR);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.WORMHOLE_TRANSPORTER);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.ITEM_TRASHCAN);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.FLUID_TRASHCAN);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.ENERGY_TRASHCAN);

        blockStateModelGenerator.registerCooker(ModBlocks.SPACE_TIME_FURNACE, TexturedModel.ORIENTABLE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CELESTITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_CELESTITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_EXPERIENCE, Models.GENERATED);
        //itemModelGenerator.register(ModItems.MAGIC_CELESTITE_DETECTOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTIUM_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.GRAIN_OF_TIME, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHRONOS_CLOCK, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNHOLY_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.WILDERSUNG_STRING, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIME_FORGED_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNHOLY_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.EXPERIENCE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_FORGED_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_FUEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_TIME_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPEED_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.QUANTITY_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOB_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.IMPORT_CARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.EXPORT_CARD, Models.GENERATED);

        itemModelGenerator.register(ModFluids.SPACE_TIME_BUCKET, Models.GENERATED);

        /* Wands */
        itemModelGenerator.register(ModItems.WAND_BINDING, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNHOLY_WAND, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIVINE_WAND_OF_FLIGHT, Models.GENERATED);
        itemModelGenerator.register(ModItems.WAND_OF_CAPTURING, Models.GENERATED);
        itemModelGenerator.register(ModItems.WAND_OF_RESPIRATION, Models.GENERATED);
        itemModelGenerator.register(ModItems.WAND_OF_TELEPORTATION, Models.GENERATED);
        itemModelGenerator.register(ModItems.WAND_OF_THE_ARCHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.WAND_OF_CELEBRATION, Models.GENERATED);
        itemModelGenerator.register(ModItems.WAND_OF_FIRE_BENDING, Models.GENERATED);

        /* Tools */
        itemModelGenerator.register(ModItems.CELESTITE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTITE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTITE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.CELESTITE_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.TIME_FORGED_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TIME_FORGED_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TIME_FORGED_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TIME_FORGED_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TIME_FORGED_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.SPACE_FORGED_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_FORGED_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_FORGED_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_FORGED_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_FORGED_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.SPACE_TIME_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_TIME_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_TIME_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_TIME_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_TIME_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.CELESTITE_PAXEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TIME_FORGED_PAXEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_FORGED_PAXEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPACE_TIME_PAXEL, Models.HANDHELD);

        /* Armor */
        itemModelGenerator.registerArmor((ArmorItem) ModItems.CELESTITE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.CELESTITE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.CELESTITE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.CELESTITE_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.TIME_FORGED_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.TIME_FORGED_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.TIME_FORGED_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.TIME_FORGED_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.SPACE_FORGED_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.SPACE_FORGED_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.SPACE_FORGED_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.SPACE_FORGED_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.EXPERIENCE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.EXPERIENCE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.EXPERIENCE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.EXPERIENCE_BOOTS);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.HERMES_BOOTS);

        /* Foci */
        itemModelGenerator.register(ModItems.CELESTITE_COAL_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_IRON_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_GOLD_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_DIAMOND_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_NETHERITE_FOCUS, Models.GENERATED);

        itemModelGenerator.register(ModBlocks.SPACE_SIPHON.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SPACE_TIME_EVAPORATOR.asItem(), Models.GENERATED);
    }
}
