package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import name.divinityunbound.block.ModBlockConstants;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.entity.GenerationStationBlockEntity;
import name.divinityunbound.block.entity.ModBlockEntities;
import name.divinityunbound.block.entity.SpaceSiphonBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
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

import java.util.Iterator;
import java.util.List;

public class GenerationStationBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 7, 16);
    protected final Random random = Random.create();
    public static final MapCodec<GenerationStationBlock> CODEC = GenerationStationBlock.createCodec(GenerationStationBlock::new);
    public GenerationStationBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
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
        return new GenerationStationBlockEntity(pos, state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            this.updateUpgrades(world, pos);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            this.updateUpgrades(world, pos);
        }
    }

    private void updateUpgrades(World world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof GenerationStationBlockEntity) {
            ((GenerationStationBlockEntity)be).resetUpgrades();
            Iterator it = ModBlockConstants.UPGRADE_PROVIDER_OFFSETS.iterator();
            while (it.hasNext()) {
                BlockPos blockPosOffset = (BlockPos) it.next();
                BlockPos calcPos = new BlockPos(pos.getX() + blockPosOffset.getX(),
                        pos.getY() + blockPosOffset.getY(), pos.getZ() + blockPosOffset.getZ());
                if (world.getBlockState(calcPos).getBlock().equals(ModBlocks.SPEED_UPGRADE)) {
                    ((GenerationStationBlockEntity)be).increaseSpeed();
                } else if (world.getBlockState(calcPos).getBlock().equals(ModBlocks.QUANTITY_UPGRADE)) {
                    ((GenerationStationBlockEntity)be).increaseQuantity();
                }
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GenerationStationBlockEntity) {
                ItemScatterer.spawn(world, pos, (GenerationStationBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((GenerationStationBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.GENERATION_STATION_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    public static final List<BlockPos> POWER_PROVIDER_OFFSETS = BlockPos.stream(-2, 0, -2, 2, 1, 2).filter((pos) -> {
        return Math.abs(pos.getX()) == 2 || Math.abs(pos.getZ()) == 2;
    }).map(BlockPos::toImmutable).toList();

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        double d = (double)pos.getX() + 0.5;
        double e = (double)pos.getY() + 1.5;
        double f = (double)pos.getZ() + 0.5;
        Iterator it = POWER_PROVIDER_OFFSETS.iterator();
        while(it.hasNext()) {
            BlockPos blockPos = (BlockPos)it.next();
            if (random.nextInt(64) == 0) {
                world.addParticle(ParticleTypes.ENCHANT, d, e, f,
                        (double) ((float) blockPos.getX() + random.nextFloat()) - 0.5,
                        (double) ((float) blockPos.getY() - random.nextFloat() - 1.0F),
                        (double) ((float) blockPos.getZ() + random.nextFloat()) - 0.5);
            }
        }
    }
}
