package name.divinityunbound.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    CELESTITE(2, 600, 7.0f, 2.5f, 10, () -> Ingredient.ofItems(ModItems.CELESTITE)),
    TIME_FORGED(3, 1000, 8.0f, 3.0f, 15, () -> Ingredient.ofItems(ModItems.TIME_FORGED_INGOT)),
    SPACE_FORGED(3, 1800, 9.5f, 3.5f, 20, () -> Ingredient.ofItems(ModItems.SPACE_FORGED_INGOT)),
    SPACE_TIME_FORGED(4, 2500, 12f, 5.0f, 40, () -> Ingredient.ofItems(ModItems.SPACE_TIME_INGOT));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> supplier) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = supplier;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
