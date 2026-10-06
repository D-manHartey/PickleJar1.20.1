package net.dman.thepicklejar.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

public final class PancakedEffect extends StatusEffect {
    private static final float PANCAKED_MODEL_HEIGHT = 0.12F;
    private static final float PANCAKED_MODEL_WIDTH = 1.35F;
    private static final float PANCAKED_EYE_HEIGHT = 0.12F;

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
            setScale(entity, ScaleTypes.MODEL_HEIGHT, PANCAKED_MODEL_HEIGHT);
            setScale(entity, ScaleTypes.MODEL_WIDTH, PANCAKED_MODEL_WIDTH);
            setScale(entity, ScaleTypes.EYE_HEIGHT, PANCAKED_EYE_HEIGHT);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient) {
            setScale(entity, ScaleTypes.MODEL_HEIGHT, 1.0F);
            setScale(entity, ScaleTypes.MODEL_WIDTH, 1.0F);
            setScale(entity, ScaleTypes.EYE_HEIGHT, 1.0F);
        }
        super.onRemoved(entity, attributes, amplifier);
    }

    private static void setScale(LivingEntity entity, ScaleType type, float scale) {
        ScaleData scaleData = type.getScaleData(entity);
        scaleData.setScale(scale);
        scaleData.setPersistence(false);
    }
}
