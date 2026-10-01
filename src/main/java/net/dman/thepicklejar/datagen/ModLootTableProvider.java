package net.dman.thepicklejar.datagen;

import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.block.custom.TeaLeavesCropBlock;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.block.custom.PeanutButterCropBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.PICKLOLIUM_BLOCK);
        addDrop(ModBlocks.CHUTNEY_BLOCK);
        addDrop(ModBlocks.PHIL_BLOCK);

        addDrop(ModBlocks.PHIL_BUTTON);
        addDrop(ModBlocks.PHIL_FENCE);
        addDrop(ModBlocks.PHIL_FENCE_GATE);
        addDrop(ModBlocks.PHIL_PRESSURE_PLATE);
        addDrop(ModBlocks.PHIL_TRAPDOOR);
        addDrop(ModBlocks.PHIL_STAIRS);
        addDrop(ModBlocks.PHIL_WALL);
        addDrop(ModBlocks.GIARDINIERA_ALTAR);

        addDrop(ModBlocks.PHIL_DOOR, doorDrops(ModBlocks.PHIL_DOOR));
        addDrop(ModBlocks.PHIL_SLAB, slabDrops(ModBlocks.PHIL_SLAB));



        addDrop(ModBlocks.PICKLOLIUM_DEPOSIT, copperLikeOreDrops(ModBlocks.PICKLOLIUM_DEPOSIT, ModItems.RAW_PICKLOLIUM));
        addDrop(ModBlocks.CHUTNEY_DEPOSIT, chutneyDepositDrops());


        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(ModBlocks.PEANUT_CROP).properties(StatePredicate.Builder.create()
                .exactMatch(PeanutButterCropBlock.AGE, 5));
        addDrop(ModBlocks.PEANUT_CROP, cropDrops(ModBlocks.PEANUT_CROP, ModItems.PEANUT_BUTTER, ModItems.PEANUT_SEEDS, builder));


        AnyOfLootCondition.Builder builder2 =
                BlockStatePropertyLootCondition.builder(ModBlocks.TEA_LEAVES_CROP).properties(StatePredicate.Builder.create()
                        .exactMatch(TeaLeavesCropBlock.AGE, 7))
                        .or(BlockStatePropertyLootCondition.builder(ModBlocks.TEA_LEAVES_CROP).properties(StatePredicate.Builder.create()
                                .exactMatch(TeaLeavesCropBlock.AGE, 8)));
        addDrop(ModBlocks.TEA_LEAVES_CROP, cropDrops(ModBlocks.TEA_LEAVES_CROP, ModItems.GREEN_TEA_LEAVES, ModItems.TEA_LEAF_SEEDS, builder2));

        addDrop(ModBlocks.GREEN_CHRYSANTHEMUM);
        addPottedPlantDrops(ModBlocks.POTTED_GREEN_CHRYSANTHEMUM);

    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(drop, (LootPoolEntry.Builder<?>)this.applyExplosionDecay(drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction
                                        .builder(UniformLootNumberProvider
                                                .create(1.0F, 2.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }

    private LootTable.Builder chutneyDepositDrops() {
        LootPool.Builder rawOrSilkPool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(AlternativeEntry.builder(
                        ItemEntry.builder(ModBlocks.CHUTNEY_DEPOSIT).conditionally(MatchToolLootCondition.builder(
                                ItemPredicate.Builder.create().enchantment(
                                        new EnchantmentPredicate(Enchantments.SILK_TOUCH,
                                                NumberRange.IntRange.atLeast(1))))),
                        ItemEntry.builder(ModItems.RAW_CHUTNEY).conditionally(InvertedLootCondition.builder(
                                MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(
                                        new EnchantmentPredicate(Enchantments.SILK_TOUCH,
                                                NumberRange.IntRange.atLeast(1))))))
                                .apply(SetCountLootFunction.builder(
                                        UniformLootNumberProvider.create(1.0F, 2.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));

        LootPool.Builder expiredBonusPool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(ModItems.EXPIRED_CHUTNEY)
                        .conditionally(RandomChanceLootCondition.builder(0.35F))
                        .conditionally(InvertedLootCondition.builder(
                                MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(
                                        new EnchantmentPredicate(Enchantments.SILK_TOUCH,
                                                NumberRange.IntRange.atLeast(1)))))));

        return LootTable.builder().pool(rawOrSilkPool).pool(expiredBonusPool);
    }
}