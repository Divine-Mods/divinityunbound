package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.entity.HallowedFluidTankBlockEntity;
import name.divinityunbound.block.entity.ModBlockEntities;
import name.divinityunbound.block.entity.ZeusBatteryBlockEntity;
import name.divinityunbound.fluid.ModFluids;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class HallowedFluidTankBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    protected final Random random = Random.create();
    public static final MapCodec<HallowedFluidTankBlock> CODEC = HallowedFluidTankBlock.createCodec(HallowedFluidTankBlock::new);
    public HallowedFluidTankBlock(Settings settings) {
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
        return new HallowedFluidTankBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof HallowedFluidTankBlockEntity) {
                ItemScatterer.spawn(world, pos, (HallowedFluidTankBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ItemStack stackInHand = player.getStackInHand(hand);
            if (stackInHand.getItem() instanceof BucketItem) {
                HallowedFluidTankBlockEntity be = ((HallowedFluidTankBlockEntity) world.getBlockEntity(pos));
                boolean transferred = FluidStorageUtil.interactWithFluidStorage(be.fluidStorage, player, hand);
                return transferred ? ActionResult.SUCCESS : ActionResult.FAIL;
            }
            else {
                NamedScreenHandlerFactory screenHandlerFactory = ((HallowedFluidTankBlockEntity) world.getBlockEntity(pos));

                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof HallowedFluidTankBlockEntity) {
            HallowedFluidTankBlockEntity hallowedFluidTankBlockEntity = (HallowedFluidTankBlockEntity)blockEntity;
            if (!world.isClient) {
                ItemStack itemStack = HallowedFluidTankBlock.getItemStack();
                blockEntity.setStackNbt(itemStack);

                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    public static ItemStack getItemStack() {
        return new ItemStack(ModBlocks.HALLOWED_FLUID_TANK);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.HALLOWED_FLUID_TANK_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
