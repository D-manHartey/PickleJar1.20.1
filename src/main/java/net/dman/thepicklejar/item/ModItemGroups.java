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
                        entries.add(ModItems.RAW_CHUTNEY);
                        entries.add(ModItems.MOLTEN_CHUTNEY);


                        entries.add(ModItems.INKBLOT_MALLET);
                        entries.add(ModItems.RAGGIDY_SCYTHE);

                        entries.add(ModItems.TOON_HELMET);
                        entries.add(ModItems.TOON_CHESTPLATE);
                        entries.add(ModItems.TOON_LEGGINGS);
                        entries.add(ModItems.TOON_BOOTS);

                        entries.add(ModItems.BROKEN_TELEPHONE);

                        entries.add(ModItems.PICKLE);
                        entries.add(ModItems.GOLDEN_PICKLE);
                        entries.add(ModItems.GHERKIN_SOUL);
                        entries.add(ModItems.PICKLE_ALFREDO);
                        entries.add(ModItems.CUP_O_GREEN_TEA);

                        entries.add(ModItems.PEANUT_SEEDS);
                        entries.add(ModItems.PEANUT_BUTTER);
                        entries.add(ModItems.TEA_LEAF_SEEDS);
                        entries.add(ModItems.GREEN_TEA_LEAVES);

                        entries.add(ModBlocks.GREEN_CHRYSANTHEMUM);

                        entries.add(ModBlocks.PICKLOLIUM_DEPOSIT);
                        entries.add(ModBlocks.PICKLOLIUM_BLOCK);
                        entries.add(ModBlocks.CHUTNEY_BLOCK);
                        entries.add(ModBlocks.CHUTNEY_DEPOSIT);
                        entries.add(ModBlocks.PHIL_BLOCK);

                        entries.add(ModBlocks.PHIL_STAIRS);
                        entries.add(ModBlocks.PHIL_SLAB);
                        entries.add(ModBlocks.PHIL_BUTTON);
                        entries.add(ModBlocks.PHIL_PRESSURE_PLATE);
                        entries.add(ModBlocks.PHIL_FENCE);
                        entries.add(ModBlocks.PHIL_FENCE_GATE);
                        entries.add(ModBlocks.PHIL_WALL);
                        entries.add(ModBlocks.PHIL_DOOR);
                        entries.add(ModBlocks.PHIL_TRAPDOOR);

                        entries.add(ModBlocks.GIADINIERA_ALTAR);

                    }).build());


    public static void registerItemGroups() {
        ThePickleJar.LOGGER.info("Registering Item Groups for " + ThePickleJar.MOD_ID);
    }
}
