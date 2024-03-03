package name.divinityunbound;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.entity.ModBlockEntities;
import name.divinityunbound.block.entity.client.SpaceSiphonBlockRenderer;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.screen.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class DivinityUnboundClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILDERSUNG_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILDERSUNG_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROZEN_TIME_GLASS, RenderLayer.getCutout());

        HandledScreens.register(ModScreenHandlers.GENERATION_STATION_SCREEN_HANDLER, GenerationStationScreen::new);
        HandledScreens.register(ModScreenHandlers.CHRONOS_TIME_ACCUMULATOR_SCREEN_HANDLER, ChronosTimeAccumulatorScreen::new);
        HandledScreens.register(ModScreenHandlers.DIVINE_REPLICATOR_SCREEN_HANDLER, DivineReplicatorScreen::new);
        HandledScreens.register(ModScreenHandlers.UNHOLY_SILENCER_SCREEN_HANDLER, UnholySilencerScreen::new);
        HandledScreens.register(ModScreenHandlers.SPACE_TIME_EVAPORATOR_SCREEN_HANDLER, SpaceTimeEvaporatorScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.SPACE_SIPHON_BLOCK_ENTITY, SpaceSiphonBlockRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_SPACE_TIME, ModFluids.FLOWING_SPACE_TIME,
                SimpleFluidRenderHandler.coloredWater(0x0CC3CA));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.STILL_SPACE_TIME, ModFluids.FLOWING_SPACE_TIME);
    }
}
