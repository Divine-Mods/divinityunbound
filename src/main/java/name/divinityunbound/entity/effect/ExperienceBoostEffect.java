package name.divinityunbound.entity.effect;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ExperienceBoostEffect extends StatusEffect {
    protected ExperienceBoostEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xc9ff8f);
    }
}
