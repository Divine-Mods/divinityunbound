package name.divinityunbound.mixin;

import name.divinityunbound.entity.effect.ModStatusEffect;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "damage", at = @At(value = "HEAD"))
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean hasPlayerEffect = ((ServerPlayerEntity)(Object)this).hasStatusEffect(ModStatusEffect.HERMES_EFFECT);
        if (hasPlayerEffect && source.isIn(DamageTypeTags.IS_FALL)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
