package net.dman.thepicklejar.item;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup PICKLE_JAR = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ThePickleJar.MOD_ID, "picklejar"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.picklejar"))
                    .icon(() -> new ItemStack(ModItems.PICKLOLIUM)).entries((displayContext, entries) -> {
                        entries.add(ModItems.PICKLOLIUM);
                        entries.add(ModItems.RAW_PICKLOLIUM);
                        entries.add(ModItems.TOON_STEEL);
                        entries.add(ModItems.RADIOACTIVE_PICKLOLIUM);

                        entries.add(ModItems.INKBLOT_MALLET);
                        entries.add(ModItems.RAGGIDY_SCYTHE);

                        entries.add(ModItems.BROKEN_TELEPHONE);

                        entries.add(ModItems.PICKLE);
                        entries.add(ModItems.GOLDEN_PICKLE);
                        entries.add(ModItems.GHERKIN_SOUL);

                        entries.add(ModBlocks.PICKLOLIUM_DEPOSIT);

                        entries.add(ModBlocks.PICKLOLIUM_BLOCK);


                    }).build());


    public static void registerItemGroups() {
        ThePickleJar.LOGGER.info("Registering Item Groups for " + ThePickleJar.MOD_ID);
    }
}
