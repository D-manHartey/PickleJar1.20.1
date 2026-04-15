package net.dman.thepicklejar.item;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // Metal
    public static final Item PICKLOLIUM = registerItem("picklolium", new Item (new FabricItemSettings()));
    public static final Item RAW_PICKLOLIUM = registerItem("rawpick", new Item(new FabricItemSettings()));
    public static final Item TOON_STEEL = registerItem("toon_steel", new ToonSteelItem(new FabricItemSettings()));
    public static final Item RADIOACTIVE_PICKLOLIUM = registerItem("rad_pick", new RadioactivePickItem(new FabricItemSettings()));
    public static final Item RAW_CHUTNEY = registerItem("raw_chutney", new Item(new FabricItemSettings()));
    public static final Item MOLTEN_CHUTNEY = registerItem("molten_chutney", new Item(new FabricItemSettings()));

    // WIP stuff
    public static final Item BROKEN_TELEPHONE = registerItem("broken_telephone", new BrokenTelephoneItem(new FabricItemSettings()));


    // Food
    public static final Item PICKLE = registerItem("pickle", new Item(new FabricItemSettings().food(ModFoodComponents.PICKLE)));
    public static final Item GOLDEN_PICKLE = registerItem("golden_pickle", new Item(new FabricItemSettings().food(ModFoodComponents.GOLDEN_PICKLE)));
    public static final Item PICKLE_ALFREDO = registerItem("pickle_alfredo", new Item(new FabricItemSettings().food(ModFoodComponents.PICKLE_ALFREDO)));
    public static final Item CUP_O_GREEN_TEA = registerItem("cup_green_tea", new Item(new FabricItemSettings().food(ModFoodComponents.CUP_O_GREEN_TEA)));


    // Fuel Sources
    public static final Item GHERKIN_SOUL = registerItem("gherkin_soul", new Item(new FabricItemSettings()));
    public static final Item SALSA_SOUL = registerItem("salsa_soul", new Item(new FabricItemSettings()));

    // Weapons
    public static final Item INKBLOT_MALLET = registerItem("inkblot_mallet",
            new InkblotMalletItem(ModToolMaterial.TOON, 10, -3.2f, new FabricItemSettings()));
    public static final Item RAGGIDY_SCYTHE = registerItem("raggidy_scythe",
            new RaggidyScytheItem(ModToolMaterial.RADIATED, 8, -2.4f, new FabricItemSettings()));

    // Armor
    public static final Item TOON_HELMET = registerItem("toon_helmet",
            new ModArmorItem(ModArmorMaterials.TOONPROT, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item TOON_CHESTPLATE = registerItem("toon_chestplate",
            new ArmorItem(ModArmorMaterials.TOONPROT, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item TOON_LEGGINGS = registerItem("toon_leggings",
            new ArmorItem(ModArmorMaterials.TOONPROT, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item TOON_BOOTS = registerItem("toon_boots",
            new ArmorItem(ModArmorMaterials.TOONPROT, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // Crops
    public static final Item PEANUT_SEEDS = registerItem("peanut_seeds",
            new AliasedBlockItem(ModBlocks.PEANUT_CROP, new FabricItemSettings()));
    public static final Item PEANUT_BUTTER = registerItem("peanut_butter", new Item(new FabricItemSettings().food(ModFoodComponents.PEANUT_BUTTER)));

    public static final Item GREEN_TEA_LEAVES = registerItem("green_tea_leaves", new Item(new FabricItemSettings()));
    public static final Item TEA_LEAF_SEEDS = registerItem("tea_leaf_seeds",
            new AliasedBlockItem(ModBlocks.TEA_LEAVES_CROP, new FabricItemSettings()));

    // Eternal Jarred Energy
    public static final Item JARRED_MIND = registerItem("jarred_mind", new Item(new FabricItemSettings()));
    public static final Item JARRED_SPACE = registerItem("jarred_space", new Item(new FabricItemSettings()));
    public static final Item JARRED_REALITY = registerItem("jarred_reality", new Item(new FabricItemSettings()));
    public static final Item JARRED_POWER = registerItem("jarred_power", new Item(new FabricItemSettings()));
    public static final Item JARRED_TIME = registerItem("jarred_time", new Item(new FabricItemSettings()));
    public static final Item JARRED_SOUL = registerItem("jarred_soul", new Item(new FabricItemSettings()));

    // Eternal Pickles
    public static final Item POWER_PICKLE = registerItem("power_pickle",
            new EternalPickles.PowerPickle(new FabricItemSettings()));
    public static final Item MIND_PICKLE = registerItem("mind_pickle",
            new EternalPickles.MindPickle(new FabricItemSettings()));
    public static final Item REALITY_PICKLE = registerItem("reality_pickle",
            new EternalPickles.RealityPickle(new FabricItemSettings()));
    public static final Item SOUL_PICKLE = registerItem("soul_pickle",
            new EternalPickles.SoulPickle(new FabricItemSettings()));
    public static final Item TIME_PICKLE = registerItem("time_pickle",
            new EternalPickles.TimePickle(new FabricItemSettings()));
    public static final Item SPACE_PICKLE = registerItem("space_pickle",
            new EternalPickles.SpacePickle(new FabricItemSettings()));

    // Eternal Bowl
    public static final Item ETERNAL_BOWL = registerItem("eternal_bowl",
            new EternalBowlItem(new FabricItemSettings()));
    public static final Item ETERNAL_PICKLE_BOWL = registerItem("eternal_pickle_bowl",
            new EternalPickleBowlItem(new FabricItemSettings()));


    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(PICKLOLIUM);
        entries.add(RAW_PICKLOLIUM);
        entries.add(TOON_STEEL);
        entries.add(RADIOACTIVE_PICKLOLIUM);
        entries.add(RAW_CHUTNEY);
        entries.add(MOLTEN_CHUTNEY);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ThePickleJar.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ThePickleJar.LOGGER.info("Registering Mod Items for " + ThePickleJar.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
