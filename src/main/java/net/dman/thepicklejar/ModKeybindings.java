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
    public static KeyBinding ACTIVATE_ABILITY_KEY;
    public static KeyBinding OPEN_BOWL_GUI_KEY;


    public static void registerKeybindings() {
        ACTIVATE_ABILITY_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.thepicklejar.activate_ability",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.thepicklejar.gameplay"
        ));

        OPEN_BOWL_GUI_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.thepicklejar.open_bowl_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,  // B key for Bowl GUI
                "category.thepicklejar.gameplay"
        ));
    }


    public static boolean isAbilityKeyPressed() {
        return ACTIVATE_ABILITY_KEY != null && ACTIVATE_ABILITY_KEY.wasPressed();
    }


    public static boolean isBowlGuiKeyPressed() {
        return OPEN_BOWL_GUI_KEY != null && OPEN_BOWL_GUI_KEY.wasPressed();
    }
}
