package net.dman.thepicklejar.util;

import net.dman.thepicklejar.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;

public final class BffsTargeting {
    private BffsTargeting() {
    }

    public static boolean isProtectedPlayer(Entity entity) {
        return entity instanceof PlayerEntity player
                && player.hasStatusEffect(ModEffects.BFFS);
    }

    public static boolean blocksTarget(MobEntity mob, Entity target) {
        return mob instanceof Monster && isProtectedPlayer(target);
    }

    public static void clearProtectedTargets(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            for (MobEntity mob : world.getEntitiesByType(
                    TypeFilter.instanceOf(MobEntity.class),
                    candidate -> candidate instanceof Monster
            )) {
                clearProtectedTargets(mob);
            }
        }
    }

    private static void clearProtectedTargets(MobEntity mob) {
        LivingEntity normalTarget = mob.getTarget();
        if (blocksTarget(mob, normalTarget)) {
            mob.setTarget(null);
            mob.setAttacker(null);
        }

        if (mob instanceof HoglinEntity hoglin) {
            hoglin.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                    .filter(BffsTargeting::isProtectedPlayer)
                    .ifPresent(target -> {
                       hoglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
                       hoglin.getBrain().forget(MemoryModuleType.WALK_TARGET);
                       hoglin.setAttacker(null);
                    });
        }

        if (mob instanceof WardenEntity warden) {
            warden.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                    .filter(BffsTargeting::isProtectedPlayer)
                    .ifPresent(target -> {
                        warden.removeSuspect(target);
                        warden.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
                        warden.getBrain().forget(MemoryModuleType.ROAR_TARGET);
                        warden.setAttacker(null);
                    });
        }
    }
}
