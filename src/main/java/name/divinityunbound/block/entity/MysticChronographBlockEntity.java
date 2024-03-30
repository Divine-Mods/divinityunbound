package name.divinityunbound.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MysticChronographBlockEntity  extends BlockEntity {
    private static final ArrayList<BlockEntityType<? extends BlockEntity>> blockEntities = new ArrayList<BlockEntityType<? extends BlockEntity>>() {{
        add(ModBlockEntities.MYSTIC_CHRONOGRAPH_BLOCK_ENTITY);
        add(ModBlockEntities.CHRONOS_TIME_ACCUMULATOR_BLOCK_ENTITY);
        add(ModBlockEntities.PERSEPHONES_BLESSING_BLOCK_ENTITY);
        add(ModBlockEntities.DEMETERS_HARVESTER_BLOCK_ENTITY);
        add(ModBlockEntities.UNHOLY_GRASS_BLOCK_ENTITY);
        add(ModBlockEntities.WORMHOLE_TRANSPORTER_BLOCK_ENTITY);
    }};
    public MysticChronographBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MYSTIC_CHRONOGRAPH_BLOCK_ENTITY, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        BlockEntity down_block = world.getBlockEntity(pos.down(1));
        if (down_block != null) {
            BlockState down_block_state = world.getBlockState(pos.down(1));
            BlockEntityType<BlockEntity> type = (BlockEntityType<BlockEntity>) down_block.getType();

            if(!blockEntities.contains(type)) {
                for (int i = 0; i < 16; i++) {
                    BlockEntityTicker<BlockEntity> ticker = world.getBlockState(pos.down(1)).getBlockEntityTicker(world, type);
                    if (ticker != null) {
                        ticker.tick(world, pos.down(1), down_block_state, down_block);
                    }
                }
            }
        }
    }
}
