package name.divinityunbound.datagen;

import com.ibm.icu.text.Normalizer2;
import dev.architectury.platform.Mod;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.custom.EtherealCrystalGreensBlock;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.item.client.SpaceSiphonItemModel;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

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
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CELESTIUM_DUST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.UNHOLY_DUST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SPACE_DUST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CONDENSED_MATTER_DUST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DIVINE_TIME_DUST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DIVINE_SPACE_DUST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FROZEN_TIME_LAMP);
        blockStateModelGenerator.registerSimpleState(ModBlocks.ARCANE_FLOOR_LAMP);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CELESTIAL_GLASS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DARK_CELESTIAL_GLASS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.WILDERSUNG_SILK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.WITHERED_GLASS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TIME_FORGED_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SPACE_FORGED_BLOCK);
        blockStateModelGenerator.registerSimpleState(ModBlocks.MINI_GLASS);
        blockStateModelGenerator.registerSimpleState(ModBlocks.DIVINE_TORCH);
        blockStateModelGenerator.registerSimpleState(ModBlocks.UNHOLY_GRASS_BLOCK);
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
        blockStateModelGenerator.registerSimpleState(ModBlocks.RANGE_UPGRADE);
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
        //blockStateModelGenerator.registerRod(ModBlocks.WORMHOLE_TRANSPORTER);
        //blockStateModelGenerator.registerRotatable(ModBlocks.WORMHOLE_TRANSPORTER);
        registerNorthDefaultAllAxisRotation(blockStateModelGenerator, ModBlocks.WORMHOLE_TRANSPORTER);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.ITEM_TRASHCAN);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.FLUID_TRASHCAN);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.ENERGY_TRASHCAN);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.HALLOWED_FLUID_TANK);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.ITEM_SINGULARITY_STORAGE);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.KNOWLEDGE_EXTRACTOR);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.COAL_GENERATOR);
        blockStateModelGenerator.registerSimpleState(ModBlocks.MOB_ATTRACTOR);
        blockStateModelGenerator.registerSimpleState(ModBlocks.ZEUS_BATTERY);
        blockStateModelGenerator.registerSimpleState(ModBlocks.CREATIVE_ZEUS_BATTERY);
        blockStateModelGenerator.registerSimpleState(ModBlocks.DEMETERS_HARVESTER);
        blockStateModelGenerator.registerSimpleState(ModBlocks.PERSEPHONES_BLESSING);

        blockStateModelGenerator.registerSimpleState(ModBlocks.PROTEUS_CONTROLLER_BLOCK);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.PROTEUS_CONVERTER_BLOCK);

        blockStateModelGenerator.registerCooker(ModBlocks.SPACE_TIME_FURNACE, TexturedModel.ORIENTABLE);

        /* Crops */
        blockStateModelGenerator.registerCrop(ModBlocks.ETHEREAL_CRYSTAL_GREENS, EtherealCrystalGreensBlock.AGE, 0, 1, 2, 3);
    }

    public final void registerNorthDefaultAllAxisRotation(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(block))).coordinate(this.createNorthDefaultFacingVariantMap()));
    }

    public final BlockStateVariantMap createNorthDefaultFacingVariantMap() {
        return BlockStateVariantMap.create(Properties.FACING)
                .register(Direction.DOWN,
                        BlockStateVariant.create()
                                .put(VariantSettings.X, VariantSettings.Rotation.R90))
                .register(Direction.UP, BlockStateVariant.create()
                        .put(VariantSettings.X, VariantSettings.Rotation.R270))
                .register(Direction.NORTH, BlockStateVariant.create())
                .register(Direction.SOUTH, BlockStateVariant.create()
                        .put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(Direction.WEST, BlockStateVariant.create()
                        .put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .register(Direction.EAST, BlockStateVariant.create()
                        .put(VariantSettings.Y, VariantSettings.Rotation.R90));
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
        itemModelGenerator.register(ModItems.CONDENSED_MATTER_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIVINE_TIME_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIVINE_SPACE_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.WILDERSUNG_STRING, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIME_FORGED_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNHOLY_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.EXPERIENCE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_FORGED_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_FUEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_TIME_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPEED_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.QUANTITY_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RANGE_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOB_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.IMPORT_CARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.EXPORT_CARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLD_BAND, Models.GENERATED);
        itemModelGenerator.register(ModItems.BATTERY_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FILTER_ITEM, Models.GENERATED);

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
        itemModelGenerator.register(ModItems.WAND_OF_MAGNETIZATION, Models.GENERATED);
        itemModelGenerator.register(ModItems.WAND_OF_HEALING, Models.GENERATED);
        itemModelGenerator.register(ModItems.GREATER_WAND_OF_HEALING, Models.GENERATED);

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

        //itemModelGenerator.register(ModItems.PROTEUS_TRIDENT, Models.);

        /* Foci */
        itemModelGenerator.register(ModItems.CELESTITE_COAL_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_REDSTONE_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_LAPIS_LAZULI_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_IRON_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_GOLD_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_DIAMOND_FOCUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CELESTITE_NETHERITE_FOCUS, Models.GENERATED);

        itemModelGenerator.register(ModBlocks.SPACE_SIPHON.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SPACE_TIME_EVAPORATOR.asItem(), Models.GENERATED);

        /* Crops */
        itemModelGenerator.register(ModItems.ETHEREAL_CRYSTAL_GREENS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ETHEREAL_CRYSTAL_GREENS_SALAD, Models.GENERATED);
    }
}
