package net.dman.thepicklejar.datagen;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> PICKLOLIUM_SMELTABLES = List.of(ModItems.RAW_PICKLOLIUM,
            ModBlocks.PICKLOLIUM_DEPOSIT);
    private static final List<ItemConvertible> CHUTNEY_SMELTABLES = List.of(ModItems.RAW_CHUTNEY,
            ModBlocks.CHUTNEY_DEPOSIT);
    private static final List<ItemConvertible> TEA_SMELTABLES = List.of(ModItems.GREEN_TEA_LEAVES,
            ModItems.CUP_O_GREEN_TEA);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, PICKLOLIUM_SMELTABLES, RecipeCategory.MISC, ModItems.PICKLOLIUM,
                0.7f, 200, "picklejar");
        offerBlasting(exporter, PICKLOLIUM_SMELTABLES, RecipeCategory.MISC, ModItems.PICKLOLIUM,
                0.7f, 100, "picklejar");
        offerSmelting(exporter, CHUTNEY_SMELTABLES, RecipeCategory.MISC, ModItems.MOLTEN_CHUTNEY,
                0.8f, 350, "picklejar");
        offerBlasting(exporter, CHUTNEY_SMELTABLES, RecipeCategory.MISC, ModItems.MOLTEN_CHUTNEY,
                0.8f, 200, "picklejar");
        offerSmelting(exporter, TEA_SMELTABLES, RecipeCategory.FOOD, ModItems.CUP_O_GREEN_TEA,
                0.4f, 150, "picklejar");

        // hella fast brew
        CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(ModItems.GREEN_TEA_LEAVES), RecipeCategory.FOOD, ModItems.CUP_O_GREEN_TEA, 0.3f, 50)
                .criterion(hasItem(ModItems.GREEN_TEA_LEAVES), conditionsFromItem(ModItems.CUP_O_GREEN_TEA))
                .offerTo(exporter, new Identifier(ThePickleJar.MOD_ID, "green_tea_brewing"));
        //slow ass brew
        CookingRecipeJsonBuilder.createCampfireCooking(Ingredient.ofItems(ModItems.GREEN_TEA_LEAVES), RecipeCategory.FOOD, ModItems.CUP_O_GREEN_TEA, 0.3f, 500)
                .criterion(hasItem(ModItems.GREEN_TEA_LEAVES), conditionsFromItem(ModItems.CUP_O_GREEN_TEA))
                .offerTo(exporter, new Identifier(ThePickleJar.MOD_ID, "campfire_tea_brew"));


        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PICKLOLIUM, RecipeCategory.DECORATIONS,
                ModBlocks.PICKLOLIUM_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.MOLTEN_CHUTNEY, RecipeCategory.DECORATIONS,
                ModBlocks.CHUTNEY_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GOLDEN_PICKLE, 1)
                .pattern("DGD")
                .pattern("GPG")
                .pattern("DGD")
                .input('G', Items.GOLD_BLOCK)
                .input('P', ModItems.PICKLE)
                .input('D', ModItems.PICKLOLIUM)
                .criterion(hasItem(Items.GOLD_BLOCK), conditionsFromItem(Items.GOLD_BLOCK))
                .criterion(hasItem(ModItems.PICKLE), conditionsFromItem(ModItems.PICKLE))
                .criterion(hasItem(ModItems.PICKLOLIUM), conditionsFromItem(ModItems.PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GOLDEN_PICKLE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GHERKIN_SOUL, 4)
                .pattern(" C ")
                .pattern("CSC")
                .pattern(" C ")
                .input('S', Items.SEA_PICKLE)
                .input('C', Items.COAL)
                .criterion(hasItem(Items.SEA_PICKLE), conditionsFromItem(Items.SEA_PICKLE))
                .criterion(hasItem(Items.COAL), conditionsFromItem(Items.COAL))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GHERKIN_SOUL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TOON_STEEL, 1)
                .pattern("CIG")
                .pattern("NPL")
                .pattern("RED")
                .input('P', ModItems.PICKLOLIUM)
                .input('C', Items.COPPER_INGOT)
                .input('I', Items.IRON_INGOT)
                .input('G', Items.GOLD_INGOT)
                .input('N', Items.NETHERITE_INGOT)
                .input('L', Items.LAPIS_LAZULI)
                .input('R', Items.REDSTONE)
                .input('E', Items.EMERALD)
                .input('D', Items.DIAMOND)
                .criterion(hasItem(ModItems.PICKLOLIUM), conditionsFromItem(ModItems.PICKLOLIUM))
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .criterion(hasItem(Items.LAPIS_LAZULI), conditionsFromItem(Items.LAPIS_LAZULI))
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_STEEL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RADIOACTIVE_PICKLOLIUM, 1)
                .pattern("PPP")
                .pattern("PCC")
                .pattern("CC ")
                .input('P', ModItems.PICKLOLIUM)
                .input('C', ModItems.MOLTEN_CHUTNEY)
                .criterion(hasItem(ModItems.PICKLOLIUM), conditionsFromItem(ModItems.PICKLOLIUM))
                .criterion(hasItem(ModItems.MOLTEN_CHUTNEY), conditionsFromItem(ModItems.MOLTEN_CHUTNEY))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RADIOACTIVE_PICKLOLIUM)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INKBLOT_MALLET, 1)
                .pattern("TRT")
                .pattern("TST")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INKBLOT_MALLET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAGGIDY_SCYTHE, 1)
                .pattern(" RT")
                .pattern(" SR")
                .pattern("S R")
                .input('S', Items.STICK)
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RAGGIDY_SCYTHE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TOON_HELMET, 1)
                .pattern("TRT")
                .pattern("T T")
                .pattern("   ")
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_HELMET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TOON_CHESTPLATE, 1)
                .pattern("T T")
                .pattern("TRT")
                .pattern("RTR")
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_CHESTPLATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TOON_LEGGINGS, 1)
                .pattern("TRT")
                .pattern("R R")
                .pattern("T T")
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_LEGGINGS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TOON_BOOTS, 1)
                .pattern("   ")
                .pattern("T T")
                .pattern("R R")
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_BOOTS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PICKLE, 2)
                .pattern("   ")
                .pattern("PS ")
                .pattern("   ")
                .input('P', Items.SEA_PICKLE)
                .input('S', Items.SUGAR)
                .criterion(hasItem(Items.SEA_PICKLE), conditionsFromItem(Items.SEA_PICKLE))
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PICKLE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PICKLE_ALFREDO, 1)
                .pattern("   ")
                .pattern("PW ")
                .pattern(" B ")
                .input('P', ModItems.PICKLE)
                .input('W', Items.WHEAT)
                .input('B', ModItems.PEANUT_BUTTER)
                .criterion(hasItem(ModItems.PICKLE), conditionsFromItem(ModItems.PICKLE))
                .criterion(hasItem(Items.WHEAT), conditionsFromItem(Items.WHEAT))
                .criterion(hasItem(ModItems.PEANUT_BUTTER), conditionsFromItem(ModItems.PEANUT_BUTTER))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PICKLE_ALFREDO)));
    }
}
