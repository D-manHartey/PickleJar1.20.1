package net.dman.thepicklejar.compat;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.ModBlocks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class GiardinieraAltarCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(ThePickleJar.MOD_ID, "textures/gui/giardiniera_altar_gui.png");
    public static final CategoryIdentifier<GiardinieraAltarDisplay> GIARDINIERA_ALTAR =
            CategoryIdentifier.of(ThePickleJar.MOD_ID, "giardiniera_altering");


    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return GIARDINIERA_ALTAR;
    }

    @Override
    public Text getTitle() {
        return Text.literal("Giardiniera Altar");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.GIARDINIERA_ALTAR.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 26, startPoint.y + 17))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 81, startPoint.y + 17))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 135, startPoint.y + 17))
                .entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 81, startPoint.y + 97))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }
}
