package net.dman.thepicklejar.event;

import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.item.custom.EternalPickleItem;
import net.dman.thepicklejar.screen.EternalPickleBowlSelectionScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

/**
 * KeyEventHandler - Handles keybind events for abilities and GUI
 */
public class KeyEventHandler {
    // Keybindings
    public static KeyBinding activateAbilityKey;
    public static KeyBinding openBowlGuiKey;

    /**
     * Register keybindings and event listeners
     */
    public static void registerKeyEvents() {
        // Register keybindings
        activateAbilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.thepicklejar.activate_ability",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.thepicklejar.gameplay"
        ));

        openBowlGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.thepicklejar.open_bowl_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.thepicklejar.gameplay"
        ));

        // Register client tick event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (activateAbilityKey.wasPressed()) {
                handleAbilityKeyPress(client);
            }

            while (openBowlGuiKey.wasPressed()) {
                handleBowlGuiKeyPress(client);
            }
        });
    }

    /**
     * Handle ability activation key press (V key)
     */
    private static void handleAbilityKeyPress(MinecraftClient client) {
        if (client.player == null) return;

        ItemStack heldItem = client.player.getMainHandStack();
        if (heldItem.isEmpty()) return;

        // Check if held item is an eternal pickle or bowl
        if (heldItem.getItem() instanceof EternalPickleItem ||
                heldItem.getItem() == ModItems.ETERNAL_PICKLE_BOWL) {

            // Send packet to server to activate ability
            net.dman.thepicklejar.network.ActivateAbilityPacket packet =
                    new net.dman.thepicklejar.network.ActivateAbilityPacket(heldItem);
            packet.send();
        }
    }

    /**
     * Handle bowl GUI key press (B key)
     */
    private static void handleBowlGuiKeyPress(MinecraftClient client) {
        if (client.player == null) return;

        ItemStack heldItem = client.player.getMainHandStack();

        // Check if held item is the eternal pickle bowl
        if (heldItem.getItem() == ModItems.ETERNAL_PICKLE_BOWL) {
            // Open the bowl selection screen
            client.setScreen(new EternalPickleBowlSelectionScreen());
        }
    }
}