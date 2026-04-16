package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Power Pickle - Grants Strength III for 10 seconds (ability)
 * Consequence: Weakness effect when eaten
 */
public class PowerPickle extends EternalPickleItem{
    public PowerPickle(Item.Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Weakness effect for 30 seconds
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.WITHER, 600, 0, false, false, true)
            );
        }
    }
}
