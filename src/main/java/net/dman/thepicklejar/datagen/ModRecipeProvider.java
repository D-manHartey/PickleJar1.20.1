package net.dman.thepicklejar.datagen;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.StonecuttingRecipe;
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

        //hella fast brew
        CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(ModItems.GREEN_TEA_LEAVES), RecipeCategory.FOOD, ModItems.CUP_O_GREEN_TEA, 0.3f, 50)
                .criterion(hasItem(ModItems.GREEN_TEA_LEAVES), conditionsFromItem(ModItems.CUP_O_GREEN_TEA))
                .offerTo(exporter, new Identifier(ThePickleJar.MOD_ID, "green_tea_brewing"));
        //slow ass brew
        CookingRecipeJsonBuilder.createCampfireCooking(Ingredient.ofItems(ModItems.GREEN_TEA_LEAVES), RecipeCategory.FOOD, ModItems.CUP_O_GREEN_TEA, 0.3f, 500)
                .criterion(hasItem(ModItems.GREEN_TEA_LEAVES), conditionsFromItem(ModItems.CUP_O_GREEN_TEA))
                .offerTo(exporter, new Identifier(ThePickleJar.MOD_ID, "campfire_brew"));


        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PICKLOLIUM, RecipeCategory.DECORATIONS,
                ModBlocks.PICKLOLIUM_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.MOLTEN_CHUTNEY, RecipeCategory.DECORATIONS,
                ModBlocks.CHUTNEY_BLOCK);

        //building blocks start

        offerStonecuttingRecipe(exporter,RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_STAIRS, ModBlocks.PHIL_BLOCK, 4);
        offerStonecuttingRecipe(exporter,RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_WALL, ModBlocks.PHIL_BLOCK, 6);
        offerStonecuttingRecipe(exporter,RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_BUTTON, ModBlocks.PHIL_BLOCK, 3);
        offerStonecuttingRecipe(exporter,RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_SLAB, ModBlocks.PHIL_BLOCK, 6);

        createStairsRecipe(ModBlocks.PHIL_STAIRS, Ingredient.ofItems(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                        .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_STAIRS)));
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_SLAB, Ingredient.ofItems(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_SLAB)));
        createPressurePlateRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_PRESSURE_PLATE, Ingredient.ofItems(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_PRESSURE_PLATE)));
        createDoorRecipe(ModBlocks.PHIL_DOOR, Ingredient.ofItems(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_DOOR)));
        createTrapdoorRecipe(ModBlocks.PHIL_TRAPDOOR, Ingredient.ofItems(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_TRAPDOOR)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_FENCE, 3)
                .pattern("PSP")
                .pattern("PSP")
                .pattern("   ")
                .input('P', ModBlocks.PHIL_BLOCK)
                .input('S', Items.STICK)
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_FENCE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_FENCE_GATE, 2)
                .pattern("SPS")
                .pattern("SPS")
                .pattern("   ")
                .input('P', ModBlocks.PHIL_BLOCK)
                .input('S', Items.STICK)
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_FENCE_GATE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_BUTTON, 1)
                .pattern(" P ")
                .pattern("   ")
                .pattern("   ")
                .input('P', ModBlocks.PHIL_BLOCK)
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_BUTTON)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_WALL, 6)
                .pattern("PMP")
                .pattern("PMP")
                .pattern("   ")
                .input('P', ModBlocks.PHIL_BLOCK)
                .input('M', Blocks.MUD)
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(Blocks.MUD), conditionsFromItem(Blocks.MUD))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_WALL)));
        //building blocks end


        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GOLDEN_PICKLE, 1)
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.INKBLOT_MALLET, 1)
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.RAGGIDY_SCYTHE, 1)
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TOON_HELMET, 1)
                .pattern("TRT")
                .pattern("T T")
                .pattern("   ")
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_HELMET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TOON_CHESTPLATE, 1)
                .pattern("T T")
                .pattern("TRT")
                .pattern("RTR")
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_CHESTPLATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TOON_LEGGINGS, 1)
                .pattern("TRT")
                .pattern("R R")
                .pattern("T T")
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_LEGGINGS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TOON_BOOTS, 1)
                .pattern("   ")
                .pattern("T T")
                .pattern("R R")
                .input('T', ModItems.TOON_STEEL)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.TOON_STEEL), conditionsFromItem(ModItems.TOON_STEEL))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.TOON_BOOTS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PICKLE, 2)
                .pattern("   ")
                .pattern("PS ")
                .pattern("   ")
                .input('P', Items.SEA_PICKLE)
                .input('S', Items.SUGAR)
                .criterion(hasItem(Items.SEA_PICKLE), conditionsFromItem(Items.SEA_PICKLE))
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PICKLE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PHIL_BLOCK, 1)
                .pattern("MMM")
                .pattern("CMP")
                .pattern("MMM")
                .input('M', Blocks.MUD)
                .input('C', ModItems.PICKLOLIUM)
                .input('P', ModItems.MOLTEN_CHUTNEY)
                .criterion(hasItem(Blocks.MUD), conditionsFromItem(Blocks.MUD))
                .criterion(hasItem(ModItems.PICKLOLIUM), conditionsFromItem(ModItems.PICKLOLIUM))
                .criterion(hasItem(ModItems.MOLTEN_CHUTNEY), conditionsFromItem(ModItems.MOLTEN_CHUTNEY))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.PHIL_BLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GIARDINIERA_ALTAR, 1)
                .pattern("RGR")
                .pattern("HPH")
                .pattern("PPP")
                .input('G', ModItems.GOLDEN_PICKLE)
                .input('H', ModBlocks.PHIL_BLOCK)
                .input('P', ModBlocks.PICKLOLIUM_BLOCK)
                .input('R', ModItems.RADIOACTIVE_PICKLOLIUM)
                .criterion(hasItem(ModItems.GOLDEN_PICKLE), conditionsFromItem(ModItems.GOLDEN_PICKLE))
                .criterion(hasItem(ModBlocks.PHIL_BLOCK), conditionsFromItem(ModBlocks.PHIL_BLOCK))
                .criterion(hasItem(ModBlocks.PICKLOLIUM_BLOCK), conditionsFromItem(ModBlocks.PICKLOLIUM_BLOCK))
                .criterion(hasItem(ModItems.RADIOACTIVE_PICKLOLIUM), conditionsFromItem(ModItems.RADIOACTIVE_PICKLOLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.GIARDINIERA_ALTAR)));

    }
}
