package net.dman.thepicklejar.datagen;

import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> PICKLOLIUM_SMELTABLES = List.of(ModItems.RAW_PICKLOLIUM,
            ModBlocks.PICKLOLIUM_DEPOSIT);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, PICKLOLIUM_SMELTABLES, RecipeCategory.MISC, ModItems.PICKLOLIUM,
                0.7f, 200, "picklejar");
        offerBlasting(exporter, PICKLOLIUM_SMELTABLES, RecipeCategory.MISC, ModItems.PICKLOLIUM,
                0.7f, 100, "picklejar");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PICKLOLIUM, RecipeCategory.DECORATIONS,
                ModBlocks.PICKLOLIUM_BLOCK);

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
    }
}
