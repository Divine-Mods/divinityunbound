package name.divinityunbound.item.custom;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class UnholyWandItem extends Item {
    public UnholyWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            BlockPos pos = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            Random random = context.getWorld().getRandom();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            List<BlockPos> SWAP_OFFSETS = Arrays.asList(new BlockPos(x,y,z), new BlockPos(x+1,y,z+1), new BlockPos(x+1,y,z),new BlockPos(x+1,y,z-1),
            new BlockPos(x,y,z-1),new BlockPos(x-1,y,z-1),new BlockPos(x-1,y,z),new BlockPos(x-1,y,z+1));
            int totalSwapped = 0;

            for (int c = 0; c < SWAP_OFFSETS.size(); c++) {
                pos = SWAP_OFFSETS.get(c);
                int swapNumber = 0;
                for (int i = 0; i <= pos.getY() + 64; i++) {
                    BlockState state = context.getWorld().getBlockState(pos.down(i));

                    if (isValuableBlock(state)) {
                        //outputValuableCoordinates(pos.down(i), player, state.getBlock());
                        BlockState swapBlock = context.getWorld().getBlockState(pos.down(swapNumber));
//                        player.sendMessage(Text.literal("Swapping " + state.getBlock().toString() +
//                                " and " + swapBlock.getBlock().toString()));

                        context.getWorld().setBlockState(pos.down(swapNumber), state);
                        context.getWorld().setBlockState(pos.down(i), swapBlock);

                        swapNumber++;
                        totalSwapped++;
                    }
                }
            }
            context.getWorld().playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.BLOCKS, 1.0F,
                    (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            player.sendMessage(Text.literal(totalSwapped + " ores divined to the surface!"));
        }

        context.getStack().damage(1, context.getPlayer(),
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        return ActionResult.SUCCESS;
    }

    public static void createParticles(WorldAccess world, BlockPos pos, int count) {
            pos = pos.up();
            double d = 3.0;
            double e = 1.0;
            world.addParticle(ParticleTypes.HAPPY_VILLAGER,
                    (double)pos.getX() + 0.5,
                    (double)pos.getY() + 0.5,
                    (double)pos.getZ() + 0.5,
                    0, 0,0);
            Random random = world.getRandom();
            for (int b = 0; b < 10; b++) {
                double f = random.nextGaussian() * 0.02;
                double g = random.nextGaussian() * 0.02;
                double h = random.nextGaussian() * 0.02;
                double j = 0.5 - d;
                double k = (double)pos.getX() + j + random.nextDouble() * d * 2.0;
                double l = (double)pos.getY() + random.nextDouble() * e;
                double m = (double)pos.getZ() + j + random.nextDouble() * d * 2.0;
                if (!world.getBlockState(BlockPos.ofFloored(k, l, m).down()).isAir()) {
                    world.addParticle(ParticleTypes.HAPPY_VILLAGER, k, l, m, f, g, h);
                }
            }
    }

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block block) {
        player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + "," + blockPos.getZ() + ")"), false);
    }

    private boolean isValuableBlock(BlockState state) {
        return state.isIn(BlockTags.IRON_ORES) || state.isIn(BlockTags.DIAMOND_ORES) || state.isIn(BlockTags.COAL_ORES)
                || state.isIn(BlockTags.LAPIS_ORES) || state.isIn(BlockTags.REDSTONE_ORES)
                || state.isOf(ModBlocks.CELESTITE_ORE) || state.isOf(ModBlocks.DEEPSLATE_CELESTITE_ORE);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.unholy_wand"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}

