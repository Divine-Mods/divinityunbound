package name.divinityunbound.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent ETHEREAL_CRYSTAL_GREENS = new FoodComponent.Builder().hunger(2).saturationModifier(0.25f)
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 200), 0.25f)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200), 0.25f).build();
}
