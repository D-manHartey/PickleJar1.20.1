package net.dman.thepicklejar.util;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerAbilityManager {
    // Map to store the selected ability index for each player UUID
    private static final Map<UUID, Integer> selectedAbilities = new HashMap<>();

    // Local storage for the client-side selection
    private static int clientSelectedIndex = -1;

    // Ability index mapping:
    // 0 = Power
    // 1 = Mind
    // 2 = Reality
    // 3 = Soul
    // 4 = Time
    // 5 = Space

    /*
     * Get the selected ability index for a specific player (Server-side)
     */
    public static int getSelectedAbility(PlayerEntity player) {
        return selectedAbilities.getOrDefault(player.getUuid(), -1);
    }

    /*
     * Set the selected ability index for a specific player (Server-side)
     */
    public static void setSelectedAbility(PlayerEntity player, int abilityIndex) {
        selectedAbilities.put(player.getUuid(), abilityIndex);
    }

    /*
     * Get the currently selected ability index (Client-side GUI use)
     */
    public static int getSelectedAbilityIndex() {
        return clientSelectedIndex;
    }

    /*
     * Set the currently selected ability index (Client-side GUI use)
     */
    public static void setSelectedAbilityIndex(int index) {
        clientSelectedIndex = index;
    }
}
