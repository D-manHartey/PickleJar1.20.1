package net.dman.thepicklejar.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.recipe.GiardinieraAltarRecipe;
import net.dman.thepicklejar.screen.GiardinieraAltarScreen;

public class ThePickleJarREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new GiardinieraAltarCategory());

        registry.addWorkstations(GiardinieraAltarCategory.GIARDINIERA_ALTAR, EntryStacks.of(ModBlocks.GIARDINIERA_ALTAR));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(GiardinieraAltarRecipe.class, GiardinieraAltarRecipe.Type.INSTANCE,
                GiardinieraAltarDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), GiardinieraAltarScreen.class,
                GiardinieraAltarCategory.GIARDINIERA_ALTAR);
    }
}
