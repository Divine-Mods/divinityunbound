package name.divinityunbound.entity.effect;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import name.divinityunbound.DivinityUnbound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class FlightStatusEffect extends StatusEffect {
    protected FlightStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xf0ca3a);
    }
    public static final AbilitySource FLIGHT_POTION = Pal.getAbilitySource(DivinityUnbound.MOD_ID, "flight_potion");
    PlayerEntity player = null;
    @Override
    public void onApplied(LivingEntity effected, int amplifier) {
        if (effected instanceof PlayerEntity) {
            player = (PlayerEntity) effected;
            Pal.grantAbility((PlayerEntity) effected, VanillaAbilities.ALLOW_FLYING, FLIGHT_POTION);
            // equivalent to: FLIGHT_POTION.grantTo((PlayerEntity) effected, VanillaAbilities.ALLOW_FLYING);
        }
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        if (player != null && player instanceof PlayerEntity) {
            Pal.revokeAbility((PlayerEntity) player, VanillaAbilities.ALLOW_FLYING, FLIGHT_POTION);
            // equivalent to: FLIGHT_POTION.revokeFrom((PlayerEntity) effected, VanillaAbilities.ALLOW_FLYING);
        }
    }
}
