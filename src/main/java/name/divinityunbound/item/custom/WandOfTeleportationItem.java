package name.divinityunbound.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class WandOfTeleportationItem extends Item {
    public WandOfTeleportationItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        super.useOnBlock(context);
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        Direction facing = context.getSide();
        World worldIn = context.getWorld();
        ItemStack stack = context.getStack();
        if(player.isSneaking()) {
            stack.removeSubNbt("blockpos");
            NbtCompound nbt = new NbtCompound();
            nbt.putIntArray("blockpos", Arrays.asList(pos.getX(), pos.getY(), pos.getZ()));
            stack.setNbt(nbt);
//            player.sendMessage(Text.literal("Saved block pos: " +
//                    pos.getX() + ", " + pos.getY() + ", " + pos.getZ()));
        }
        else {
            teleport(stack, player, worldIn);
        }
        return ActionResult.SUCCESS;
    }

    private void teleport(ItemStack stack, PlayerEntity player, World world) {
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("blockpos")) {
                int[] pos = stack.getNbt().getIntArray("blockpos");
                player.teleport(pos[0] + 0.5, pos[1] + 1, pos[2] + 0.5, true);
                playUseSound(world, player.getBlockPos());
//                player.getStackInHand(hand).damage(1, player,
//                        playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = player.getStackInHand(hand);
            if(player.isSneaking()) {
                stack.removeSubNbt("blockpos");
                player.sendMessage(Text.literal("Cleared saved block position!"));
            }
            else {
                teleport(stack, player, world);
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().contains("blockpos");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.wand_of_teleportation"));
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("blockpos")) {
                int[] pos = stack.getNbt().getIntArray("blockpos");
                tooltip.add(Text.literal("Saved Block Pos: " + pos[0] + ", " + pos[1] + ", " + pos[2]));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    private void playUseSound(World world, BlockPos pos) {
        Random random = world.getRandom();
        world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_PLAYER_TELEPORT,
                SoundCategory.BLOCKS, 1.0F,
                (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
