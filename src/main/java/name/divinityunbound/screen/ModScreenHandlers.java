package name.divinityunbound.screen;

import name.divinityunbound.DivinityUnbound;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<GenerationStationScreenHandler> GENERATION_STATION_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(DivinityUnbound.MOD_ID, "generation_station"),
                    new ExtendedScreenHandlerType<>(GenerationStationScreenHandler::new));

    public static void registerScreenHandlers() {
        DivinityUnbound.LOGGER.info("Registering Screen Handlers for " + DivinityUnbound.MOD_ID);
    }
}
