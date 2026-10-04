package net.dman.thepicklejar.effect;

import net.dman.thepicklejar.util.BffsTargeting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public final class BffsEffect extends StatusEffect {
    public BffsEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFF8559);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        if (!entity.getWorld().isClient && entity instanceof ServerPlayerEntity player) {
            BffsTargeting.clearProtectedTargets(player.getServer());
        }
    }
}
