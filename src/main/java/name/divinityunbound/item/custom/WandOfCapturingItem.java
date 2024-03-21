package name.divinityunbound.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

// Done with the help of:
// https://github.com/InnovativeOnlineIndustries/Industrial-Foregoing/tree/1.20
public class WandOfCapturingItem extends Item {
    public WandOfCapturingItem(Settings settings) {
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
        //player.sendMessage(Text.literal("Calling release function, hasNbt: " + capturedNbt.toString()));
        if (!release(player, pos, facing, worldIn, stack)) return ActionResult.FAIL;
        return ActionResult.SUCCESS;
    }

    public boolean release(PlayerEntity player, BlockPos pos, Direction facing, World worldIn, ItemStack stack) {
        if (!worldIn.isClient) {
            if (stack.getNbt() == null) {
                return false;
            }
            //player.sendMessage(Text.literal("Stack nbt " + stack.getNbt().toString()));
            Optional<Entity> ent = getEntityFromStack(stack, worldIn, false, false);
            Entity entity = ent.get();
            //player.sendMessage(Text.literal("Has entity " + (entity != null)));
            if (entity != null) {
                //player.sendMessage(Text.literal(stack.getSubNbt("entity").toString()));
                entity.refreshPositionAndAngles(pos.getX() + 0.5,
                        pos.getY() + 1, pos.getZ() + 0.5,
                        worldIn.getRandom().nextFloat() * 360.0F, 0.0F);
                //stack.setNbt(null);
                stack.removeSubNbt("entity");
                boolean spawned = worldIn.spawnEntity(entity);
                //player.sendMessage(Text.literal("Spawned Entity " + spawned));
                return spawned;
            }
        }
        return true;
    }

    @Nullable
    public Optional<Entity> getEntityFromStack(ItemStack stack, World world, boolean withInfo, boolean applyDuplicateFilter) {
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("entity")) {
                //EntityType<?> type = EntityType.fromNbt(stack.getSubNbt("entity")).orElse(null);
                //Entity entity = type.create(world);
                return EntityType.getEntityFromNbt(stack.getSubNbt("entity"), world);
            }
        }
        return null;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        super.useOnEntity(stack, user, entity, hand);

        if (!user.getWorld().isClient) {
            if (stack.getNbt() != null
                    && stack.getNbt().contains("entity")) {
                return ActionResult.FAIL;
            }
            if(!(entity instanceof PlayerEntity)) {
                stack.removeSubNbt("entity");
                NbtCompound nbt = new NbtCompound();
                entity.saveSelfNbt(nbt);

                NbtCompound nbt2 = new NbtCompound();
                nbt2.put("entity", nbt);
                //user.sendMessage(Text.literal("Entity nbt " + nbt.get("id")));
                stack.setNbt(nbt2);
                //stack.setNbt(nbt);
                //user.sendMessage(Text.literal("Entity nbt " + stack.getNbt().toString()));
                entity.remove(Entity.RemovalReason.KILLED);
                user.setStackInHand(hand, stack);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getNbt() != null;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.wand_of_capturing"));
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("entity")) {
                NbtCompound nbt = stack.getSubNbt("entity");
                if (nbt != null) {
                    tooltip.add(Text.literal("Contains: " + nbt.get("id")));
                }
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public boolean containsEntity(ItemStack stack) {
        return !stack.isEmpty() && stack.hasNbt() && stack.getNbt().contains("entity");
    }
}
