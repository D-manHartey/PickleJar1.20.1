package net.dman.thepicklejar.screen;

import net.dman.thepicklejar.ThePickleJar;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<GiardinieraAltarScreenHandler> GIARDINIERA_ALTAR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ThePickleJar.MOD_ID, "giardiniera_altaring"),
                    new ExtendedScreenHandlerType<>(GiardinieraAltarScreenHandler::new));

    public static void registerScreenHandlers() {
        ThePickleJar.LOGGER.info("Registering Screen Handlers for " + ThePickleJar.MOD_ID);
    }
}
