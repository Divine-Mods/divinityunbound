package name.divinityunbound.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import name.divinityunbound.recipe.GenerationStationRecipe;
import name.divinityunbound.recipe.InWorldRecipe;
import net.minecraft.recipe.RecipeEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InWorldDisplay extends BasicDisplay {
    public InWorldDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, String dimension, String ylevel) {
        super(inputs, outputs);
        this.dimension = dimension;
        this.ylevel = ylevel;
    }

    private final String dimension;
    private final String ylevel;
    public InWorldDisplay(RecipeEntry<InWorldRecipe> recipe) {
        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getResult(null)))));
        this.dimension = "dimension:" + recipe.value().getDimension().asString();
        this.ylevel = "y-level:" + recipe.value().getYlevel().asString();
    }

    private static List<EntryIngredient> getInputList(InWorldRecipe recipe) {
        if(recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(1)));
        return list;
    }

    public String getDimension() {
        return this.dimension;
    }
    public String getYlevel() {
        return this.ylevel;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return InWorldCategory.IN_WORLD_CRAFTING;
    }
}
