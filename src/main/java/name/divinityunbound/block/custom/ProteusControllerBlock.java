package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import name.divinityunbound.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class ProteusControllerBlock extends Block {
    public static final MapCodec<ProteusControllerBlock> CODEC = ProteusControllerBlock.createCodec(ProteusControllerBlock::new);
    private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 7, 12, 16, 10);
    @Nullable
    private static BlockPattern blockPattern;
    public ProteusControllerBlock(Settings settings) {
        super(settings);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient()) {
            if (pos.getY() < world.getBottomY()) {
                return;
            }
            BlockPattern.Result result = ProteusControllerBlock.getPattern().searchAround(world, pos);
            if (result == null) {
                return;
            }
            breakPatternBlocks(world, result);
            BlockState newBlockState = ModBlocks.PROTEUS_CONVERTER_BLOCK.getDefaultState();
            world.setBlockState(pos.down(), newBlockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
    }

    public static void breakPatternBlocks(World world, BlockPattern.Result patternResult) {
        for (int k = 0; k < patternResult.getDepth(); k++) {
            for (int i = 0; i < patternResult.getWidth(); ++i) {
                for (int j = 0; j < patternResult.getHeight(); ++j) {
                    CachedBlockPosition cachedBlockPosition = patternResult.translate(i, j, k);
                    world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                    world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
                }
            }
        }
    }

    private static BlockPattern getPattern() {
        if (blockPattern == null) {
            blockPattern = BlockPatternBuilder.start()
                    .aisle("WTW", "LFB", "WTW")
                    .aisle("LFB", "#~S", "LFB")
                    .aisle("WTW", "LFB", "WTW")
                    .where('#', CachedBlockPosition
                            .matchesBlockState(
                                    BlockStatePredicate
                                            .forBlock(ModBlocks.PROTEUS_CONTROLLER_BLOCK)))
                    .where('W', CachedBlockPosition
                            .matchesBlockState(
                                    BlockStatePredicate
                                            .forBlock(ModBlocks.WILDERSUNG_LOG)))
                    .where('F', CachedBlockPosition
                            .matchesBlockState(
                                    BlockStatePredicate
                                            .forBlock(ModBlocks.FROZEN_TIME_GLASS)))
                    .where('T', CachedBlockPosition
                            .matchesBlockState(
                                    BlockStatePredicate
                                            .forBlock(ModBlocks.TIME_FORGED_BLOCK)))
                    .where('S', CachedBlockPosition
                            .matchesBlockState(
                                    BlockStatePredicate
                                            .forBlock(ModBlocks.SPACE_FORGED_BLOCK)))
                    .where('L', CachedBlockPosition
                            .matchesBlockState(
                                    BlockStatePredicate
                                            .forBlock(ModBlocks.FROZEN_TIME_LAMP)))
                    .where('B', CachedBlockPosition
                            .matchesBlockState(
                                    BlockStatePredicate
                                            .forBlock(Blocks.POLISHED_BLACKSTONE)))
                    .where('^', CachedBlockPosition
                            .matchesBlockState(
                                    BlockStatePredicate
                                            .forBlock(ModBlocks.CELESTITE_BLOCK)))
                    .where('~', pos -> pos.getBlockState().isAir()).build();
        }
        return blockPattern;
    }

    @Override
    protected MapCodec<ProteusControllerBlock> getCodec() {
        return CODEC;
    }
}
