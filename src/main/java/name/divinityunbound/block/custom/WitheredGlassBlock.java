package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WitheredGlassBlock extends TransparentBlock {
    public static final MapCodec<WitheredGlassBlock> CODEC = WitheredGlassBlock.createCodec(WitheredGlassBlock::new);
    public MapCodec<? extends TransparentBlock> getCodec() {
        return CODEC;
    }
    public WitheredGlassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        tooltip.add(Text.translatable("tooltip.divinityunbound.withered_glass"));
    }
}
