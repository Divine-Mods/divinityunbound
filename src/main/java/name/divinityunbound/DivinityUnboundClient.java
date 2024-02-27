package name.divinityunbound;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.screen.ChronosTimeAccumulatorScreen;
import name.divinityunbound.screen.DivineReplicatorScreen;
import name.divinityunbound.screen.GenerationStationScreen;
import name.divinityunbound.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class DivinityUnboundClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILDERSUNG_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILDERSUNG_SAPLING, RenderLayer.getCutout());

        HandledScreens.register(ModScreenHandlers.GENERATION_STATION_SCREEN_HANDLER, GenerationStationScreen::new);
        HandledScreens.register(ModScreenHandlers.CHRONOS_TIME_ACCUMULATOR_SCREEN_HANDLER, ChronosTimeAccumulatorScreen::new);
        HandledScreens.register(ModScreenHandlers.DIVINE_REPLICATOR_SCREEN_HANDLER, DivineReplicatorScreen::new);
    }
}
