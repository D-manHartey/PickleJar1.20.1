package net.dman.thepicklejar.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PhasingManager {
    // Map to track how long players have been phasing (in ticks)
    private static final Map<UUID, Integer> phasingPlayers = new HashMap<>();

    // 5 minutes phasing = 6000 ticks
    public static final int MAX_PHASING_TICKS = 6000;

    /*
     * Start phasing for a player
     */
    public static void startPhasing(PlayerEntity player) {
        phasingPlayers.put(player.getUuid(), MAX_PHASING_TICKS);
        player.noClip = true; // Enables noclip
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
     */
    public static void handlePlayerPhasingTick(PlayerEntity player) {
        if (!isPhasing(player)) {
            // Ensure noClip is disabled if they shouldn't be phasing
            if (player.noClip && !player.isSpectator()) {
                player.noClip = false;
            }
            return;
        }

        // Enable noClip so they can move through blocks
        player.noClip = true;

        // Prevent falling through the floor
        // Check if the block directly below the player's feet is solid
        BlockPos posBelow = player.getBlockPos().down();
        boolean isSolidBelow = !player.getWorld().getBlockState(posBelow).isAir();

        // If they are crouching, let them sink down slowly
        if (player.isSneaking()) {
            player.setVelocity(player.getVelocity().x, -0.2, player.getVelocity().z);
        }
        // If they are jumping, let them rise up
        else if (player.getVelocity().y > 0.1) {
            player.setVelocity(player.getVelocity().x, 0.2, player.getVelocity().z);
        }
        // Otherwise, if there is a solid block below them and they are moving down, stop them from falling
        else if (isSolidBelow && player.getVelocity().y < 0) {
            player.setVelocity(player.getVelocity().x, 0, player.getVelocity().z);
            // Snap them to the top of the block below so they don't get stuck halfway
            player.setPos(player.getX(), posBelow.getY() + 1.0, player.getZ());
            player.setOnGround(true);
        }
    }
}
