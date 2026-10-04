package net.dman.thepicklejar.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public final class RealityCloakEffect extends StatusEffect {
    public RealityCloakEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xc60404);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        if (!entity.getWorld().isClient) {
            entity.setInvisible(true);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient && !entity.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            entity.setInvisible(false);
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
