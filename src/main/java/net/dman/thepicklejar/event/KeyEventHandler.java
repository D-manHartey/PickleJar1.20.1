package net.dman.thepicklejar.event;

import net.dman.thepicklejar.ModKeybindings;
import net.dman.thepicklejar.item.custom.EternalPickles;
import net.dman.thepicklejar.network.ActivateAbilityPacket;
import net.dman.thepicklejar.screen.EternalPickleBowlSelectionScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * Handles keybinding events for eternal pickle abilities
 * Listens for the ACTIVATE_ABILITY_KEY and sends packet to server
 */
public class KeyEventHandler {

    public static void registerKeyEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Handle ability activation key (V)
            if (ModKeybindings.isAbilityKeyPressed()) {
                handleAbilityActivation(client);
            }

            // Handle bowl GUI key (B)
            if (ModKeybindings.isBowlGuiKeyPressed()) {
                handleBowlGuiOpen(client);
            }
        });
    }


    private static void handleAbilityActivation(MinecraftClient client) {
        PlayerEntity player = client.player;
        if (player == null) return;

        // Get the item in the player's hand
        ItemStack heldItem = player.getMainHandStack();

        // Check if it's an eternal pickle or eternal pickle bowl
        if (heldItem.getItem() instanceof EternalPickles.PowerPickle ||
                heldItem.getItem() instanceof EternalPickles.MindPickle ||
                heldItem.getItem() instanceof EternalPickles.RealityPickle ||
                heldItem.getItem() instanceof EternalPickles.SoulPickle ||
                heldItem.getItem() instanceof EternalPickles.TimePickle ||
                heldItem.getItem() instanceof EternalPickles.SpacePickle) {

            // Send packet to server to activate ability
            ActivateAbilityPacket packet = new ActivateAbilityPacket(heldItem);
            packet.send();
        }
    }


    private static void handleBowlGuiOpen(MinecraftClient client) {
        PlayerEntity player = client.player;
        if (player == null) return;

        // Check if player has Eternal Pickle Bowl in inventory
        boolean hasBowl = false;
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem().getClass().getSimpleName().equals("EternalPickleBowlItem")) {
                hasBowl = true;
                break;
            }
        }

        // Also check offhand
        if (!hasBowl && player.getOffHandStack().getItem().getClass().getSimpleName().equals("EternalPickleBowlItem")) {
            hasBowl = true;
        }

        if (hasBowl) {
            // Open the bowl selection screen
            client.setScreen(new EternalPickleBowlSelectionScreen());
        }
    }
}