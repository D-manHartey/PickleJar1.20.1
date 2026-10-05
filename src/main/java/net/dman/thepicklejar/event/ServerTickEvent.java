package net.dman.thepicklejar.event;

import net.dman.thepicklejar.item.custom.EternalPickles;
import net.dman.thepicklejar.util.BffsTargeting;
import net.dman.thepicklejar.util.EternalPickleManager;
import net.dman.thepicklejar.util.MobDespawnTracker;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public final class ServerTickEvent {
    private static int bffsSweepTicks;

    private ServerTickEvent() {
    }

    public static void registerEvents() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            MobDespawnTracker.tickDespawnTimers(server);

            if (++bffsSweepTicks >= 5) {
                bffsSweepTicks = 0;
                BffsTargeting.clearProtectedTargets(server);
            }

            // Check inventory penalties and apply cooldown ticks for each player
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                // Check and apply inventory penalties
                EternalPickleManager.checkInventoryPenalties(player);
            }
        });
    }
}
