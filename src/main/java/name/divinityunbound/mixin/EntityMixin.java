package name.divinityunbound.mixin;

import name.divinityunbound.entity.effect.ModStatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "getY", at = @At("RETURN"), cancellable = true)
    private void injected(CallbackInfoReturnable<Double> cir) {
        if(((Entity)(Object)this) instanceof PlayerEntity) {
            if (((PlayerEntity)(Object)this).hasStatusEffect(ModStatusEffect.HERMES_EFFECT)){
                cir.setReturnValue(cir.getReturnValue() + 1.0);
                cir.cancel();
            }
        }
    }
}
