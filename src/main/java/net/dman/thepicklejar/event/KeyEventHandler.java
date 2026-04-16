package net.dman.thepicklejar.event;

import net.dman.thepicklejar.ModKeybindings;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.item.custom.EternalPickleItem;
import net.dman.thepicklejar.network.ActivateAbilityPacket;
import net.dman.thepicklejar.screen.EternalPickleBowlSelectionScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

/**
 * KeyEventHandler - Handles keybind events for abilities and GUI
 * FIXED: Uses ModKeybindings instead of registering duplicate keybindings
 * This prevents the "Attempted to register two key bindings with equal ID" error
 */
public class KeyEventHandler {

    private static boolean wasAbilityKeyPressed = false;
    private static boolean wasBowlKeyPressed = false;

    /**
     * Register keybinding event listeners
     * NOTE: Keybindings are already registered in ModKeybindings.java
     * This method only registers the event handlers, NOT the keybindings themselves
     */
    public static void registerKeyEvents() {
        // Register client tick event to check for key presses
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Check ability activation key (V)
            if (ModKeybindings.ACTIVATE_ABILITY_KEY != null && ModKeybindings.ACTIVATE_ABILITY_KEY.isPressed()) {
                if (!wasAbilityKeyPressed) {
                    handleAbilityKeyPress(client);
                    wasAbilityKeyPressed = true;
                }
            } else {
                wasAbilityKeyPressed = false;
            }

            // Check bowl GUI key (B)
            if (ModKeybindings.OPEN_BOWL_GUI_KEY != null && ModKeybindings.OPEN_BOWL_GUI_KEY.isPressed()) {
                if (!wasBowlKeyPressed) {
                    handleBowlGuiKeyPress(client);
                    wasBowlKeyPressed = true;
                }
            } else {
                wasBowlKeyPressed = false;
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
            ActivateAbilityPacket packet = new ActivateAbilityPacket(heldItem);
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