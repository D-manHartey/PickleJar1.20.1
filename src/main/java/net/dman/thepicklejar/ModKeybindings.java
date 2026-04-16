package net.dman.thepicklejar;


import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/*
 * Registers custom keybindings for the mod
 * This should be called from ThePickleJarClient during client initialization
 */
public class ModKeybindings {

    // Keybinding for activating eternal pickle abilities
    public static final KeyBinding ACTIVATE_ABILITY_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    "key.thepicklejar.activate_ability", // Translation key
                    InputUtil.Type.KEYSYM, // Type: keyboard
                    GLFW.GLFW_KEY_V, // Default key: V
                    "key.categories.gameplay" // Category: Gameplay
            )
    );

    /*
     * Call this from ThePickleJarClient.onInitializeClient()
     */
    public static void registerKeybindings() {
        // Keybindings are registered above in the static initializer
        // This method is just for organization
    }
}
