package name.divinityunbound.datagen;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.tag.ValueLookupTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.Blocks.MAGIC_ORE_DETECTOR_DETECTABLE_BLOCKS)
                .add(ModBlocks.CELESTITE_ORE)
                .add(ModBlocks.DEEPSLATE_CELESTITE_ORE)
                .add(ModBlocks.EXPERIENCE_ORE)
                .add(ModBlocks.DEEPSLATE_EXPERIENCE_ORE);

        getOrCreateTagBuilder(ModTags.Blocks.PAXEL_MINABLE)
                .forceAddTag(BlockTags.PICKAXE_MINEABLE)
                .forceAddTag(BlockTags.AXE_MINEABLE)
                .forceAddTag(BlockTags.SHOVEL_MINEABLE);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.SAND_OF_TIME);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.CELESTITE_BLOCK)
                .add(ModBlocks.RAW_CELESTITE_BLOCK)
                .add(ModBlocks.CELESTITE_ORE)
                .add(ModBlocks.DEEPSLATE_CELESTITE_ORE)
                .add(ModBlocks.EXPERIENCE_ORE)
                .add(ModBlocks.DEEPSLATE_EXPERIENCE_ORE)
                .add(ModBlocks.CELESTITE_INFUSED_STONE)
                .add(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE)
                .add(ModBlocks.FROZEN_TIME_GLASS)
                .add(ModBlocks.FROZEN_TIME_LAMP)
                .add(ModBlocks.CHRONOS_TIME_ACCUMULATOR)
                .add(ModBlocks.GENERATION_STATION)
                .add(ModBlocks.MYSTIC_CHRONOGRAPH)
                .add(ModBlocks.DIVINE_REPLICATOR)
                .add(ModBlocks.SPACE_SIPHON)
                .add(ModBlocks.SPEED_UPGRADE)
                .add(ModBlocks.QUANTITY_UPGRADE)
                .add(ModBlocks.UNHOLY_SILENCER)
                .add(ModBlocks.SPACE_TIME_EVAPORATOR)
                .add(ModBlocks.SPACE_TIME_AMALGAMATOR)
                .add(ModBlocks.WORMHOLE_TRANSPORTER)
                .add(ModBlocks.ITEM_TRASHCAN)
                .add(ModBlocks.FLUID_TRASHCAN)
                .add(ModBlocks.ENERGY_TRASHCAN)
                .add(ModBlocks.SPACE_TIME_FURNACE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.CELESTITE_BLOCK)
                .add(ModBlocks.RAW_CELESTITE_BLOCK)
                .add(ModBlocks.CELESTITE_ORE)
                .add(ModBlocks.DEEPSLATE_CELESTITE_ORE)
                .add(ModBlocks.EXPERIENCE_ORE)
                .add(ModBlocks.DEEPSLATE_EXPERIENCE_ORE)
                .add(ModBlocks.CELESTITE_INFUSED_STONE)
                .add(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE)
                .add(ModBlocks.SAND_OF_TIME)
                .add(ModBlocks.CHRONOS_TIME_ACCUMULATOR)
                .add(ModBlocks.GENERATION_STATION)
                .add(ModBlocks.MYSTIC_CHRONOGRAPH)
                .add(ModBlocks.DIVINE_REPLICATOR)
                .add(ModBlocks.SPACE_SIPHON)
                .add(ModBlocks.SPEED_UPGRADE)
                .add(ModBlocks.QUANTITY_UPGRADE)
                .add(ModBlocks.UNHOLY_SILENCER)
                .add(ModBlocks.WORMHOLE_TRANSPORTER)
                .add(ModBlocks.ITEM_TRASHCAN)
                .add(ModBlocks.FLUID_TRASHCAN)
                .add(ModBlocks.ENERGY_TRASHCAN);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.SPACE_TIME_EVAPORATOR)
                .add(ModBlocks.SPACE_TIME_AMALGAMATOR)
                .add(ModBlocks.SPACE_TIME_FURNACE);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.WILDERSUNG_LOG)
                .add(ModBlocks.WILDERSUNG_WOOD)
                .add(ModBlocks.STRIPPED_WILDERSUNG_LOG)
                .add(ModBlocks.STRIPPED_WILDERSUNG_WOOD)
                .add(ModBlocks.WILDERSUNG_PLANKS)
                .add(ModBlocks.CELESTITE_INFUSED_WILDERSUNG_PLANKS);

        getOrCreateTagBuilder(BlockTags.LEAVES)
                .add(ModBlocks.WILDERSUNG_LEAVES);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.WILDERSUNG_LOG)
                .add(ModBlocks.WILDERSUNG_WOOD)
                .add(ModBlocks.STRIPPED_WILDERSUNG_LOG)
                .add(ModBlocks.STRIPPED_WILDERSUNG_WOOD);

//        getOrCreateTagBuilder(BlockTags.WALL_POST_OVERRIDE)
//        .add(ModBlocks.DIVINE_TORCH).addTag(BlockTags.SIGNS);
//        getOrCreateTagBuilder(BlockTags.WALL_POST_OVERRIDE).addTag(BlockTags.SIGNS)
//        .add(ModBlocks.DIVINE_TORCH);
        //.addTag((TagKey)BlockTags.BANNERS)).addTag((TagKey)BlockTags.PRESSURE_PLATES);
    }
}
