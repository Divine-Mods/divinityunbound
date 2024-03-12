package name.divinityunbound;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.entity.ModBlockEntities;
import name.divinityunbound.enchantment.ModEnchantments;
import name.divinityunbound.entity.effect.ModStatusEffect;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.item.ModItemGroups;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.particle.ModParticles;
import name.divinityunbound.recipe.ModRecipes;
import name.divinityunbound.screen.ModScreenHandlers;
import name.divinityunbound.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DivinityUnbound implements ModInitializer {
	public static final String MOD_ID = "divinityunbound";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModWorldGeneration.generateModWorldGen();
		ModFluids.registerFluids();
		ModEnchantments.registerModEnchantments();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModStatusEffect.registerStatusEffects();
		ModParticles.registerParticles();

		ModRecipes.registerRecipes();

		StrippableBlockRegistry.register(ModBlocks.WILDERSUNG_LOG, ModBlocks.STRIPPED_WILDERSUNG_LOG);
		StrippableBlockRegistry.register(ModBlocks.WILDERSUNG_WOOD, ModBlocks.STRIPPED_WILDERSUNG_WOOD);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WILDERSUNG_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WILDERSUNG_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_WILDERSUNG_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_WILDERSUNG_WOOD, 5, 5);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WILDERSUNG_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CELESTITE_INFUSED_WILDERSUNG_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WILDERSUNG_LEAVES, 30, 60);
	}
}