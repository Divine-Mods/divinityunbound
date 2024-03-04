package name.divinityunbound.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WandOfCelebrationItem extends FireworkRocketItem {
    public WandOfCelebrationItem(Settings settings) {
        super(settings);
    }

    private final ItemStack fireworkItem = createFireworkItem();

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        if (!world.isClient) {
            ItemStack itemStack = fireworkItem;
            Vec3d vec3d = context.getHitPos();
            Direction direction = context.getSide();
            FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, context.getPlayer(), vec3d.x + (double)direction.getOffsetX() * 0.15, vec3d.y + (double)direction.getOffsetY() * 0.15, vec3d.z + (double)direction.getOffsetZ() * 0.15, itemStack);
            world.spawnEntity(fireworkRocketEntity);
            player.getStackInHand(hand).damage(1, player,
                    playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.isFallFlying()) {
            ItemStack itemStack = fireworkItem;
            if (!world.isClient) {
                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemStack, player);
                world.spawnEntity(fireworkRocketEntity);
                player.getStackInHand(hand).damage(1, player,
                        playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
            }
            return TypedActionResult.success(player.getStackInHand(hand), world.isClient());
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    private ItemStack createFireworkItem() {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        NbtCompound nbtCompound = itemStack.getOrCreateSubNbt("Fireworks");
        nbtCompound.putByte("Flight", (byte)3);
        return itemStack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.wand_of_celebration"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
