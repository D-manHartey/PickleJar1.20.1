package net.dman.thepicklejar.event;

import net.dman.thepicklejar.util.EternalPickleManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

public class ServerTickEvent {
    public static void registerEvents() {
        ServerTickEvents.END_WORLD_TICK.register(ServerTickEvent::onWorldTick);
    }

    private static void onWorldTick(ServerWorld world) {
        // Checks all players for inventory penalties
        world.getPlayers(player -> true).forEach(player ->
                EternalPickleManager.checkInventoryPenalties(player)
        );
    }
}
