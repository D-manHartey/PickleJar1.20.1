package net.dman.thepicklejar.event;

import net.dman.thepicklejar.ModKeybindings;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.item.custom.EternalPickleItem;
import net.dman.thepicklejar.network.client.ClientPackets;
import net.dman.thepicklejar.screen.EternalPickleBowlSelectionScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public final class KeyEventHandler {
    private static boolean wasAbilityKeyPressed;
    private static boolean wasBowlKeyPressed;

    private KeyEventHandler() {
    }

    public static void registerKeyEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Check ability activation key (V)
            if (ModKeybindings.ACTIVATE_ABILITY_KEY != null
                    && ModKeybindings.ACTIVATE_ABILITY_KEY.isPressed()) {
                if (!wasAbilityKeyPressed) {
                    handleAbilityKeyPress(client);
                    wasAbilityKeyPressed = true;
                }
            } else {
                wasAbilityKeyPressed = false;
            }

            // Check bowl GUI key (B)
            if (ModKeybindings.OPEN_BOWL_GUI_KEY != null
                    && ModKeybindings.OPEN_BOWL_GUI_KEY.isPressed()) {
                if (!wasBowlKeyPressed) {
                    handleBowlGuiKeyPress(client);
                    wasBowlKeyPressed = true;
                }
            } else {
                wasBowlKeyPressed = false;
            }
        });
    }

    //Handle ability activation key press (V key)
    private static void handleAbilityKeyPress(MinecraftClient client) {
        if (client.player == null) return;
        if (isActivatable(client.player.getMainHandStack())) {
            ClientPackets.sendAbilityActivation(Hand.MAIN_HAND);
        } else if (isActivatable(client.player.getOffHandStack())) {
            ClientPackets.sendAbilityActivation(Hand.OFF_HAND);
        }
    }

    //Handle bowl GUI key press (B key)
    private static void handleBowlGuiKeyPress(MinecraftClient client) {
        if (client.player != null && findBowlHand(client) != null) {
            client.setScreen(new EternalPickleBowlSelectionScreen());
        }
    }

    private static boolean isActivatable(ItemStack stack) {
        return stack.getItem() instanceof EternalPickleItem || stack.isOf(ModItems.ETERNAL_PICKLE_BOWL);
    }

    private static Hand findBowlHand(MinecraftClient client) {
        if (client.player.getMainHandStack().isOf(ModItems.ETERNAL_PICKLE_BOWL))
            return Hand.MAIN_HAND;
        if (client.player.getOffHandStack().isOf(ModItems.ETERNAL_PICKLE_BOWL))
            return Hand.OFF_HAND;
        return null;
    }
}