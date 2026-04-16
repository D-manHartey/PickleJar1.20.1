package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Mind Pickle - Grants Haste III for 5 minutes (ability)
 * Consequence: Mining Fatigue effect when eaten
 */
public class MindPickle extends EternalPickleItem{
    public MindPickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Mining Fatigue effect for 30 seconds
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 600, 0, false, false, true)
            );
        }
    }
}
