package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Space Pickle - Grants teleport to looked-at block ability (ability)
 * Consequence: Teleport to random location when eaten
 */
public class SpacePickle extends EternalPickleItem{
    public SpacePickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Teleport to random location within 100000 blocks
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            // Teleport to random location within 100000 blocks
            double randomX = serverPlayer.getX() + (Math.random() * 100 - 100000);
            double randomY = serverPlayer.getY();
            double randomZ = serverPlayer.getZ() + (Math.random() * 100 - 100000);

            serverPlayer.teleport(
                    (net.minecraft.server.world.ServerWorld) serverPlayer.getWorld(),
                    randomX,
                    randomY,
                    randomZ,
                    0,
                    0
            );
        }
    }
}
