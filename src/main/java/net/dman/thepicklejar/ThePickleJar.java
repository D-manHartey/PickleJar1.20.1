package net.dman.thepicklejar;

import net.dman.thepicklejar.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThePickleJar implements ModInitializer {
	public static final String MOD_ID = "the-pickle-jar";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
	}
}