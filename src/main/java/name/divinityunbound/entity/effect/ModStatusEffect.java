package name.divinityunbound.entity.effect;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffect {
    public static final StatusEffect FLIGHT_EFFECT = Registry.register(Registries.STATUS_EFFECT,
            new Identifier(DivinityUnbound.MOD_ID, "flight"), new FlightStatusEffect());

    public static final StatusEffect EXPERIENCE_BOOST_EFFECT = Registry.register(Registries.STATUS_EFFECT,
            new Identifier(DivinityUnbound.MOD_ID, "experience_boost"), new ExperienceBoostEffect());
    public static void registerStatusEffects() {
        DivinityUnbound.LOGGER.info("Registering Status Effects for " + DivinityUnbound.MOD_ID);
    }
}
