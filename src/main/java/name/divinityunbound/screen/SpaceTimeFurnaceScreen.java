package name.divinityunbound.screen;

import name.divinityunbound.DivinityUnbound;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpaceTimeFurnaceScreen extends AbstractFurnaceScreen<SpaceTimeFurnaceScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(DivinityUnbound.MOD_ID, "textures/gui/space_time_furnace_gui.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = new Identifier("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = new Identifier("container/furnace/burn_progress");

    public SpaceTimeFurnaceScreen(SpaceTimeFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new FurnaceRecipeBookScreen(), inventory, title, TEXTURE, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE);
    }
}