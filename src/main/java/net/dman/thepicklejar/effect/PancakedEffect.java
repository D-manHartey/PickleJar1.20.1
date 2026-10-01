package net.dman.thepicklejar.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public final class PancakedEffect extends StatusEffect {
    public PancakedEffect() {
        super(StatusEffectCategory.HARMFUL, 0xf7d788);
        addAttributeModifier(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                "b09ee4fb-65f4-480d-bf6c-9a9f8aa7cd72", -0.75D,
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    }
}
