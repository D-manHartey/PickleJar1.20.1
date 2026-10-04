package net.dman.thepicklejar.util;

import net.dman.thepicklejar.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;

public final class BffsTargeting {
    private BffsTargeting() {
    }

    public static boolean blocksTarget(MobEntity mob, LivingEntity target) {
        return mob instanceof Monster
                && target instanceof PlayerEntity player
                && player.hasStatusEffect(ModEffects.BFFS);
    }

    public static void clearProtectedTargets(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            for (MobEntity mob : world.getEntitiesByType(
                    TypeFilter.instanceOf(MobEntity.class),
                    candidate -> candidate instanceof Monster
                    && candidate.getTarget() != null
                    && blocksTarget(candidate, candidate.getTarget())
            )) {
                mob.setTarget(null);
                mob.setAttacker(null);
            }
        }
    }
}
