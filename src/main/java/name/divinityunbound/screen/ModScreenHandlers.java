package name.divinityunbound.screen;

import name.divinityunbound.DivinityUnbound;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<GenerationStationScreenHandler> GENERATION_STATION_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "generation_station"),
                    new ExtendedScreenHandlerType<>(GenerationStationScreenHandler::new));

    public static final ScreenHandlerType<ChronosTimeAccumulatorScreenHandler> CHRONOS_TIME_ACCUMULATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "chronos_time_accumulator"),
                    new ExtendedScreenHandlerType<>(ChronosTimeAccumulatorScreenHandler::new));

    public static final ScreenHandlerType<DivineReplicatorScreenHandler> DIVINE_REPLICATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "divine_replicator"),
                    new ExtendedScreenHandlerType<>(DivineReplicatorScreenHandler::new));

    public static final ScreenHandlerType<UnholySilencerScreenHandler> UNHOLY_SILENCER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "unholy_silencer"),
                    new ExtendedScreenHandlerType<>(UnholySilencerScreenHandler::new));

    public static final ScreenHandlerType<SpaceTimeEvaporatorScreenHandler> SPACE_TIME_EVAPORATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "space_time_evaporator"),
                    new ExtendedScreenHandlerType<>(SpaceTimeEvaporatorScreenHandler::new));

    public static final ScreenHandlerType<SpaceTimeAmalgamatorScreenHandler> SPACE_TIME_AMALGAMATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "space_time_amalgamator"),
                    new ExtendedScreenHandlerType<>(SpaceTimeAmalgamatorScreenHandler::new));

    public static final ScreenHandlerType<WormholeTransporterScreenHandler> WORMHOLE_TRANSPORTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "wormhole_transporter"),
                    new ExtendedScreenHandlerType<>(WormholeTransporterScreenHandler::new));

    public static final ScreenHandlerType<ItemTrashcanScreenHandler> ITEM_TRASHCAN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "item_trashcan"),
                    new ExtendedScreenHandlerType<>(ItemTrashcanScreenHandler::new));

    public static final ScreenHandlerType<FluidTrashcanScreenHandler> FLUID_TRASHCAN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "fluid_trashcan"),
                    new ExtendedScreenHandlerType<>(FluidTrashcanScreenHandler::new));

    public static final ScreenHandlerType<EnergyTrashcanScreenHandler> ENERGY_TRASHCAN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "energy_trashcan"),
                    new ExtendedScreenHandlerType<>(EnergyTrashcanScreenHandler::new));

    public static final ScreenHandlerType<HallowedFluidTankScreenHandler> HALLOWED_FLUID_TANK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "hallowed_fluid_tank"),
                    new ExtendedScreenHandlerType<>(HallowedFluidTankScreenHandler::new));

    public static final ScreenHandlerType<SpaceTimeFurnaceScreenHandler> SPACE_TIME_FURNACE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "space_time_furnace"),
                    new ScreenHandlerType<>(SpaceTimeFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerScreenHandlers() {
        DivinityUnbound.LOGGER.info("Registering Screen Handlers for " + DivinityUnbound.MOD_ID);
    }
}
