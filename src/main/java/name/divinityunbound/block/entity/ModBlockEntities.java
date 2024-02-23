package name.divinityunbound.block.entity;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<GenerationStationBlockEntity> GENERATION_STATION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "generation_station_be"),
                    FabricBlockEntityTypeBuilder.create(GenerationStationBlockEntity::new,
                            ModBlocks.GENERATION_STATION).build());

    public static final BlockEntityType<MysticChronographBlockEntity> MYSTIC_CHRONOGRAPH_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(DivinityUnbound.MOD_ID, "mystic_chronograph_be"),
                    FabricBlockEntityTypeBuilder.create(MysticChronographBlockEntity::new,
                            ModBlocks.MYSTIC_CHRONOGRAPH).build());

    public static void registerBlockEntities() {
        DivinityUnbound.LOGGER.info("Registering Block Entities for " + DivinityUnbound.MOD_ID);
    }
}
