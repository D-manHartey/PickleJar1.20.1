package net.dman.thepicklejar.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CirclingBirdiesEffect extends StatusEffect {
    public CirclingBirdiesEffect() {
        super(StatusEffectCategory.HARMFUL, 0xffe542);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    }
}
