package name.divinityunbound;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.entity.ModBlockEntities;
import name.divinityunbound.block.entity.client.SpaceSiphonBlockRenderer;
import name.divinityunbound.block.entity.client.SpaceTimeEvaporatorBlockRenderer;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.networking.ModMessages;
import name.divinityunbound.particle.ModParticles;
import name.divinityunbound.particle.PurpleFlameParticle;
import name.divinityunbound.screen.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CELESTIAL_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HALLOWED_FLUID_TANK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KNOWLEDGE_EXTRACTOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COAL_GENERATOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PROTEUS_CONVERTER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PROTEUS_CONTROLLER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MINI_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DARK_CELESTIAL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ARCANE_FLOOR_LAMP, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WITHERED_GLASS, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPACE_TIME_AMALGAMATOR, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WORMHOLE_TRANSPORTER, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ITEM_TRASHCAN, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLUID_TRASHCAN, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENERGY_TRASHCAN, RenderLayer.getCutoutMipped());

        ModMessages.registerS2CPackets();

        ParticleFactoryRegistry.getInstance().register(ModParticles.PURPLE_FLAME_PARTICLE, PurpleFlameParticle.Factory::new);

        HandledScreens.register(ModScreenHandlers.GENERATION_STATION_SCREEN_HANDLER, GenerationStationScreen::new);
        HandledScreens.register(ModScreenHandlers.CHRONOS_TIME_ACCUMULATOR_SCREEN_HANDLER, ChronosTimeAccumulatorScreen::new);
        HandledScreens.register(ModScreenHandlers.DIVINE_REPLICATOR_SCREEN_HANDLER, DivineReplicatorScreen::new);
        HandledScreens.register(ModScreenHandlers.UNHOLY_SILENCER_SCREEN_HANDLER, UnholySilencerScreen::new);
        HandledScreens.register(ModScreenHandlers.SPACE_TIME_EVAPORATOR_SCREEN_HANDLER, SpaceTimeEvaporatorScreen::new);
        HandledScreens.register(ModScreenHandlers.SPACE_TIME_AMALGAMATOR_SCREEN_HANDLER, SpaceTimeAmalgamatorScreen::new);
        HandledScreens.register(ModScreenHandlers.WORMHOLE_TRANSPORTER_SCREEN_HANDLER, WormholeTransporterScreen::new);
        HandledScreens.register(ModScreenHandlers.ITEM_TRASHCAN_SCREEN_HANDLER, ItemTrashcanScreen::new);
        HandledScreens.register(ModScreenHandlers.FLUID_TRASHCAN_SCREEN_HANDLER, FluidTrashcanScreen::new);
        HandledScreens.register(ModScreenHandlers.ENERGY_TRASHCAN_SCREEN_HANDLER, EnergyTrashcanScreen::new);
        HandledScreens.register(ModScreenHandlers.HALLOWED_FLUID_TANK_SCREEN_HANDLER, HallowedFluidTankScreen::new);
        //HandledScreens.register(ModScreenHandlers.ITEM_SINGULARITY_STORAGE_SCREEN_HANDLER, ItemSingularityStorageScreen::new);
        HandledScreens.register(ModScreenHandlers.KNOWLEDGE_EXTRACTOR_SCREEN_HANDLER, KnowledgeExtractorScreen::new);
        HandledScreens.register(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, CoalGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.PROTEUS_CONVERTER_SCREEN_HANDLER, ProteusConverterScreen::new);
        HandledScreens.register(ModScreenHandlers.SPACE_TIME_FURNACE_SCREEN_HANDLER, SpaceTimeFurnaceScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.SPACE_SIPHON_BLOCK_ENTITY, SpaceSiphonBlockRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.SPACE_TIME_EVAPORATOR_BLOCK_ENTITY, SpaceTimeEvaporatorBlockRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_SPACE_TIME, ModFluids.FLOWING_SPACE_TIME,
                SimpleFluidRenderHandler.coloredWater(0x0CC3CA));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.STILL_SPACE_TIME, ModFluids.FLOWING_SPACE_TIME);
    }
}
