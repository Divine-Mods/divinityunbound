package name.divinityunbound.mixin;

import name.divinityunbound.item.custom.PaxelItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    // with a Space Time Paxel
    // unenchanted, 12
    // efficiency 5, 38
    // efficiency 5 with haste 1, 53.2
    // efficiency 5 with haste 2, 60.8
    @Inject(method = "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F", at = @At("RETURN"), cancellable = true)
    private void injected(BlockState block, CallbackInfoReturnable<Float> cir) {
        if (((PlayerEntity)(Object)this).getMainHandStack().getItem() instanceof PaxelItem) {
            if (((PlayerEntity)(Object)this).isSneaking()) {
                float speed = cir.getReturnValue();
                float speedLimit = clamp(speed, 1, 43);
                cir.setReturnValue(speedLimit);
                cir.cancel();
            }
        }
    }
    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
