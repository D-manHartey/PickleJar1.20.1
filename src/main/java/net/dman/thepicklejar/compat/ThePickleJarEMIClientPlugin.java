package net.dman.thepicklejar.compat;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.recipe.GiardinieraAltarRecipe;
import net.dman.thepicklejar.recipe.ModRecipes;

public class ThePickleJarEMIClientPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(GiardinieraAltarEmiRecipe.CATEGORY);
        registry.addWorkstation(GiardinieraAltarEmiRecipe.CATEGORY,
                EmiStack.of(ModBlocks.GIARDINIERA_ALTAR));

        for (GiardinieraAltarRecipe recipe :
        registry.getRecipeManager().listAllOfType(ModRecipes.GIARDINIERA_ALTAR_TYPE)) {
            registry.addRecipe(new GiardinieraAltarEmiRecipe(recipe));
        }
    }
}
