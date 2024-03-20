package name.divinityunbound.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class InWorldRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final List<Ingredient> recipeItems;

    private final DimensionCategory dimension;
    private final YlevelCategory ylevel;

    public InWorldRecipe(List<Ingredient> ingredients, ItemStack itemStack, DimensionCategory dimension, YlevelCategory ylevel) {
        this.output = itemStack;
        this.recipeItems = ingredients;
        this.dimension = dimension;
        this.ylevel = ylevel;
    }
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) {
            return false;
        }

        return recipeItems.get(0).test(inventory.getStack(0)) && recipeItems.get(1).test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public DimensionCategory getDimension() {
        return dimension;
    }
    public YlevelCategory getYlevel() {
        return ylevel;
    }

    public static class Type implements  RecipeType<InWorldRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "in_world_crafting";
    }

    public enum DimensionCategory implements StringIdentifiable
    {
        OVERWORLD("overworld"),
        REDSTONE("the_nether"),
        EQUIPMENT("the_end");

        public static final Codec<DimensionCategory> CODEC;
        private final String id;

        private DimensionCategory(String id) {
            this.id = id;
        }

        @Override
        public String asString() {
            return this.id;
        }

        static {
            CODEC = StringIdentifiable.createCodec(DimensionCategory::values);
        }
    }

    public enum YlevelCategory implements StringIdentifiable
    {
        ANY("any"),
        HIGHER(">125");

        public static final Codec<YlevelCategory> CODEC;
        private final String id;

        private YlevelCategory(String id) {
            this.id = id;
        }

        @Override
        public String asString() {
            return this.id;
        }

        static {
            CODEC = StringIdentifiable.createCodec(YlevelCategory::values);
        }
    }

    public static class Serializer implements  RecipeSerializer<InWorldRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "in_world_crafting";

        public static final Codec<InWorldRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 9).fieldOf("ingredients").forGetter(InWorldRecipe::getIngredients),
                ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").forGetter(r -> r.output),
                DimensionCategory.CODEC.fieldOf("dimension").forGetter(InWorldRecipe::getDimension),
                YlevelCategory.CODEC.fieldOf("ylevel").forGetter(InWorldRecipe::getYlevel)
        ).apply(in, InWorldRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
            return Codecs.validate(Codecs.validate(
                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }

        @Override
        public Codec<InWorldRecipe> codec() {
            return CODEC;
        }

        @Override
        public InWorldRecipe read(PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            DimensionCategory dimensionCategory = buf.readEnumConstant(DimensionCategory.class);
            YlevelCategory ylevelCategory = buf.readEnumConstant(YlevelCategory.class);

            return new InWorldRecipe(inputs, output, dimensionCategory, ylevelCategory);
        }

        @Override
        public void write(PacketByteBuf buf, InWorldRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.getResult(null));

            buf.writeEnumConstant(recipe.getDimension());
        }
    }
}
