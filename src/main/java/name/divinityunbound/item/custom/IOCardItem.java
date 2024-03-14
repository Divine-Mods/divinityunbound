package name.divinityunbound.item.custom;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class IOCardItem extends Item {
    public IOCardItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        super.useOnBlock(context);
        if (!context.getPlayer().getWorld().isClient) {
                PlayerEntity player = context.getPlayer();
                ItemStack itemStack = context.getStack();
                BlockPos pos = context.getBlockPos();
            if(player.isSneaking()) {
                if (itemStack.getSubNbt("blockpos") == null) {
                    if(player.getWorld().getBlockState(pos)
                            .getBlock().equals(ModBlocks.WORMHOLE_TRANSPORTER)) {
                        ItemStack newItemStack = new ItemStack(itemStack.getItem());

                        NbtCompound nbt = new NbtCompound();
                        nbt.putIntArray("blockpos", Arrays.asList(pos.getX(), pos.getY(), pos.getZ()));
                        newItemStack.setNbt(nbt);

                        itemStack.decrement(1);
                        player.giveItemStack(newItemStack);
                    }
                } else {
                    itemStack.removeSubNbt("blockpos");
                    NbtCompound nbt = new NbtCompound();
                    nbt.putIntArray("blockpos", Arrays.asList(pos.getX(), pos.getY(), pos.getZ()));
                    itemStack.setNbt(nbt);
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        super.use(world, player, hand);

        if(player.isSneaking()) {
            player.getStackInHand(hand).removeSubNbt("blockpos");
            player.sendMessage(Text.literal("Cleared saved block position!"));
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().contains("blockpos");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.io_card_line_1"));
        tooltip.add(Text.translatable("tooltip.divinityunbound.io_card_line_2"));
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("blockpos")) {
                int[] pos = stack.getNbt().getIntArray("blockpos");
                if(pos.length == 3) {
                    tooltip.add(Text.literal("Saved Block Pos: " + pos[0] + ", " + pos[1] + ", " + pos[2]));
                }
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
