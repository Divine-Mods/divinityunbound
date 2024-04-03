package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import name.divinityunbound.block.entity.ModBlockEntities;
import name.divinityunbound.block.entity.WormholeTransporterBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WormholeTransporterBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.FACING;
    protected final Random random = Random.create();
    public static final MapCodec<WormholeTransporterBlock> CODEC = WormholeTransporterBlock.createCodec(WormholeTransporterBlock::new);
    public WormholeTransporterBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape DEFAULT_SHAPE = Block.createCuboidShape(5, 6, 5, 11, 12, 16);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(5, 6, 0, 11, 12, 11);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0, 6, 5, 11, 12, 11);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(5, 6, 5, 16, 12, 11);
    private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(6, 5, 6, 12, 16, 12);
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(6, 0, 6, 12, 11, 12);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(state.get(WormholeTransporterBlock.FACING)) {
            case NORTH: return DEFAULT_SHAPE;
            case SOUTH: return SOUTH_SHAPE;
            case EAST: return EAST_SHAPE;
            case WEST: return  WEST_SHAPE;
            case DOWN: return DOWN_SHAPE;
            case UP: return UP_SHAPE;
            default: return DEFAULT_SHAPE;
        }
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WormholeTransporterBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WormholeTransporterBlockEntity) {
                ItemScatterer.spawn(world, pos, (WormholeTransporterBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((WormholeTransporterBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.WORMHOLE_TRANSPORTER_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
