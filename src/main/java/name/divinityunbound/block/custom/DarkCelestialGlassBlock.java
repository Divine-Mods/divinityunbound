package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TintedGlassBlock;
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

public class DarkCelestialGlassBlock extends CelestialGlassBlock {
    public static final MapCodec<DarkCelestialGlassBlock> CODEC = DarkCelestialGlassBlock.createCodec(DarkCelestialGlassBlock::new);
    public MapCodec<DarkCelestialGlassBlock> getCodec() {
        return CODEC;
    }
    public DarkCelestialGlassBlock(Settings settings) {
        super(settings);
        this.collidePlayers = true;
    }

    private final boolean collidePlayers;
    public DarkCelestialGlassBlock(Settings settings, boolean collidePlayers) {
        super(settings, collidePlayers);
        this.collidePlayers = collidePlayers;
    }
    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return world.getMaxLightLevel();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.dark_celestial_glass1"));
        tooltip.add(Text.translatable("tooltip.divinityunbound.dark_celestial_glass2"));
        //super.appendTooltip(stack, world, tooltip, options);
    }
}
