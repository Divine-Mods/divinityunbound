package name.divinityunbound.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CelestialGlassBlock extends TransparentBlock {
    public static final MapCodec<CelestialGlassBlock> CODEC = CelestialGlassBlock.createCodec(CelestialGlassBlock::new);
    public MapCodec<? extends TransparentBlock> getCodec() {
        return CODEC;
    }
    private final boolean collidePlayers;
    public CelestialGlassBlock(Settings settings) {
        super(settings);
        this.collidePlayers = true;
    }
    public CelestialGlassBlock(Settings settings, boolean collidePlayers) {
        super(settings);
        this.collidePlayers = collidePlayers;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (context instanceof EntityShapeContext
                && ((EntityShapeContext)context).getEntity() instanceof PlayerEntity
                && !(((EntityShapeContext)context).getEntity() instanceof VillagerEntity)
                && !((EntityShapeContext)context).getEntity().isSneaking())
                == collidePlayers ? state.getOutlineShape(world, pos) : VoxelShapes.empty();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.celestial_glass1"));
        tooltip.add(Text.translatable("tooltip.divinityunbound.celestial_glass2"));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
