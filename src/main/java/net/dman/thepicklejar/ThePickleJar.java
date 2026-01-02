package net.dman.thepicklejar;

import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.item.ModItemGroups;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.util.ModLootTableModifiers;
import net.dman.thepicklejar.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThePickleJar implements ModInitializer {
	public static final String MOD_ID = "the-pickle-jar";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
	public void onInitialize() {
        ModItemGroups.registerItemGroups();

        ModItems.registerModItems();
        ModBlocks.registerModBlocks();

        FuelRegistry.INSTANCE.add(ModItems.GHERKIN_SOUL, 4000);

        ModLootTableModifiers.modifyLootTables();

        ModWorldGeneration.generateModWorldGen();
	}
}