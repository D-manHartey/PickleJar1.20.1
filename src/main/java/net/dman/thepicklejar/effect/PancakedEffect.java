package net.dman.thepicklejar.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public final class PancakedEffect extends StatusEffect {
    private static final float PANCAKED_MODEL_HEIGHT = 0.12F;

    public PancakedEffect() {
        super(StatusEffectCategory.HARMFUL, 0xf7d788);
        addAttributeModifier(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                "b09ee4fb-65f4-480d-bf6c-9a9f8aa7cd72", -0.75D,
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);

        if (!entity.getWorld().isClient) {
            ScaleData modelHeight = ScaleTypes.MODEL_HEIGHT.getScaleData(entity);
            modelHeight.setScale(PANCAKED_MODEL_HEIGHT);
            modelHeight.setPersistence(false);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient) {
            ScaleData modelHeight = ScaleTypes.MODEL_HEIGHT.getScaleData(entity);
            modelHeight.setScale(1.0F);
            modelHeight.setPersistence(false);
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
