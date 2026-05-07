package net.dman.thepicklejar.event;

import net.dman.thepicklejar.item.custom.EternalPickles;
import net.dman.thepicklejar.util.EternalPickleManager;
import net.dman.thepicklejar.util.LifeStealManager;
import net.dman.thepicklejar.util.MobDespawnTracker;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Server tick event handler
 * Handles cooldown ticking and bowl protection
 */
public class ServerTickEvent {
    public static void registerEvents() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {

            LifeStealManager.tickLifeSteal();

            if (world.getServer() != null) {
                MobDespawnTracker.tickDespawnTimers(world.getServer());
            }

            // Check inventory penalties and apply cooldown ticks for each player
            for (PlayerEntity player : world.getPlayers()) {
                // Apply cooldown ticks for abilities
                EternalPickles.tickCooldowns(player);

                // Check and apply inventory penalties
                EternalPickleManager.checkInventoryPenalties(player);
            }
        });
    }
}
