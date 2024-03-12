package name.divinityunbound.mixin;

import name.divinityunbound.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ApplyBonusLootFunction.class)
public abstract class ApplyBonusLootFunctionMixin {
    private static final Random random = Random.create();
    @Inject(method = "process", at = @At("RETURN"), cancellable = true)
    private void injected(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack itemStack = context.get(LootContextParameters.TOOL);

        ItemStack returnStack = cir.getReturnValue();
        int fortuneCount = returnStack.getCount();

        int enchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.PROSPERITY, itemStack);
        if (enchantmentLevel > 0) {
            if (fortuneCount > 1) {
                int i = random.nextInt(enchantmentLevel + 2) - 1;
                if (i < 0) {
                    i = 0;
                }
                returnStack.setCount(returnStack.getCount() * (i + 1));
                cir.setReturnValue(returnStack);
            }
        }
    }
}
