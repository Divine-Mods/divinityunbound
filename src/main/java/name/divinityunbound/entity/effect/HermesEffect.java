package name.divinityunbound.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stat;

public class HermesEffect extends StatusEffect {
    protected HermesEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xc9ff8f);
    }

    PlayerEntity player = null;
    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if (entity instanceof PlayerEntity) {
            player = (PlayerEntity) entity;

            //player.setNoGravity(true);
            //player.setStepHeight(5.0F);
        }
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        super.onRemoved(attributeContainer);

        if (player != null && player instanceof PlayerEntity) {
            player.removeStatusEffect(StatusEffects.SPEED);
            player.setNoGravity(false);
            player.setStepHeight(1.0F);
        }
    }


}
