package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import name.divinityunbound.block.ModBlockConstants;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.entity.ModBlockEntities;
import name.divinityunbound.block.entity.PortunusPropagatorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class PortunusPropagatorBlock extends BlockWithEntity implements BlockEntityProvider {
    protected final Random random = Random.create();
    public static final BooleanProperty ENABLED = Properties.ENABLED;
    public static final MapCodec<PortunusPropagatorBlock> CODEC = PortunusPropagatorBlock.createCodec(PortunusPropagatorBlock::new);
    public PortunusPropagatorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ENABLED, false));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ENABLED);
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PortunusPropagatorBlockEntity(pos, state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            this.updateEnabled(world, pos, state);
            this.updateUpgrades(world, pos);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            this.updateEnabled(world, pos, state);
            this.updateUpgrades(world, pos);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        if (!world.isClient && world.getBlockEntity(pos) == null) {
            this.updateEnabled(world, pos, state);
        }
    }

    private void updateUpgrades(World world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof PortunusPropagatorBlockEntity) {
            ((PortunusPropagatorBlockEntity)be).resetUpgrades();
            Iterator it = ModBlockConstants.UPGRADE_PROVIDER_OFFSETS.iterator();
            while (it.hasNext()) {
                BlockPos blockPosOffset = (BlockPos) it.next();
                BlockPos calcPos = new BlockPos(pos.getX() + blockPosOffset.getX(),
                        pos.getY() + blockPosOffset.getY(), pos.getZ() + blockPosOffset.getZ());
                if (world.getBlockState(calcPos).getBlock().equals(ModBlocks.SPEED_UPGRADE)) {
                    ((PortunusPropagatorBlockEntity)be).increaseSpeed();
                }
                else if (world.getBlockState(calcPos).getBlock().equals(ModBlocks.QUANTITY_UPGRADE)) {
                    ((PortunusPropagatorBlockEntity)be).increaseQuantity();
                }
                else if (world.getBlockState(calcPos).getBlock().equals(ModBlocks.RANGE_UPGRADE)) {
                    ((PortunusPropagatorBlockEntity)be).increaseRange();
                }
            }
        }
    }

    private void updateEnabled(World world, BlockPos pos, BlockState state) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(ENABLED)) {
            world.setBlockState(pos, (BlockState)state.with(ENABLED, bl), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PortunusPropagatorBlockEntity) {
                ItemScatterer.spawn(world, pos, ((PortunusPropagatorBlockEntity)blockEntity).internalInventory);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((PortunusPropagatorBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.PORTUNUS_PROPAGATOR_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
