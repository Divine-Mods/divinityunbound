package name.divinityunbound.item.custom;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.entity.effect.ModStatusEffect;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
        if (stack.getNbt() != null) {
            player.sendMessage(Text.literal("Stack nbt " + stack.getNbt().toString()));
        }
        else {
            player.sendMessage(Text.literal("Stack nbt null"));
        }
        //player.sendMessage(Text.literal("Calling release function, hasNbt: " + capturedNbt.toString()));
        if (!release(player, pos, facing, worldIn, stack)) return ActionResult.FAIL;
        return ActionResult.SUCCESS;
    }

    public boolean release(PlayerEntity player, BlockPos pos, Direction facing, World worldIn, ItemStack stack) {
        if (!worldIn.isClient) {
            if (stack.getNbt() == null) {
                return false;
            }
            player.sendMessage(Text.literal("Stack nbt " + stack.getNbt().toString()));
                    //if (!containsEntity(stack)) return false;
            Entity entity = getEntityFromStack(stack, worldIn, false, false);
            player.sendMessage(Text.literal("Has entity " + (entity != null)));
            if (entity != null) {
                entity.refreshPositionAndAngles(pos.getX() + 0.5,
                        pos.getY() + 1, pos.getZ() + 0.5,
                        worldIn.getRandom().nextFloat() * 360.0F, 0.0F);
                stack.setNbt(null);
                boolean spawned = worldIn.spawnEntity(entity);
                player.sendMessage(Text.literal("Spawned Entity " + spawned));
                return spawned;
            }
        }
        return true;
    }

    @Nullable
    public Entity getEntityFromStack(ItemStack stack, World world, boolean withInfo, boolean applyDuplicateFilter) {
        if (stack.getNbt() != null) {
            EntityType type = EntityType.fromNbt(stack.getNbt()).orElse(null);
            Entity entity = type.create(world);
            return entity;
        }
        return null;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        super.useOnEntity(stack, user, entity, hand);

        if (!user.getWorld().isClient) {
            if (stack.getNbt() != null) return ActionResult.FAIL;
            if(!(entity instanceof PlayerEntity)) {
                NbtCompound nbt = new NbtCompound();
                entity.saveSelfNbt(nbt);
                user.sendMessage(Text.literal("Entity nbt " + nbt.get("id")));
                //nbt.putString("entity", EntityType.get(entity.getType().toString()).toString());
                stack.setNbt(nbt);
                user.sendMessage(Text.literal("Entity nbt " + stack.getNbt().toString()));
                entity.remove(Entity.RemovalReason.KILLED);
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
            tooltip.add(Text.literal("Contains: " + stack.getNbt().get("id")));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public boolean containsEntity(ItemStack stack) {
        return !stack.isEmpty() && stack.hasNbt() && stack.getNbt().contains("entity");
    }
}
