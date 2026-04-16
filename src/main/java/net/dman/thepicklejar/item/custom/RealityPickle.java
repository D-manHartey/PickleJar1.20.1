package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

/**
 * Reality Pickle - Grants phasing ability for 3 minutes (ability)
 * Consequence: Nausea effect when eaten
 */
public class RealityPickle extends EternalPickleItem{
    public RealityPickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Nausea effect for 30 seconds
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS,
                2600, 2, false, false, true));
        }

        /**
         * Spawn mobs around the player
         * Called when ability is activated (V key)
         */
        public static void spawnRealityMobs(ServerPlayerEntity player) {
            Vec3d playerPos = player.getPos();

            // Spawn 20 random hostile mobs in a circle around the player
            int mobCount = 20;
            double radius = 6.0; // Distance from player

            for (int i = 0; i < mobCount; i++) {
                double angle = (Math.PI * 2 / mobCount) * i;
                double x = playerPos.x + Math.cos(angle) * radius;
                double z = playerPos.z + Math.sin(angle) * radius;
                double y = playerPos.y;

                // Randomly select mob type
                int mobType = player.getRandom().nextInt(3);

                switch (mobType) {
                    case 0: // Piglin
                        PiglinEntity piglin = new PiglinEntity(EntityType.PIGLIN, player.getWorld());
                        piglin.setPosition(x, y, z);
                        player.getWorld().spawnEntity(piglin);
                        break;

                    case 1: // Vindicator
                        VindicatorEntity vindicator = new VindicatorEntity(EntityType.VINDICATOR, player.getWorld());
                        vindicator.setPosition(x, y, z);
                        player.getWorld().spawnEntity(vindicator);
                        break;

                    case 2: // Wither Skeleton
                        WitherSkeletonEntity witherSkeleton = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, player.getWorld());
                        witherSkeleton.setPosition(x, y, z);
                        player.getWorld().spawnEntity(witherSkeleton);
                        break;
                }
            }

            player.sendMessage(
                    net.minecraft.text.Text.literal("§5Reality Pickle - Mobs spawned!"),
                    false
            );
    }
}
