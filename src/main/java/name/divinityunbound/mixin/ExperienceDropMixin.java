package name.divinityunbound.mixin;

import name.divinityunbound.entity.effect.ModStatusEffect;
import name.divinityunbound.event.ExperienceDropCallback;
import name.divinityunbound.item.ModArmorMaterials;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(PlayerEntity.class)
public abstract class ExperienceDropMixin {
    @ModifyVariable(method = "addExperience(I)V", at = @At("HEAD"), ordinal = 0)
    private int injected(int i) {
        boolean hasPlayerEffect = ((PlayerEntity)(Object)this).hasStatusEffect(ModStatusEffect.EXPERIENCE_BOOST_EFFECT);
        if (hasPlayerEffect) {
            return i * 3;
        }
        return i;
    }
}