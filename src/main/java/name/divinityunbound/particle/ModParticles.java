package name.divinityunbound.particle;

import name.divinityunbound.DivinityUnbound;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType PURPLE_FLAME_PARTICLE =
            registerParticle("purple_flame_particle", FabricParticleTypes.simple());


    private static DefaultParticleType registerParticle(String name, DefaultParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(DivinityUnbound.MOD_ID, name), particleType);
    }

    public static void registerParticles() {
        DivinityUnbound.LOGGER.info("Registering Particles for " + DivinityUnbound.MOD_ID);
    }
}
