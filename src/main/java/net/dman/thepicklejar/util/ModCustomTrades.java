package net.dman.thepicklejar.util;

import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.villager.ModVillagers;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModCustomTrades {
    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1,
                factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.TEA_LEAF_SEEDS, 3),
                    4, 5, 0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 5),
                            new ItemStack(ModItems.PEANUT_SEEDS, 3),
                            5, 6, 0.05f));
                });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.PEANUT_BUTTER, 1),
                            new ItemStack(Items.EMERALD, 2),
                            10, 6, 0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GREEN_TEA_LEAVES, 5),
                            new ItemStack(Items.EMERALD, 2),
                            15, 4, 0.05f));
                });
        TradeOfferHelper.registerWanderingTraderOffers(1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 9),
                            new ItemStack(ModBlocks.GREEN_CHRYSANTHEMUM, 3),
                            4, 5, 0.05f));

                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.PICKLE, 3),
                            new ItemStack(Items.EMERALD, 6),
                            8, 5, 0.05f));

                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.PICKLOLIUM, 1),
                            new ItemStack(Items.EMERALD, 3),
                            9, 5, 0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.MOLTEN_CHUTNEY, 1),
                            new ItemStack(Items.EMERALD, 4),
                            9, 5, 0.05f));

                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RADIOACTIVE_PICKLOLIUM, 1),
                            new ItemStack(Items.EMERALD, 5),
                            9, 5, 0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.TOON_STEEL, 1),
                            new ItemStack(Items.EMERALD, 6),
                            9, 5, 0.05f));

                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.ZANY_WORSHIPPER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 20),
                            new ItemStack(ModItems.GOLDEN_PICKLE, 1),
                            9, 5, 0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModBlocks.GREEN_CHRYSANTHEMUM, 1),
                            15, 3, 0.03f));

                });
    }
}
