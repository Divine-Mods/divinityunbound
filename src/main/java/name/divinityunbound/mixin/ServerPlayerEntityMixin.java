package name.divinityunbound.mixin;

import name.divinityunbound.entity.effect.ModStatusEffect;
import name.divinityunbound.item.ModItems;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean hasPlayerEffect = ((ServerPlayerEntity)(Object)this).hasStatusEffect(ModStatusEffect.HERMES_EFFECT);
        if (hasPlayerEffect && source.isIn(DamageTypeTags.IS_FALL)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    // overrides default teleportation target when using wand of teleportation
    @Inject(method = "getTeleportTarget", at = @At("RETURN"), cancellable = true)
    private void injected(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cir) {
        ItemStack itemStack = ((ServerPlayerEntity)(Object)this).getMainHandStack();
        if (itemStack.getItem().equals(ModItems.WAND_OF_TELEPORTATION)) {
            if (itemStack.getNbt() != null) {
                if (itemStack.getNbt().contains("blockpos")) {
                    int[] pos = itemStack.getNbt().getIntArray("blockpos");
                    cir.setReturnValue(new TeleportTarget(
                            new Vec3d(pos[0] + 0.5, pos[1] + 1, pos[2] + 0.5),
                            ((ServerPlayerEntity)(Object)this).getVelocity(),
                            ((ServerPlayerEntity)(Object)this).getYaw(),
                            ((ServerPlayerEntity)(Object)this).getPitch()
                            ));
                    cir.cancel();
                }
            }
        }
    }

    @Inject(method = "createEndSpawnPlatform", at = @At(value = "HEAD"), cancellable = true)
    public void endSpawnPlatformInjected(ServerWorld world, BlockPos centerPos, CallbackInfo ci) {
        ItemStack itemStack = ((ServerPlayerEntity)(Object)this).getMainHandStack();
        if (itemStack.getItem().equals(ModItems.WAND_OF_TELEPORTATION)) {
            if (itemStack.getNbt() != null) {
                if (itemStack.getNbt().contains("blockpos")) {
                    ci.cancel();
                }
            }
        }
    }
}
