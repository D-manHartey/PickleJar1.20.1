package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

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
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0, false, false, true)
            );
        }
    }
}
