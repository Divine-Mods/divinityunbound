package name.divinityunbound.item.custom;

import name.divinityunbound.entity.effect.ModStatusEffect;
import name.divinityunbound.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class HermesBootsArmorItem extends ArmorItem {
    public HermesBootsArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if(entity instanceof PlayerEntity player && hasBootsOn(player)) {
                if(!player.hasStatusEffect(ModStatusEffect.HERMES_EFFECT)) {
                    player.addStatusEffect(new StatusEffectInstance(ModStatusEffect.HERMES_EFFECT, 200, 0,
                            false, false, true));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 2,
                            false, false, false));
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean hasBootsOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);

        return !boots.isEmpty() && boots.getItem().equals(ModItems.HERMES_BOOTS);
    }
}
