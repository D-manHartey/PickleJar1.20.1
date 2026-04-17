package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Soul Pickle - Grants life steal ability for 2 minutes (ability)
 * Consequence: Wither effect when eaten
 */
public class SoulPickle extends EternalPickleItem{
    public SoulPickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Levitation effect
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.LEVITATION, 1800, 7, false, false, true)
            );
        }
    }
}
