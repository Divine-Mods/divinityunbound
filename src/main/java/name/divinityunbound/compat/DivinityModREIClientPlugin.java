package name.divinityunbound.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.recipe.GenerationStationRecipe;
import name.divinityunbound.screen.GenerationStationScreen;

public class DivinityModREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new GenerationStationCategory());

        registry.addWorkstations(GenerationStationCategory.GENERATION_STATION, EntryStacks.of(ModBlocks.GENERATION_STATION));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(GenerationStationRecipe.class, GenerationStationRecipe.Type.INSTANCE,
                GenerationStationDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), GenerationStationScreen.class,
                GenerationStationCategory.GENERATION_STATION);
    }
}
