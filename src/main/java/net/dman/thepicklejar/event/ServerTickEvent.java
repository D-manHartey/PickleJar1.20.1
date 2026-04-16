package net.dman.thepicklejar.event;

import net.dman.thepicklejar.item.custom.EternalPickleBowlItem;
import net.dman.thepicklejar.item.custom.EternalPickles;
import net.dman.thepicklejar.util.EternalPickleManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

/**
 * Server tick event handler
 * Handles cooldown ticking and bowl protection
 */
public class ServerTickEvent {

    public static void registerEvents() {
        // Register server tick event for phasing and life steal timers
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            // Process all players in the world
            for (PlayerEntity player : world.getPlayers()) {
                // Tick cooldowns for eternal pickles
                EternalPickles.tickCooldowns(player);

                // Apply bowl protection effect
                EternalPickleBowlItem.tickBowlProtection(player);
            }
        });
    }

    private static void onWorldTick(ServerWorld world) {
        // Checks all players for inventory penalties
        world.getPlayers(player -> true).forEach(EternalPickleManager::checkInventoryPenalties);
    }
}
