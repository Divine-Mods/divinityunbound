package name.divinityunbound.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.recipe.ChronosTimeAccumulatorRecipe;
import name.divinityunbound.recipe.GenerationStationRecipe;
import name.divinityunbound.recipe.InWorldRecipe;
import name.divinityunbound.screen.ChronosTimeAccumulatorScreen;
import name.divinityunbound.screen.GenerationStationScreen;
import net.minecraft.block.Blocks;

public class DivinityModREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new GenerationStationCategory());
        registry.add(new ChronosTimeAccumulatorCategory());
        registry.add(new InWorldCategory());

        registry.addWorkstations(GenerationStationCategory.GENERATION_STATION, EntryStacks.of(ModBlocks.GENERATION_STATION));
        registry.addWorkstations(ChronosTimeAccumulatorCategory.CHRONOS_TIME_ACCUMULATOR, EntryStacks.of(ModBlocks.CHRONOS_TIME_ACCUMULATOR));
        registry.addWorkstations(InWorldCategory.IN_WORLD_CRAFTING, EntryStacks.of(Blocks.GRASS_BLOCK));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(GenerationStationRecipe.class, GenerationStationRecipe.Type.INSTANCE,
                GenerationStationDisplay::new);
        registry.registerRecipeFiller(ChronosTimeAccumulatorRecipe.class, ChronosTimeAccumulatorRecipe.Type.INSTANCE,
                ChronosTimeAccumulatorDisplay::new);
        registry.registerRecipeFiller(InWorldRecipe.class, InWorldRecipe.Type.INSTANCE,
                InWorldDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), GenerationStationScreen.class,
                GenerationStationCategory.GENERATION_STATION);
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), ChronosTimeAccumulatorScreen.class,
                ChronosTimeAccumulatorCategory.CHRONOS_TIME_ACCUMULATOR);
    }
}
