package net.dman.thepicklejar.event;

import net.dman.thepicklejar.item.custom.EternalPickles;
import net.dman.thepicklejar.util.EternalPickleManager;
import net.dman.thepicklejar.util.MobDespawnTracker;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerTickEvent {
    public static void registerEvents() {
        ServerTickEvents.END_WORLD_TICK.register(server -> {
            MobDespawnTracker.tickDespawnTimers(server);

            // Check inventory penalties and apply cooldown ticks for each player
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                // Apply cooldown ticks for abilities
                EternalPickles.tickCooldowns(player);

                // Check and apply inventory penalties
                EternalPickleManager.checkInventoryPenalties(player);
            }
        });
    }
}
