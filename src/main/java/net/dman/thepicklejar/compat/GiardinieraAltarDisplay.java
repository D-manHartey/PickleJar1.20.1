package net.dman.thepicklejar.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.dman.thepicklejar.recipe.GiardinieraAltarRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiardinieraAltarDisplay extends BasicDisplay {
    public GiardinieraAltarDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public GiardinieraAltarDisplay(GiardinieraAltarRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                List.of(EntryIngredients.of(recipe.getOutput(null)))
        );
    }

    private static List<EntryIngredient> getInputList(GiardinieraAltarRecipe recipe) {
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return GiardinieraAltarCategory.GIARDINIERA_ALTAR;
    }
}
