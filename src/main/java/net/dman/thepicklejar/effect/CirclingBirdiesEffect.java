package net.dman.thepicklejar.effect;

import net.dman.thepicklejar.particle.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

public class CirclingBirdiesEffect extends StatusEffect {
    public CirclingBirdiesEffect() {
        super(StatusEffectCategory.HARMFUL, 0xffe542);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 4 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity.getWorld() instanceof ServerWorld world)) {
            return;
        }

        double baseY = entity.getY() + entity.getHeight() + 0.45D;
        double time = world.getTime() * 0.28D;

        for (int i = 0; i < 2; i++) {
            double phase = time + Math.PI * i;
            double x = entity.getX() + Math.cos(phase) * 0.85D;
            double z = entity.getZ() + Math.sin(phase) * 0.85D;

            world.spawnParticles(ModParticles.BIRD, x, baseY, z,
                    1, 0.0D, 0.015D, 0.0D, 0.0D);
            world.spawnParticles(ParticleTypes.END_ROD, x, baseY + 0.15D, z,
                    1, 0.0D, 0.01D, 0.0D, 0.0D);
        }
    }
}
