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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;


// Done with the help:
// https://github.com/TeamGalacticraft/Galacticraft/tree/main (MIT License)
public class ChronosTimeAccumulatorCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(DivinityUnbound.MOD_ID, "textures/gui/chronos_time_accumulator_gui.png");
    public static final CategoryIdentifier<ChronosTimeAccumulatorDisplay> CHRONOS_TIME_ACCUMULATOR =
            CategoryIdentifier.of(DivinityUnbound.MOD_ID, "chronos_time_accumulator");
    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return CHRONOS_TIME_ACCUMULATOR;
    }

    @Override
    public Text getTitle() {
        return Text.literal("Chronos Time Accumulator");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.CHRONOS_TIME_ACCUMULATOR.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 59))
                .markOutput().entries(display.getOutputEntries().get(0)));


        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }
}
