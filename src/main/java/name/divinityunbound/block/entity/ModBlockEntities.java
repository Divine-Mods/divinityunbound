package name.divinityunbound.block.entity;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {
    public static final BlockEntityType<GenerationStationBlockEntity> GENERATION_STATION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "generation_station_be"),
                    FabricBlockEntityTypeBuilder.create(GenerationStationBlockEntity::new,
                            ModBlocks.GENERATION_STATION).build());

    public static final BlockEntityType<MysticChronographBlockEntity> MYSTIC_CHRONOGRAPH_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "mystic_chronograph_be"),
                    FabricBlockEntityTypeBuilder.create(MysticChronographBlockEntity::new,
                            ModBlocks.MYSTIC_CHRONOGRAPH).build());

    public static final BlockEntityType<ChronosTimeAccumulatorBlockEntity> CHRONOS_TIME_ACCUMULATOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "chronos_time_accumulator_be"),
                    FabricBlockEntityTypeBuilder.create(ChronosTimeAccumulatorBlockEntity::new,
                            ModBlocks.CHRONOS_TIME_ACCUMULATOR).build());

    public static final BlockEntityType<DivineReplicatorBlockEntity> DIVINE_REPLICATOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "divine_replicator_be"),
                    FabricBlockEntityTypeBuilder.create(DivineReplicatorBlockEntity::new,
                            ModBlocks.DIVINE_REPLICATOR).build());

    public static final BlockEntityType<SpaceSiphonBlockEntity> SPACE_SIPHON_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "space_siphon_be"),
                    FabricBlockEntityTypeBuilder.create(SpaceSiphonBlockEntity::new,
                            ModBlocks.SPACE_SIPHON).build());

    public static final BlockEntityType<UnholySilencerBlockEntity> UNHOLY_SILENCER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "unholy_silencer_be"),
                    FabricBlockEntityTypeBuilder.create(UnholySilencerBlockEntity::new,
                            ModBlocks.UNHOLY_SILENCER).build());

    public static final BlockEntityType<SpaceTimeEvaporatorBlockEntity> SPACE_TIME_EVAPORATOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "space_time_evaporator_be"),
                    FabricBlockEntityTypeBuilder.create(SpaceTimeEvaporatorBlockEntity::new,
                            ModBlocks.SPACE_TIME_EVAPORATOR).build());

    public static final BlockEntityType<SpaceTimeAmalgamatorBlockEntity> SPACE_TIME_AMALGAMATOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "space_time_amalgamator_be"),
                    FabricBlockEntityTypeBuilder.create(SpaceTimeAmalgamatorBlockEntity::new,
                            ModBlocks.SPACE_TIME_AMALGAMATOR).build());

    public static final BlockEntityType<WormholeTransporterBlockEntity> WORMHOLE_TRANSPORTER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "wormhole_transporter_be"),
                    FabricBlockEntityTypeBuilder.create(WormholeTransporterBlockEntity::new,
                            ModBlocks.WORMHOLE_TRANSPORTER).build());

    public static final BlockEntityType<ItemTrashcanBlockEntity> ITEM_TRASHCAN_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "item_trashcan_be"),
                    FabricBlockEntityTypeBuilder.create(ItemTrashcanBlockEntity::new,
                            ModBlocks.ITEM_TRASHCAN).build());

    public static final BlockEntityType<FluidTrashcanBlockEntity> FLUID_TRASHCAN_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "fluid_trashcan_be"),
                    FabricBlockEntityTypeBuilder.create(FluidTrashcanBlockEntity::new,
                            ModBlocks.FLUID_TRASHCAN).build());

    public static final BlockEntityType<EnergyTrashcanBlockEntity> ENERGY_TRASHCAN_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "energy_trashcan_be"),
                    FabricBlockEntityTypeBuilder.create(EnergyTrashcanBlockEntity::new,
                            ModBlocks.ENERGY_TRASHCAN).build());

    public static final BlockEntityType<HallowedFluidTankBlockEntity> HALLOWED_FLUID_TANK_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "hallowed_fluid_tank_be"),
                    FabricBlockEntityTypeBuilder.create(HallowedFluidTankBlockEntity::new,
                            ModBlocks.HALLOWED_FLUID_TANK).build());

    public static final BlockEntityType<ItemSingularityStorageBlockEntity> ITEM_SINGULARITY_STORAGE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "item_singularity_storage_be"),
                    FabricBlockEntityTypeBuilder.create(ItemSingularityStorageBlockEntity::new,
                            ModBlocks.ITEM_SINGULARITY_STORAGE).build());

    public static final BlockEntityType<KnowledgeExtractorBlockEntity> KNOWLEDGE_EXTRACTOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "knowledge_extractor_storage_be"),
                    FabricBlockEntityTypeBuilder.create(KnowledgeExtractorBlockEntity::new,
                            ModBlocks.KNOWLEDGE_EXTRACTOR).build());

    public static final BlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "coal_generator_be"),
                    FabricBlockEntityTypeBuilder.create(CoalGeneratorBlockEntity::new,
                            ModBlocks.COAL_GENERATOR).build());

    public static final BlockEntityType<MobAttractorBlockEntity> MOB_ATTRACTOR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "mob_attractor_be"),
                    FabricBlockEntityTypeBuilder.create(MobAttractorBlockEntity::new,
                            ModBlocks.MOB_ATTRACTOR).build());

    public static final BlockEntityType<UnholyGrassBlockEntity> UNHOLY_GRASS_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "unholy_grass_be"),
                    FabricBlockEntityTypeBuilder.create(UnholyGrassBlockEntity::new,
                            ModBlocks.UNHOLY_GRASS_BLOCK).build());

    public static final BlockEntityType<ProteusConverterBlockEntity> PROTEUS_CONVERTER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "proteus_converter_be"),
                    FabricBlockEntityTypeBuilder.create(ProteusConverterBlockEntity::new,
                            ModBlocks.PROTEUS_CONVERTER_BLOCK).build());

    public static final BlockEntityType<ZeusBatteryBlockEntity> ZEUS_BATTERY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "zeus_battery_be"),
                    FabricBlockEntityTypeBuilder.create(ZeusBatteryBlockEntity::new,
                            ModBlocks.ZEUS_BATTERY).build());

    public static final BlockEntityType<DemetersHarvesterBlockEntity> DEMETERS_HARVESTER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "demeters_harvester_be"),
                    FabricBlockEntityTypeBuilder.create(DemetersHarvesterBlockEntity::new,
                            ModBlocks.DEMETERS_HARVESTER).build());

    public static final BlockEntityType<SpaceTimeFurnaceBlockEntity> SPACE_TIME_FURNACE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "space_time_furnace_be"),
                    FabricBlockEntityTypeBuilder.create(SpaceTimeFurnaceBlockEntity::new,
                            ModBlocks.SPACE_TIME_FURNACE).build(null));

    public static void registerBlockEntities() {
        DivinityUnbound.LOGGER.info("Registering Block Entities for " + DivinityUnbound.MOD_ID);

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, SPACE_TIME_EVAPORATOR_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, SPACE_TIME_EVAPORATOR_BLOCK_ENTITY);

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, SPACE_TIME_AMALGAMATOR_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, SPACE_TIME_AMALGAMATOR_BLOCK_ENTITY);

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, WORMHOLE_TRANSPORTER_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, WORMHOLE_TRANSPORTER_BLOCK_ENTITY);

        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, FLUID_TRASHCAN_BLOCK_ENTITY);
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, ENERGY_TRASHCAN_BLOCK_ENTITY);

        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, HALLOWED_FLUID_TANK_BLOCK_ENTITY);

        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.inventoryWrapper, ITEM_SINGULARITY_STORAGE_BLOCK_ENTITY);

        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.inventoryWrapper, COAL_GENERATOR_BLOCK_ENTITY);
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, COAL_GENERATOR_BLOCK_ENTITY);

        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.inventoryWrapper, PROTEUS_CONVERTER_BLOCK_ENTITY);
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, PROTEUS_CONVERTER_BLOCK_ENTITY);

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, ZEUS_BATTERY_BLOCK_ENTITY);

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, DEMETERS_HARVESTER_BLOCK_ENTITY);
    }
}
