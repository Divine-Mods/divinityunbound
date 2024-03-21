package name.divinityunbound.entity.effect;

import name.divinityunbound.DivinityUnbound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class FlightStatusEffect extends StatusEffect {
    protected FlightStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xf0ca3a);
    }
    //public static final AbilitySource FLIGHT_POTION = Pal.getAbilitySource(DivinityUnbound.MOD_ID, "flight_potion");
    PlayerEntity player = null;
    @Override
    public void onApplied(LivingEntity effected, int amplifier) {
        if (effected instanceof PlayerEntity) {
            this.player = (PlayerEntity) effected;
            this.player.getAbilities().allowFlying = true;
            this.player.sendAbilitiesUpdate();
            //Pal.grantAbility((PlayerEntity) effected, VanillaAbilities.ALLOW_FLYING, FLIGHT_POTION);
            // equivalent to: FLIGHT_POTION.grantTo((PlayerEntity) effected, VanillaAbilities.ALLOW_FLYING);
        }
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        if (this.player instanceof PlayerEntity) {
            this.player.getAbilities().allowFlying = false;
            this.player.getAbilities().flying = false;
            this.player.sendAbilitiesUpdate();
            this.player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 100, 0, false,
                    false, true));
            //Pal.revokeAbility(this.player, VanillaAbilities.ALLOW_FLYING, FLIGHT_POTION);
            // equivalent to: FLIGHT_POTION.revokeFrom((PlayerEntity) effected, VanillaAbilities.ALLOW_FLYING);
        }
    }
}
