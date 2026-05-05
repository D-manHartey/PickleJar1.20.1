package net.dman.thepicklejar.compat;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.recipe.GiardinieraAltarRecipe;
import net.minecraft.util.Identifier;

public class GiardinieraAltarEmiRecipe extends BasicEmiRecipe {
    public static final Identifier TEXTURE =
            new Identifier(ThePickleJar.MOD_ID, "textures/gui/giardiniera_altar_gui.png");
    public static final EmiRecipeCategory CATEGORY =
            new EmiRecipeCategory(new Identifier(ThePickleJar.MOD_ID, "giardiniera_altering"),
                    EmiStack.of(ModBlocks.GIARDINIERA_ALTAR));

    public GiardinieraAltarEmiRecipe(GiardinieraAltarRecipe recipe) {
        super(CATEGORY, recipe.getId(), 178, 122);

        recipe.getIngredients().forEach(ingredient -> this.inputs.add(EmiIngredient.of(ingredient)));
        this.outputs.add(EmiStack.of(recipe.getOutput(null)));
        this.catalysts.add(EmiStack.of(ModBlocks.GIARDINIERA_ALTAR));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE, 0, 0, 178, 122, 0, 4, 178, 122, 256, 256);

        widgets.addSlot(inputs.get(0), 26, 17);
        widgets.addSlot(inputs.get(1), 81, 17);
        widgets.addSlot(inputs.get(2), 135, 17);
        widgets.addSlot(outputs.get(0), 81, 97).recipeContext(this);
    }
}
