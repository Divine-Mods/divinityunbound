package name.divinityunbound.enchantment;

import name.divinityunbound.DivinityUnbound;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static Enchantment BANE_OF_PASSIVES = register("bane_of_passives",
            new BaneOfPassivesEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static Enchantment PROSPERITY = register("prosperity",
            new ProsperityEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(DivinityUnbound.MOD_ID, name), enchantment);
    }
    public static void registerModEnchantments() {
        DivinityUnbound.LOGGER.info("Registering Enchantments for " + DivinityUnbound.MOD_ID);
    }
}
