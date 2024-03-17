package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MiniGlassBlock extends TransparentBlock {
    public static final MapCodec<MiniGlassBlock> CODEC = MiniGlassBlock.createCodec(MiniGlassBlock::new);
    private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);
    public MapCodec<? extends TransparentBlock> getCodec() {
        return CODEC;
    }
    public MiniGlassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
