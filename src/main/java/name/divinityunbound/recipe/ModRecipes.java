package name.divinityunbound.recipe;

import name.divinityunbound.DivinityUnbound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(DivinityUnbound.MOD_ID, GenerationStationRecipe.Serializer.ID),
                GenerationStationRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(DivinityUnbound.MOD_ID, GenerationStationRecipe.Type.ID),
                GenerationStationRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(DivinityUnbound.MOD_ID, ChronosTimeAccumulatorRecipe.Serializer.ID),
                ChronosTimeAccumulatorRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(DivinityUnbound.MOD_ID, ChronosTimeAccumulatorRecipe.Type.ID),
                ChronosTimeAccumulatorRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(DivinityUnbound.MOD_ID, InWorldRecipe.Serializer.ID),
                InWorldRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(DivinityUnbound.MOD_ID, InWorldRecipe.Type.ID),
                InWorldRecipe.Type.INSTANCE);
    }
}
