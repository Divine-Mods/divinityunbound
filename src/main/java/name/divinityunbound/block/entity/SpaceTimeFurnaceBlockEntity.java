package name.divinityunbound.block.entity;

import name.divinityunbound.block.custom.SpaceTimeFurnaceBlock;
import name.divinityunbound.screen.SpaceTimeFurnaceScreenHandler;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SpaceTimeFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    private final RecipeManager.MatchGetter<Inventory, ? extends AbstractCookingRecipe> matchGetter;
    public SpaceTimeFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPACE_TIME_FURNACE_BLOCK_ENTITY, pos, state, RecipeType.SMELTING);
        this.matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("block.divinityunbound.space_time_furnace");
    }

    int burnTime;
    int fuelTime;
    int cookTime;
    int cookTimeTotal;
    public static void tick(World world, BlockPos pos, BlockState state, SpaceTimeFurnaceBlockEntity blockEntity) {
        boolean bl4;
        boolean bl = blockEntity.isBurning();
        boolean bl2 = false;;
        if (blockEntity.isBurning()) {
            --blockEntity.burnTime;
        }
        ItemStack itemStack = blockEntity.inventory.get(1);
        boolean bl3 = !blockEntity.inventory.get(0).isEmpty();
        boolean bl5 = bl4 = !itemStack.isEmpty();
        if (blockEntity.isBurning() || bl4 && bl3) {
            RecipeEntry recipeEntry = bl3 ? (RecipeEntry) blockEntity.matchGetter.getFirstMatch(blockEntity, world).orElse(null) : null;
            int i = blockEntity.getMaxCountPerStack();
            if (!blockEntity.isBurning() && SpaceTimeFurnaceBlockEntity.canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                blockEntity.fuelTime = blockEntity.burnTime = blockEntity.getFuelTime(itemStack);
                if (blockEntity.isBurning()) {
                    bl2 = true;
                    if (bl4) {
                        Item item = itemStack.getItem();
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            Item item2 = item.getRecipeRemainder();
                            blockEntity.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                        }
                    }
                }
            }
            if (blockEntity.isBurning() && SpaceTimeFurnaceBlockEntity.canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                ++blockEntity.cookTime;
//                if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                    blockEntity.cookTime = 0;
                    blockEntity.cookTimeTotal = SpaceTimeFurnaceBlockEntity.getCookTime(world, blockEntity);
                    if (SpaceTimeFurnaceBlockEntity.craftRecipe(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                        blockEntity.setLastRecipe(recipeEntry);
                    }
                    bl2 = true;
                //}
            } else {
                blockEntity.cookTime = 0;
            }
        } else if (!blockEntity.isBurning() && blockEntity.cookTime > 0) {
            blockEntity.cookTime = MathHelper.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
        }
        if (bl != blockEntity.isBurning()) {
            bl2 = true;
            state = (BlockState) state.with(SpaceTimeFurnaceBlock.LIT, blockEntity.isBurning());
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (bl2) {
            SpaceTimeFurnaceBlockEntity.markDirty(world, pos, state);
        }
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    private static int getCookTime(World world, SpaceTimeFurnaceBlockEntity furnace) {
        return 10;
        //return furnace.matchGetter.getFirstMatch(furnace, world).map(recipe -> ((AbstractCookingRecipe)recipe.value()).getCookingTime()).orElse(200);
    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (slots.get(0).isEmpty() || recipe == null) {
            return false;
        }
        ItemStack itemStack = recipe.value().getResult(registryManager);
        if (itemStack.isEmpty()) {
            return false;
        }
        ItemStack itemStack2 = slots.get(2);
        if (itemStack2.isEmpty()) {
            return true;
        }
        if (!ItemStack.areItemsEqual(itemStack2, itemStack)) {
            return false;
        }
        if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
            return true;
        }
        return itemStack2.getCount() < itemStack.getMaxCount();
    }

    private static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe == null || !SpaceTimeFurnaceBlockEntity.canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            return false;
        }
        ItemStack itemStack = slots.get(0);
        ItemStack itemStack2 = recipe.value().getResult(registryManager);
        ItemStack itemStack3 = slots.get(2);
        if (itemStack3.isEmpty()) {
            slots.set(2, itemStack2.copy());
        } else if (itemStack3.isOf(itemStack2.getItem())) {
            itemStack3.increment(1);
        }
        if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !slots.get(1).isEmpty() && slots.get(1).isOf(Items.BUCKET)) {
            slots.set(1, new ItemStack(Items.WATER_BUCKET));
        }
        itemStack.decrement(1);
        return true;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new SpaceTimeFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) / 2;
    }


}