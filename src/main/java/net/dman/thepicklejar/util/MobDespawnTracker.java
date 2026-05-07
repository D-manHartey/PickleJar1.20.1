package net.dman.thepicklejar.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;

import java.util.*;

/**
 * Tracks spawned mobs and removes them after a set duration
 * Used for Reality Pickle ability to despawn mobs after 2 minutes
 */
public class MobDespawnTracker {

    private static final Map<UUID, Long> trackedMobs = new HashMap<>();
    private static final Map<UUID, UUID> mobOwners = new HashMap<>();

    private static final String REALITY_MOB_TAG = "thepicklejar_reality_mob";
    private static final String OWNER_TAG_PREFIX = "thepicklejar_owner_";

    public static final long DESPAWN_DURATION_MS = 240000;

    public static void trackMobForDespawn(MobEntity mob) {
        long expirationTime = System.currentTimeMillis() + DESPAWN_DURATION_MS;
        trackedMobs.put(mob.getUuid(), expirationTime);
    }

    public static void trackMobForDespawn(MobEntity mob, UUID ownerUuid) {
        long expirationTime = System.currentTimeMillis() + DESPAWN_DURATION_MS;
        trackedMobs.put(mob.getUuid(), expirationTime);
        mobOwners.put(mob.getUuid(), ownerUuid);
        tagMobWithOwner(mob, ownerUuid);
    }

    public static void trackMobsForDespawn(List<MobEntity> mobs) {
        long expirationTime = System.currentTimeMillis() + DESPAWN_DURATION_MS;
        for (MobEntity mob : mobs) {
            trackedMobs.put(mob.getUuid(), expirationTime);
        }
    }

    public static void trackMobsForDespawn(List<MobEntity> mobs, UUID ownerUuid) {
        long expirationTime = System.currentTimeMillis() + DESPAWN_DURATION_MS;
        for (MobEntity mob : mobs) {
            trackedMobs.put(mob.getUuid(), expirationTime);
            mobOwners.put(mob.getUuid(), ownerUuid);
            tagMobWithOwner(mob, ownerUuid);
        }
    }

    public static void tickDespawnTimers(net.minecraft.server.MinecraftServer server) {
        long currentTime = System.currentTimeMillis();
        List<UUID> mobsToRemove = new ArrayList<>();

        for (net.minecraft.server.world.ServerWorld world : server.getWorlds()) {
            for (Entity entity : world.iterateEntities()) {
                if (entity instanceof MobEntity) {
                    MobEntity mob = (MobEntity) entity;
                    UUID ownerUuid = resolveOwnerUuid(mob);

                    if (ownerUuid != null) {
                        mobOwners.put(mob.getUuid(), ownerUuid);

                        Entity ownerEntity = world.getEntity(ownerUuid);
                        if (ownerEntity instanceof LivingEntity) {
                            LivingEntity livingOwner = (LivingEntity) ownerEntity;

                            if (mob.getTarget() == livingOwner) {
                                mob.setTarget(null);
                            }
                            if (mob.getAttacker() == livingOwner) {
                                mob.setAttacker(null);
                            }
                        }
                    }
                }
            }
        }

        for (Map.Entry<UUID, Long> entry : trackedMobs.entrySet()) {
            UUID mobUuid = entry.getKey();
            long expirationTime = entry.getValue();

            if (currentTime >= expirationTime) {
                mobsToRemove.add(mobUuid);

                for (net.minecraft.server.world.ServerWorld world : server.getWorlds()) {
                    Entity entity = world.getEntity(mobUuid);
                    if (entity instanceof MobEntity) {
                        entity.discard();
                    }
                }
            }
        }

        for (UUID mobUuid : mobsToRemove) {
            trackedMobs.remove(mobUuid);
            mobOwners.remove(mobUuid);
        }
    }

    private static void tagMobWithOwner(MobEntity mob, UUID ownerUuid) {
        mob.addCommandTag(REALITY_MOB_TAG);
        mob.addCommandTag(OWNER_TAG_PREFIX + ownerUuid.toString());
    }

    private static UUID getOwnerUuidFromTags(MobEntity mob) {
        for (String tag : mob.getCommandTags()) {
            if (tag.startsWith(OWNER_TAG_PREFIX)) {
                try {
                    return UUID.fromString(tag.substring(OWNER_TAG_PREFIX.length()));
                } catch (IllegalArgumentException ignored) {
                    return null;
                }
            }
        }
        return null;
    }

    private static UUID resolveOwnerUuid(MobEntity mob) {
        UUID ownerUuid = mobOwners.get(mob.getUuid());
        if (ownerUuid != null) {
            return ownerUuid;
        }

        ownerUuid = getOwnerUuidFromTags(mob);
        if (ownerUuid != null) {
            return ownerUuid;
        }

        if (mob instanceof VexEntity) {
            VexEntity vex = (VexEntity) mob;
            Entity ownerEntity = vex.getOwner();
            if (ownerEntity instanceof MobEntity) {
                MobEntity ownerMob = (MobEntity) ownerEntity;
                ownerUuid = resolveOwnerUuid(ownerMob);
                if (ownerUuid != null) {
                    mobOwners.put(mob.getUuid(), ownerUuid);
                    return ownerUuid;
                }
            }
        }

        return null;
    }

    public static boolean isRealityMobOwner(MobEntity mob, UUID playerUuid) {
        UUID ownerUuid = resolveOwnerUuid(mob);
        if (ownerUuid != null) {
            mobOwners.put(mob.getUuid(), ownerUuid);
        }
        return playerUuid.equals(ownerUuid);
    }

    public static void clearAllTrackedMobs() {
        trackedMobs.clear();
        mobOwners.clear();
    }

    public static int getTrackedMobCount() {
        return trackedMobs.size();
    }
}