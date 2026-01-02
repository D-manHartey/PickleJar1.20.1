package net.dman.thepicklejar.item;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.item.custom.BrokenTelephoneItem;
import net.dman.thepicklejar.item.custom.RadioactivePickItem;
import net.dman.thepicklejar.item.custom.ToonSteelItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item PICKLOLIUM = registerItem("picklolium", new Item (new FabricItemSettings()));
    public static final Item RAW_PICKLOLIUM = registerItem("rawpick", new Item(new FabricItemSettings()));

    public static final Item TOON_STEEL = registerItem("toon_steel", new ToonSteelItem(new FabricItemSettings()));
    public static final Item RADIOACTIVE_PICKLOLIUM = registerItem("rad_pick", new RadioactivePickItem(new FabricItemSettings()));

    public static final Item BROKEN_TELEPHONE = registerItem("broken_telephone", new BrokenTelephoneItem(new FabricItemSettings()));

    public static final Item PICKLE = registerItem("pickle", new Item(new FabricItemSettings().food(ModFoodComponents.PICKLE)));
    public static final Item GOLDEN_PICKLE = registerItem("golden_pickle", new Item(new FabricItemSettings().food(ModFoodComponents.GOLDEN_PICKLE)));

    public static final Item GHERKIN_SOUL = registerItem("gherkin_soul", new Item(new FabricItemSettings()));

    public static final Item INKBLOT_MALLET = registerItem("inkblot_mallet", new Item(new FabricItemSettings().maxCount(1)));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(PICKLOLIUM);
        entries.add(RAW_PICKLOLIUM);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ThePickleJar.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ThePickleJar.LOGGER.info("Registering Mod Items for " + ThePickleJar.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
