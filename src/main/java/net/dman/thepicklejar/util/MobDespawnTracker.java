package net.dman.thepicklejar.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import java.util.*;

/**
 * Tracks spawned mobs and removes them after a set duration
 * Used for Reality Pickle ability to despawn mobs after 2 minutes
 */
public final class MobDespawnTracker {

    public static final long DESPAWN_DURATION_MS = 120_000L;
    private static final String REALITY_MOB_TAG = "thepicklejar_reality_mob";
    private static final String OWNER_TAG_PREFIX = "thepicklejar_owner_";
    private static final Map<UUID, Long> trackedMobs = new HashMap<>();
    private static final Map<UUID, UUID> mobOwners = new HashMap<>();

    private MobDespawnTracker() {
    }

    public static void trackMobForDespawn(MobEntity mob) {
        trackMobForDespawn(mob, null);
    }

    public static void trackMobForDespawn(MobEntity mob, UUID ownerUuid) {
        TRACKED_MOBS.put(mob.getUuid(), System.currentTimeMillis() + DESPAWN_DURATION_MS);
        mob.addCommandTag(REALITY_MOB_TAG);
        mob.disableExperienceDropping();
        if (ownerUuid != null) {
            MOB_OWNERS.put(mob.getUuid(), ownerUuid);
            mob.addCommandTag(OWNER_TAG_PREFIX + ownerUuid);
        }
    }

    public static void trackMobsForDespawn(List<MobEntity> mobs) {
        for (MobEntity mob : mobs) trackMobForDespawn(mob);
    }

    public static void trackMobsForDespawn(List<MobEntity> mobs, UUID ownerUuid) {
        for (MobEntity mob : mobs) trackMobForDespawn(mob, ownerUuid);
    }

    public static void tickDespawnTimers(MinecraftServer server) {
        long now = System.currentTimeMillis();
        List<UUID> expired = new ArrayList<>();
        for (Map.Entry<UUID, Long> entry : TRACKED_MOBS.entrySet()) {
            UUID mobUuid = entry.getKey();
            MobEntity mob = findMob(server, mobUuid);
            if (mob != null) {
                preventOwnerTargeting(server, mob);
                mob.disableExperienceDropping();
            }
            if (now >= entry.getValue()) {
                if (mob != null) mob.discard();
                expired.add(mobUuid);
            }
        }
        for (UUID mobUuid : expired) {
            TRACKED_MOBS.remove(mobUuid);
            MOB_OWNERS.remove(mobUuid);
        }
    }

    public static boolean isRealityMob(MobEntity mob) {
        return mob.getCommandTags().contains(REALITY_MOB_TAG) ||
                resolveOwnerUuid(mob) != null;
    }

    public static boolean isRealityMobOwner(MobEntity mob, UUID playerUuid) {
        return playerUuid.equals(resolveOwnerUuid(mob));
    }

    public static void clearAllTrackedMobs() {
        return TRACKED_MOBS.size();
    }

    private static MobEntity findMob(MinecraftServer server, UUID uuid) {
        for (ServerWorld world : server.getWorlds()) {
            Entity entity = world.getEntity(uuid);
            if (entity instanceof MobEntity mob) return mob;
        }
        return null;
    }

    private static void preventOwnerTargeting(MinecraftServer server, MobEntity mob) {
        UUID ownerUuid = resolveOwnerUuid(mob);
        if (ownerUuid == null) return;
        for (ServerWorld world : server.getWorlds()) {
            Entity owner = world.getEntity(ownerUuid);
            if (owner instanceof LivingEntity livingOwner) {
                if (mob.getTarget() == livingOwner) mob.setTarget(null);
                if (mob.getAttacker() == livingOwner) mob.setAttacker(null);
                return;
            }
        }
    }

    private static UUID resolveOwnerUuid(MobEntity mob) {
        UUID ownerUuid = MOB_OWNERS.get(mob.getUuid());
        if (ownerUuid != null) return ownerUuid;
        for (String tag : mob.getCommandTags()) {
            if (tag.startsWith(OWNER_TAG_PREFIX)) {
                try {
                    ownerUuid = UUID.fromString(tag.substring
                            (OWNER_TAG_PREFIX.length()));
                    MOB_OWNERS.put(mob.getUuid(), ownerUuid);
                    return ownerUuid;
                } catch (IllegalArgumentException ignored) {
                    return null;
                }
            }
        }
        if (mob instanceof VexEntity vex && vex.getOwner() instanceof MobEntity ownerMob) {
            ownerUuid = resolveOwnerUuid(ownerMob);
            if (ownerUuid != null) MOB_OWNERS.put(mob.getUuid(), ownerUuid);

        }
        return ownerUuid;
    }
}