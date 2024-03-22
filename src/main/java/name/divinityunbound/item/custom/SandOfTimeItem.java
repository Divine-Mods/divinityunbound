package name.divinityunbound.item.custom;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

public class SandOfTimeItem extends BlockItem {
    public SandOfTimeItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            World world = context.getWorld();

            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);

            PlayerEntity player = context.getPlayer();
            if (player.isSneaking()) {
                if (world.getDimensionKey() == DimensionTypes.OVERWORLD) {
                    if (blockState.getBlock().equals(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE)
                            && blockPos.getY() > 125) {
                        ItemStack item = new ItemStack(ModBlocks.CELESTIUM_DUST_BLOCK);

                        this.playUseSound(world, blockPos);
                        context.getStack().decrement(1);
                        player.giveItemStack(item);
                    }
                } else if (world.getDimensionKey() == DimensionTypes.THE_NETHER) {
                    if (blockState.getBlock().equals(Blocks.BEDROCK)) {
                        ItemStack item = new ItemStack(ModBlocks.UNHOLY_DUST_BLOCK);

                        this.playUseSound(world, blockPos);
                        context.getStack().decrement(1);
                        player.giveItemStack(item);
                    }
                } else if (world.getDimensionKey() == DimensionTypes.THE_END) {
                    if (blockState.getBlock().equals(Blocks.OBSIDIAN)) {
                        ItemStack item = new ItemStack(ModBlocks.SPACE_DUST_BLOCK);

                        this.playUseSound(world, blockPos);
                        context.getStack().decrement(1);
                        player.giveItemStack(item);
                    }
                }
            }
            else {
                return this.place(new ItemPlacementContext(context));
            }
        }


        return ActionResult.SUCCESS;
    }

    private void playUseSound(World world, BlockPos pos) {
        Random random = world.getRandom();
        world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SAND_BREAK,
                SoundCategory.BLOCKS, 1.0F,
                (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
