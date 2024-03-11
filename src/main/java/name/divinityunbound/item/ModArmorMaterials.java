package name.divinityunbound.item;

import name.divinityunbound.DivinityUnbound;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    CELESTITE("celestite", 18, new int[] { 2, 7, 5, 2}, 10,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f, 0.0f,
            () -> Ingredient.ofItems(ModItems.CELESTITE)),

    TIME_FORGED("time_forged", 24, new int[] { 3, 8, 6, 3}, 15,
    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1f, 0.0f,
            () -> Ingredient.ofItems(ModItems.TIME_FORGED_INGOT)),

    SPACE_FORGED("space_forged", 35, new int[] { 3, 8, 6, 3}, 25,
    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3f, 0.2f,
            () -> Ingredient.ofItems(ModItems.SPACE_FORGED_INGOT)),

    SPACE_TIME_FORGED("space_time_forged", 40, new int[] { 4, 9, 7, 4}, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4f, 0.3f,
            () -> Ingredient.ofItems(ModItems.SPACE_TIME_INGOT)),

    EXPERIENCE("experience", 15, new int[] { 1, 6, 4, 1}, 20,
    SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 0f, 0.0f,
            () -> Ingredient.ofItems(ModItems.EXPERIENCE_INGOT)),

    HERMES("hermes", 15, new int[] { 0, 0, 0, 1}, 20,
    SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 1f, 0.0f,
            () -> Ingredient.ofItems(ModItems.EXPERIENCE_INGOT));

    // TODO: Add space time forged armor
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
