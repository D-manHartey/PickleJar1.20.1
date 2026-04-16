package net.dman.thepicklejar.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PhasingManager {
    // Map to track how long players have been phasing (in ticks)
    private static final Map<UUID, Integer> phasingPlayers = new HashMap<>();


    // 3 minutes phasing = 3600 ticks
    public static final int MAX_PHASING_TICKS = 3600;


    /*
     * Start phasing for a player (spectator-like mode)
     */
    public static void startPhasing(PlayerEntity player) {
        phasingPlayers.put(player.getUuid(), MAX_PHASING_TICKS);
        player.noClip = true; // Enables noclip - pass through blocks like spectator
    }


    /*
     * Check if a player is currently phasing
     */
    public static boolean isPhasing(PlayerEntity player) {
        return phasingPlayers.containsKey(player.getUuid()) && phasingPlayers.get(player.getUuid()) > 0;
    }


    /*
     * Tick the phasing duration for all active players
     * Call this from a ServerTickEvents.END_SERVER_TICK listener
     */
    public static void tickPhasing() {
        phasingPlayers.entrySet().removeIf(uuidIntegerEntry -> {
            int remainingTicks = uuidIntegerEntry.getValue() - 1;
            if (remainingTicks <= 0) {
                // Return true to remove from map
                return true;
            }
            uuidIntegerEntry.setValue(remainingTicks);
            return false;
        });
    }


    /*
     * Called every tick for a phasing player to handle movement
     * Needs to be injected via mixin into PlayerEntity.tick() or handled in a tick event
     *
     * This enables true spectator-like phasing through blocks
     */
    public static void handlePlayerPhasingTick(PlayerEntity player) {
        if (!isPhasing(player)) {
            // Ensure noClip is disabled if they shouldn't be phasing
            if (player.noClip && !player.isSpectator()) {
                player.noClip = false;
            }
            return;
        }

        // Enable noClip so they can move through blocks (like spectator mode)
        player.noClip = true;

        // In spectator mode, players can move freely in all directions
        // The noClip flag handles collision detection
        // Movement is controlled by the player's input (WASD keys)

        // Optional: Add slight gravity compensation if needed
        // This allows players to stay at their current height without constantly jumping
        if (player.getVelocity().y < -0.1) {
            // Reduce falling speed slightly for easier control
            player.setVelocity(player.getVelocity().x, player.getVelocity().y * 0.95, player.getVelocity().z);
        }
    }

    /*
     * Stop phasing for a player
     */
    public static void stopPhasing(PlayerEntity player) {
        phasingPlayers.remove(player.getUuid());
        player.noClip = false;
    }

    /*
     * Get remaining phasing time in ticks
     */
    public static int getRemainingPhasingTime(PlayerEntity player) {
        return phasingPlayers.getOrDefault(player.getUuid(), 0);
    }
}
