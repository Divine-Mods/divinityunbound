package name.divinityunbound.screen;

import name.divinityunbound.block.entity.SpaceTimeFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;

public class SpaceTimeFurnaceScreenHandler extends AbstractFurnaceScreenHandler {
    public SpaceTimeFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.SPACE_TIME_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public SpaceTimeFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, SpaceTimeFurnaceBlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.SPACE_TIME_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory, blockEntity, propertyDelegate);
    }

    @Override
    protected boolean isFuel(ItemStack itemStack) {
        return true;
    }
}