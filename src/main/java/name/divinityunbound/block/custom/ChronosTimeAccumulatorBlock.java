package name.divinityunbound.block.custom;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import name.divinityunbound.block.ModBlockConstants;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.entity.ChronosTimeAccumulatorBlockEntity;
import name.divinityunbound.block.entity.ModBlockEntities;
import name.divinityunbound.block.entity.SpaceSiphonBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class ChronosTimeAccumulatorBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<ChronosTimeAccumulatorBlock> CODEC = ChronosTimeAccumulatorBlock.createCodec(ChronosTimeAccumulatorBlock::new);

    protected final Random random = Random.create();
    public ChronosTimeAccumulatorBlock(Settings settings) {
        super(settings);
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
        return new ChronosTimeAccumulatorBlockEntity(pos, state);
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
        if (be instanceof ChronosTimeAccumulatorBlockEntity) {
            ((ChronosTimeAccumulatorBlockEntity)be).resetUpgrades();
            Iterator it = ModBlockConstants.UPGRADE_PROVIDER_OFFSETS.iterator();
            while (it.hasNext()) {
                BlockPos blockPosOffset = (BlockPos) it.next();
                BlockPos calcPos = new BlockPos(pos.getX() + blockPosOffset.getX(),
                        pos.getY() + blockPosOffset.getY(), pos.getZ() + blockPosOffset.getZ());
                if (world.getBlockState(calcPos).getBlock().equals(ModBlocks.SPEED_UPGRADE)) {
                    ((ChronosTimeAccumulatorBlockEntity)be).increaseSpeed();
                } else if (world.getBlockState(calcPos).getBlock().equals(ModBlocks.QUANTITY_UPGRADE)) {
                    ((ChronosTimeAccumulatorBlockEntity)be).increaseQuantity();
                }
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ChronosTimeAccumulatorBlockEntity) {
                ItemScatterer.spawn(world, pos, (ChronosTimeAccumulatorBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((ChronosTimeAccumulatorBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.CHRONOS_TIME_ACCUMULATOR_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d = (double)pos.getX() + 0.7;
        double e = (double)pos.getY() + 0.7;
        double f = (double)pos.getZ() + 0.7;
        double velx = (this.random.nextDouble() - 0.5) * 2.0;
        double vely = -this.random.nextDouble();
        double velz = (this.random.nextDouble() - 0.5) * 2.0;
        for(int i = 0; i < 8; ++i) {
            world.addParticle(ParticleTypes.PORTAL, d, e, f, velx, vely, velz);
        }
    }
}
