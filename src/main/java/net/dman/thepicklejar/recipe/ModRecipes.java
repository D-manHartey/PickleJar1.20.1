package net.dman.thepicklejar.recipe;

import net.dman.thepicklejar.ThePickleJar;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<GiardinieraAltarRecipe> GIARDINIERA_ALTAR_SERIALIZER =
            Registry.register(
                    Registries.RECIPE_SERIALIZER,
                    new Identifier(ThePickleJar.MOD_ID, GiardinieraAltarRecipe.Serializer.ID),
                    GiardinieraAltarRecipe.Serializer.INSTANCE
            );

    public static final RecipeType<GiardinieraAltarRecipe> GIARDINIERA_ALTAR_TYPE =
            Registry.register(
                    Registries.RECIPE_TYPE,
                    new Identifier(ThePickleJar.MOD_ID, GiardinieraAltarRecipe.Type.ID),
                    GiardinieraAltarRecipe.Type.INSTANCE
            );

    public static void registerRecipes() {
        ThePickleJar.LOGGER.info("Registering recipes for " + ThePickleJar.MOD_ID);
    }
}
