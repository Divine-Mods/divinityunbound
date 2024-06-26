package name.divinityunbound.block;

import com.mojang.serialization.MapCodec;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.custom.*;
import name.divinityunbound.particle.ModParticles;
import name.divinityunbound.world.tree.ModSaplingGenerators;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
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

    public static final Block EXPERIENCE_ORE = registerBlock("experience_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), FabricBlockSettings.copyOf(Blocks.STONE).strength(2f)));
    public static final Block DEEPSLATE_EXPERIENCE_ORE = registerBlock("deepslate_experience_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), FabricBlockSettings.copyOf(Blocks.DEEPSLATE).strength(4f)));
//    public static final Block NETHER_CELESTITE_ORE = registerBlock("nether_celestite_ore",
//            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), FabricBlockSettings.copyOf(Blocks.NETHERRACK).strength(1.5f)));
//    public static final Block END_STONE_CELESTITE_ORE = registerBlock("end_stone_celestite_ore",
//            new ExperienceDroppingBlock(UniformIntProvider.create(4, 7), FabricBlockSettings.copyOf(Blocks.END_STONE).strength(4f)));

    public static final Block WILDERSUNG_LOG = registerBlock("wildersung_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(4f)));
    public static final Block WILDERSUNG_WOOD = registerBlock("wildersung_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(4f)));
    public static final Block STRIPPED_WILDERSUNG_LOG = registerBlock("stripped_wildersung_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(4f)));
    public static final Block STRIPPED_WILDERSUNG_WOOD = registerBlock("stripped_wildersung_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(4f)));

    public static final Block WILDERSUNG_PLANKS = registerBlock("wildersung_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(4f)));

    public static final Block CELESTITE_INFUSED_WILDERSUNG_PLANKS = registerBlock("celestite_infused_wildersung_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(5f)));
    public static final Block CELESTITE_INFUSED_STONE = registerBlock("celestite_infused_stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(5f)));
    public static final Block GOLDEN_CELESTITE_INFUSED_STONE = registerBlock("golden_celestite_infused_stone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(5f)));

    public static final Block FROZEN_TIME_GLASS = registerBlock("frozen_time_glass",
            new Block(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)));

    public static final Block SAND_OF_TIME = registerBlockWithoutBlockItem("sand_of_time",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND)) {
                @Override
                protected MapCodec<? extends FallingBlock> getCodec() {
                    return null;
                }
            });

    public static final Block CELESTIUM_DUST_BLOCK = registerBlock("celestium_dust_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND)) {
                @Override
                protected MapCodec<? extends FallingBlock> getCodec() {
                    return null;
                }
            });
    public static final Block UNHOLY_DUST_BLOCK = registerBlock("unholy_dust_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND)) {
                @Override
                protected MapCodec<? extends FallingBlock> getCodec() {
                    return null;
                }
            });

    public static final Block SPACE_DUST_BLOCK = registerBlock("space_dust_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND)) {
                @Override
                protected MapCodec<? extends FallingBlock> getCodec() {
                    return null;
                }
            });

    public static final Block CONDENSED_MATTER_DUST_BLOCK = registerBlock("condensed_matter_dust_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND)) {
                @Override
                protected MapCodec<? extends FallingBlock> getCodec() {
                    return null;
                }
            });

    public static final Block DIVINE_TIME_DUST_BLOCK = registerBlock("divine_time_dust_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND)) {
                @Override
                protected MapCodec<? extends FallingBlock> getCodec() {
                    return null;
                }
            });

    public static final Block DIVINE_SPACE_DUST_BLOCK = registerBlock("divine_space_dust_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND)) {
                @Override
                protected MapCodec<? extends FallingBlock> getCodec() {
                    return null;
                }
            });

    public static final Block WILDERSUNG_LEAVES = registerBlock("wildersung_leaves",
            new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(4f).nonOpaque()));

    public static final Block WILDERSUNG_SAPLING = registerBlock("wildersung_sapling",
            new SaplingBlock(ModSaplingGenerators.WILDERSUNG, FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));

    public static final Block WILDERSUNG_SILK = registerBlock("wildersung_silk",
            new Block(FabricBlockSettings.copyOf(Blocks.PINK_WOOL)));

    public static final Block GENERATION_STATION = registerBlock("generation_station",
            new GenerationStationBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block SPEED_UPGRADE = registerBlock("speed_upgrade",
            new SpeedUpgradeBlock(FabricBlockSettings.copyOf(Blocks.END_STONE_BRICKS).nonOpaque()));
    public static final Block QUANTITY_UPGRADE = registerBlock("quantity_upgrade",
            new QuantityUpgradeBlock(FabricBlockSettings.copyOf(Blocks.END_STONE_BRICKS).nonOpaque()));
    public static final Block RANGE_UPGRADE = registerBlock("range_upgrade",
            new RangeUpgradeBlock(FabricBlockSettings.copyOf(Blocks.END_STONE_BRICKS).nonOpaque()));

    public static final Block KNOWLEDGE_EXTRACTOR = registerBlock("knowledge_extractor",
            new KnowledgeExtractorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block TIME_FORGED_BLOCK = registerBlock("time_forged_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    public static final Block SPACE_FORGED_BLOCK = registerBlock("space_forged_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    public static final Block FROZEN_TIME_LAMP = registerBlock("frozen_time_lamp",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_PURPLE)
                    .instrument(Instrument.PLING)
                    .strength(0.3f).sounds(BlockSoundGroup.GLASS)
                    .luminance(state -> 15).solidBlock(Blocks::never)));

    public static final Block ARCANE_FLOOR_LAMP = registerBlock("arcane_floor_lamp",
            new ArcaneFloorLampBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(Instrument.PLING)
                    .strength(0.3f).sounds(BlockSoundGroup.GLASS)
                    .luminance(state -> 15)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .blockVision(Blocks::never)
                    .solidBlock(Blocks::never)));

    public static final Block DIVINE_TORCH = registerBlockWithoutBlockItem("divine_torch",
            (Block)new TorchBlock(ModParticles.PURPLE_FLAME_PARTICLE,
                    AbstractBlock.Settings.create()
                            .noCollision().breakInstantly()
                            .luminance(state -> 15)
                            .sounds(BlockSoundGroup.WOOD)
                            .pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block WALL_DIVINE_TORCH = registerBlockWithoutBlockItem("wall_divine_torch",
            (Block)new WallTorchBlock(ModParticles.PURPLE_FLAME_PARTICLE,
                    AbstractBlock.Settings.create()
                            .noCollision().breakInstantly()
                            .luminance(state -> 15)
                            .sounds(BlockSoundGroup.WOOD)
                            .dropsLike(DIVINE_TORCH)
                            .pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block CELESTIAL_GLASS = registerBlock("celestial_glass",
            new CelestialGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never), false));

    public static final Block DARK_CELESTIAL_GLASS = registerBlock("dark_celestial_glass",
            new DarkCelestialGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never), false));

    public static final Block MINI_GLASS = registerBlock("mini_glass",
            new MiniGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)));

    public static final Block WITHERED_GLASS = registerBlock("withered_glass",
            new WitheredGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
                    .mapColor(MapColor.GRAY).nonOpaque()
                    .strength(4.5f, 3600000.0f)
                    .allowsSpawning(Blocks::never)
                    .blockVision(Blocks::never)));

    public static final Block ETHEREAL_CRYSTAL_GREENS = registerBlockWithoutBlockItem("ethereal_crystal_greens",
            new EtherealCrystalGreensBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));

    public static final Block MYSTIC_CHRONOGRAPH = registerBlock("mystic_chronograph",
            new MysticChronographBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block DIVINE_REPLICATOR = registerBlock("divine_replicator",
            new DivineReplicatorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block SPACE_SIPHON = registerBlock("space_siphon",
            new SpaceSiphonBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque().solidBlock(Blocks::never)));
    public static final Block CHRONOS_TIME_ACCUMULATOR = registerBlock("chronos_time_accumulator",
            new ChronosTimeAccumulatorBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(5f)));

    public static final Block UNHOLY_SILENCER = registerBlock("unholy_silencer",
            new UnholySilencerBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block SPACE_TIME_EVAPORATOR = registerBlock("space_time_evaporator",
            new SpaceTimeEvaporatorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block SPACE_TIME_AMALGAMATOR = registerBlock("space_time_amalgamator",
            new SpaceTimeAmalgamatorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block WORMHOLE_TRANSPORTER = registerBlock("wormhole_transporter",
            new WormholeTransporterBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block SPACE_TIME_FURNACE = registerBlock("space_time_furnace",
            new SpaceTimeFurnaceBlock(FabricBlockSettings.copyOf(Blocks.STONE)));

    public static final Block ITEM_TRASHCAN = registerBlock("item_trashcan",
            new ItemTrashcanBlock(FabricBlockSettings.copyOf(Blocks.GREEN_TERRACOTTA).nonOpaque()));

    public static final Block FLUID_TRASHCAN = registerBlock("fluid_trashcan",
            new FluidTrashcanBlock(FabricBlockSettings.copyOf(Blocks.GREEN_TERRACOTTA).nonOpaque()));

    public static final Block ENERGY_TRASHCAN = registerBlock("energy_trashcan",
            new EnergyTrashcanBlock(FabricBlockSettings.copyOf(Blocks.GREEN_TERRACOTTA).nonOpaque()));

    public static final Block HALLOWED_FLUID_TANK = registerBlock("hallowed_fluid_tank",
            new HallowedFluidTankBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block COAL_GENERATOR = registerBlock("coal_generator",
            new CoalGeneratorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block MOB_ATTRACTOR = registerBlock("mob_attractor",
            new MobAttractorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block UNHOLY_GRASS_BLOCK = registerBlock("unholy_grass_block",
            new UnholyGrassBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)));

    public static final Block PROTEUS_CONTROLLER_BLOCK = registerBlock("proteus_controller",
            new ProteusControllerBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block PROTEUS_CONVERTER_BLOCK = registerBlock("proteus_converter",
            new ProteusConverterBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block ZEUS_BATTERY = registerBlock("zeus_battery",
            new ZeusBatteryBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).nonOpaque()));

    public static final Block CREATIVE_ZEUS_BATTERY = registerBlock("creative_zeus_battery",
            new ZeusBatteryBlock(true, FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).nonOpaque()));

    public static final Block DEMETERS_HARVESTER = registerBlock("demeters_harvester",
            new DemetersHarvesterBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block PERSEPHONES_BLESSING = registerBlock("persephones_blessing",
            new PersephonesBlessingBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block PORTUNUS_PROPAGATOR = registerBlock("portunus_propagator",
            new PortunusPropagatorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block VELES_GATHERER = registerBlock("veles_gatherer",
            new VelesGathererBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block ITEM_SINGULARITY_STORAGE = registerBlock("item_singularity_storage",
            new ItemSingularityStorageBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(DivinityUnbound.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(DivinityUnbound.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(DivinityUnbound.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        DivinityUnbound.LOGGER.info("Registering ModBlocks for " + DivinityUnbound.MOD_ID);
    }
}
