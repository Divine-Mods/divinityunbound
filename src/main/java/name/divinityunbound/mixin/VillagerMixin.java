package name.divinityunbound.mixin;

import name.divinityunbound.item.ModItems;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public class VillagerMixin {

    @Inject(method = "interactMob", at = @At(value = "HEAD"), cancellable = true)
    public void injected(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(ModItems.WAND_OF_CAPTURING)) {
            cir.setReturnValue(ActionResult.PASS);
            cir.cancel();
        }
    }
}
