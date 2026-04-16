package net.dman.thepicklejar.event;

import net.dman.thepicklejar.ModKeybindings;
import net.dman.thepicklejar.item.custom.EternalPickleItem;
import net.dman.thepicklejar.item.custom.EternalPickles;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/*
 * Handles keybinding events for eternal pickle abilities
 * Listens for the ACTIVATE_ABILITY_KEY and triggers abilities
 */
public class KeyEventHandler {

    private static boolean wasKeyPressed = false;

    /*
     * Register the key event handler
     * Call this from ThePickleJarClient.onInitializeClient()
     */
    public static void registerKeyEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            handleAbilityKeyPress();
        });
    }

    /*
     * Handle ability key presses
     * Called every client tick
     */
    private static void handleAbilityKeyPress() {
        MinecraftClient client = MinecraftClient.getInstance();

        // Only process if client is running and player exists
        if (client.player == null || client.world == null) {
            wasKeyPressed = false;
            return;
        }

        PlayerEntity player = client.player;
        boolean isKeyPressed = ModKeybindings.ACTIVATE_ABILITY_KEY.isPressed();

        // Only trigger on key press (not held)
        if (isKeyPressed && !wasKeyPressed) {
            triggerAbility(player);
        }

        wasKeyPressed = isKeyPressed;
    }

    /*
     * Trigger the ability of the held eternal pickle
     */
    private static void triggerAbility(PlayerEntity player) {
        // Check main hand
        ItemStack mainHand = player.getMainHandStack();
        if (mainHand.getItem() instanceof EternalPickleItem) {
            triggerPickleAbility(player, mainHand);
            return;
        }

        // Check off hand
        ItemStack offHand = player.getOffHandStack();
        if (offHand.getItem() instanceof EternalPickleItem) {
            triggerPickleAbility(player, offHand);
            return;
        }

        // No eternal pickle in either hand
    }

    /*
     * Trigger the ability for a specific pickle
     */
    private static void triggerPickleAbility(PlayerEntity player, ItemStack stack) {
        // Only execute on server side
        if (player.getWorld().isClient) {
            return;
        }

        // Get the pickle item
        if (!(stack.getItem() instanceof EternalPickleItem pickle)) {
            return;
        }

        // Trigger the ability through the EternalPickles class
        // This will call the appropriate useAbility() method
        EternalPickles.triggerAbilityForItem(player.getWorld(), player, stack, pickle);
    }
}
