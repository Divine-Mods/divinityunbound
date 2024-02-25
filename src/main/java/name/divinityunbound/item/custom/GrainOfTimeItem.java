package name.divinityunbound.item.custom;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.fluid.WaterFluid;

public class GrainOfTimeItem extends Item {
    public GrainOfTimeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            World world = context.getWorld();

            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);

            PlayerEntity player = context.getPlayer();

            if (world.getDimension().ultrawarm()) {
                if (blockState.getBlock().equals(Blocks.BEDROCK)) {
                    ItemStack item = new ItemStack(ModItems.UNHOLY_DUST);

                    this.playUseSound(world, blockPos);
                    context.getStack().decrement(1);
                    player.giveItemStack(item);
                }
            }
            else {
                if (blockState.getBlock().equals(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE)
                        && blockPos.getY() > 300) {
                    ItemStack item = new ItemStack(ModItems.CELESTIUM_DUST);

                    this.playUseSound(world, blockPos);
                    context.getStack().decrement(1);
                    player.giveItemStack(item);
                }
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
