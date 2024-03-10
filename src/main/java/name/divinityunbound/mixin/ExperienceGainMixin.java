package name.divinityunbound.mixin;

import name.divinityunbound.entity.effect.ModStatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class ExperienceGainMixin {
    @ModifyVariable(method = "addExperience(I)V", at = @At("HEAD"), ordinal = 0)
    private int injected(int i) {
        boolean hasPlayerEffect = ((PlayerEntity)(Object)this).hasStatusEffect(ModStatusEffect.EXPERIENCE_BOOST_EFFECT);
        if (hasPlayerEffect) {
            return i * 3;
        }
        return i;
    }
}