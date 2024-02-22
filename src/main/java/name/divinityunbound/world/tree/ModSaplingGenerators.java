package name.divinityunbound.world.tree;

import name.divinityunbound.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;


public class ModSaplingGenerators {
    public static final SaplingGenerator WILDERSUNG =
            new SaplingGenerator("wildersung", 0f, Optional.empty(),
                    Optional.empty(),
                    Optional.of(ModConfiguredFeatures.WILDERSUNG_KEY),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty());
}
