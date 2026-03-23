package net.dman.thepicklejar.datagen;

import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.item.custom.PeanutButterCropBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.PICKLOLIUM_BLOCK);
        addDrop(ModBlocks.CHUTNEY_BLOCK);

        addDrop(ModBlocks.PICKLOLIUM_DEPOSIT, copperLikeOreDrops(ModBlocks.PICKLOLIUM_DEPOSIT, ModItems.RAW_PICKLOLIUM));
        addDrop(ModBlocks.CHUTNEY_DEPOSIT, copperLikeOreDrops(ModBlocks.CHUTNEY_DEPOSIT, ModItems.RAW_CHUTNEY));

        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(ModBlocks.PEANUT_CROP).properties(StatePredicate.Builder.create()
                .exactMatch(PeanutButterCropBlock.AGE, 5));
        addDrop(ModBlocks.PEANUT_CROP, cropDrops(ModBlocks.PEANUT_CROP, ModItems.PEANUT_BUTTER, ModItems.PEANUT_SEEDS, builder));

    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(drop, (LootPoolEntry.Builder<?>)this.applyExplosionDecay(drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction
                                        .builder(UniformLootNumberProvider
                                                .create(1.0F, 1.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}
