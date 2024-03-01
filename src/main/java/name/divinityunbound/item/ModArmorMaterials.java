package name.divinityunbound.item;

import name.divinityunbound.DivinityUnbound;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    CELESTITE("celestite", 25, new int[] { 3, 8, 6, 3}, 10,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1f, 0.1f,
            () -> Ingredient.ofItems(ModItems.CELESTITE)),

    TIME_FORGED("time_forged", 30, new int[] { 3, 8, 6, 3}, 15,
    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2f, 0.2f,
            () -> Ingredient.ofItems(ModItems.TIME_FORGED_INGOT)),

    SPACE_FORGED("space_forged", 35, new int[] { 3, 8, 6, 3}, 25,
    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5f, 0.2f,
            () -> Ingredient.ofItems(ModItems.SPACE_FORGED_INGOT));

    // TODO: Add time forged armor and other tiers of armor
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts,
                      int enchantability, SoundEvent equipSound, float toughness,
                      float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return DivinityUnbound.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
