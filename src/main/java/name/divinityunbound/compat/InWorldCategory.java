package name.divinityunbound.compat;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;


// Done with the help:
// https://github.com/TeamGalacticraft/Galacticraft/tree/main (MIT License)
public class InWorldCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(DivinityUnbound.MOD_ID, "textures/gui/in_world_gui.png");
    public static final CategoryIdentifier<ChronosTimeAccumulatorDisplay> IN_WORLD_CRAFTING =
            CategoryIdentifier.of(DivinityUnbound.MOD_ID, "in_world_crafting");
    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return IN_WORLD_CRAFTING;
    }

    @Override
    public Text getTitle() {
        return Text.literal("In World Crafting");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Blocks.GRASS_BLOCK.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 176, 38)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 20, startPoint.y + 11))
                .markOutput().entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 11))
                .markOutput().entries(display.getInputEntries().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 140, startPoint.y + 11))
                .markOutput().entries(display.getOutputEntries().get(0)));

        widgets.add(Widgets.createRecipeBase(new Rectangle(
                startPoint.x + 8, startPoint.y + 40,
                115, 18)));

        widgets.add(Widgets.createLabel(new Point(startPoint.x + 65, startPoint.y + 45),
                Text.literal(
                        ((InWorldDisplay)display).getDimension()
                )));

        widgets.add(Widgets.createRecipeBase(new Rectangle(
                startPoint.x + 8, startPoint.y + 60,
                75, 18)));

        widgets.add(Widgets.createLabel(new Point(startPoint.x + 45, startPoint.y + 65),
                Text.literal(
                        ((InWorldDisplay)display).getYlevel()
                )));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }
}
