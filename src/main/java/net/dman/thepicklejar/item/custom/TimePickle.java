package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Time Pickle - Grants Speed III for 30 seconds (ability)
 * Consequence: Slowness effect when eaten
 */
public class TimePickle extends EternalPickleItem{
    public TimePickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Slowness effect for 30 seconds
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 10, false, false, true)
            );
        }
    }
}
