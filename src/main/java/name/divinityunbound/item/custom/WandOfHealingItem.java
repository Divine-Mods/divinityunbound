package name.divinityunbound.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WandOfHealingItem extends Item {
    public WandOfHealingItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            user.getItemCooldownManager().set(this, 600);
            user.addStatusEffect(new StatusEffectInstance(new StatusEffectInstance(StatusEffects.REGENERATION,
                    100, 1,
                    false, false, true)));
            user.addStatusEffect(new StatusEffectInstance(new StatusEffectInstance(StatusEffects.ABSORPTION,
                    200, 0,
                    false, false, true)));
            user.heal(4.0F);
            user.getStackInHand(hand).damage(1, user,
                    playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

//    @Override
//    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
//        tooltip.add(Text.translatable("tooltip.divinityunbound.wand_of_healing"));
//        super.appendTooltip(stack, world, tooltip, context);
//    }
}
