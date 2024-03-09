package name.divinityunbound.world;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> CELESTITE_ORE_KEY = registerKey("celestite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> EXPERIENCE_ORE_KEY = registerKey("experience_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> WILDERSUNG_KEY = registerKey("wildersung");

    public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplacables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endReplacables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldCelestiteOres =
                List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.CELESTITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_CELESTITE_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldExperienceOres =
                List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.EXPERIENCE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_EXPERIENCE_ORE.getDefaultState()));

        register(context, CELESTITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldCelestiteOres, 12));
        register(context, EXPERIENCE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldExperienceOres, 12));

        register(context, WILDERSUNG_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.WILDERSUNG_LOG),
                new StraightTrunkPlacer(5, 4, 3),
                BlockStateProvider.of(ModBlocks.WILDERSUNG_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(2), 4),
                new TwoLayersFeatureSize(1, 2, 4)
        ).build());
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(DivinityUnbound.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}