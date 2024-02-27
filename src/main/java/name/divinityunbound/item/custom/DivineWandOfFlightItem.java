package name.divinityunbound.item.custom;

import dev.architectury.event.events.common.TickEvent;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.entity.effect.ModStatusEffect;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DivineWandOfFlightItem extends Item {
    public DivineWandOfFlightItem(Settings settings) {
        super(settings);
    }

    public static final AbilitySource FLIGHT_CHARM = Pal.getAbilitySource(DivinityUnbound.MOD_ID, "divine_wand_of_flight");  // works like an identifier
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            boolean hasPlayerEffect = user.hasStatusEffect(ModStatusEffect.FLIGHT_EFFECT);
            if (!hasPlayerEffect) {
                user.addStatusEffect(new StatusEffectInstance(new StatusEffectInstance(ModStatusEffect.FLIGHT_EFFECT,
                        1200, 1,
                        false, false, true)));
                user.getStackInHand(hand).damage(1, user,
                        playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
            }
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.divine_wand_of_flight"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
