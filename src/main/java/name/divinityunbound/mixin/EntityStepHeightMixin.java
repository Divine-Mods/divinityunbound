package name.divinityunbound.mixin;

import name.divinityunbound.entity.effect.ModStatusEffect;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class EntityStepHeightMixin {
    private static float stepHeight = -1.0f;
    @Shadow public abstract boolean isSneaking();
    @Inject(method = "move", at = @At(value = "HEAD"))
    private void hermesStepHeight(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if (player.hasStatusEffect(ModStatusEffect.HERMES_EFFECT)) {
            if (stepHeight < 0) {
                stepHeight = player.getStepHeight();
            }
            player.setStepHeight(2.5f);
        } else if (stepHeight >= 0) {
            player.setStepHeight(stepHeight);
        }
    }

    @Inject(method = "shouldAutoJump", at = @At(value = "HEAD"), cancellable = true)
    private void disableAutoJump(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
        cir.cancel();
    }

//    @Inject(method = "applyDamage", at = @At(value = "HEAD"), cancellable = true)
//    public void handleFallDamage(DamageSource source, float amount, CallbackInfo ci) {
//        boolean hasPlayerEffect = ((PlayerEntity)(Object)this).hasStatusEffect(ModStatusEffect.HERMES_EFFECT);
//        if (hasPlayerEffect && source.isIn(DamageTypeTags.IS_FALL)) {
//            ci.cancel();
//        }
//    }

}