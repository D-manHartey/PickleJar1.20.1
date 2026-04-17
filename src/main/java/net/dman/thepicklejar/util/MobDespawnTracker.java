package net.dman.thepicklejar.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;

import java.util.*;

/**
 * Tracks spawned mobs and removes them after a set duration
 * Used for Reality Pickle ability to despawn mobs after 2 minutes
 */
public class MobDespawnTracker {

    private static final Map<UUID, Long> trackedMobs = new HashMap<>();

    public static final long DESPAWN_DURATION_MS = 240000;

    public static void trackMobForDespawn(MobEntity mob) {
        long expirationTime = System.currentTimeMillis() + DESPAWN_DURATION_MS;
        trackedMobs.put(mob.getUuid(), expirationTime);
    }

    public static void trackMobsForDespawn(List<MobEntity> mobs) {
        long expirationTime = System.currentTimeMillis() + DESPAWN_DURATION_MS;
        for (MobEntity mob : mobs) {
            trackedMobs.put(mob.getUuid(), expirationTime);
        }
    }

    public static void tickDespawnTimers(net.minecraft.server.MinecraftServer server) {
        long currentTime = System.currentTimeMillis();
        List<UUID> mobsToRemove = new ArrayList<>();

        for (Map.Entry<UUID, Long> entry : trackedMobs.entrySet()) {
            UUID mobUuid = entry.getKey();
            long expirationTime = entry.getValue();

            if (currentTime >= expirationTime) {
                mobsToRemove.add(mobUuid);

                for (net.minecraft.server.world.ServerWorld world : server.getWorlds()) {
                    Entity entity = world.getEntity(mobUuid);
                    if (entity != null && entity instanceof MobEntity) {
                        entity.discard();
                    }
                }
            }
        }

        for (UUID mobUuid : mobsToRemove) {
            trackedMobs.remove(mobUuid);
        }
    }

    public static void clearAllTrackedMobs() {
        trackedMobs.clear();
    }

    public static int getTrackedMobCount() {
        return trackedMobs.size();
    }
}
