package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EternalPickles {

    public static class PowerPickle extends EternalPickleItem {
        public PowerPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 2));
            // Strength III for 10 seconds when used
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 30 * 20, 0));
            // Wither for 30s if eaten
        }
    }

    public static class MindPickle extends EternalPickleItem {
        public MindPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 6000, 2));
            // Haste III for 5 minutes
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 60 * 40, 6));
            // Nausea VI for 2 minutes
        }
    }

    public static class RealityPickle extends EternalPickleItem {
        public RealityPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            // Logic to enable phasing for 90 seconds handled in a mixin/tick event
            // custom tag is set
            user.getCommandTags().add("reality_phasing");
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 65 * 40, 0));
            // Darkness for 2 minutes and 10 seconds
        }
    }

    public static class SoulPickle extends EternalPickleItem {
        public SoulPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            // Life Steal handed in attack event
            user.getCommandTags().add("soul_lifesteal");
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 90 * 20, 7));
            // Levitation VII for 90 seconds
        }
    }

    public static class TimePickle extends EternalPickleItem {
        public TimePickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            // Shoots time projectile
            // TimeProjectileEntity projectile = new TimeProjectileEntity(world, user);
            // projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 3.0F, 1.0F);
            // world.spawnEntity(projectile);
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30 * 20, 10));
            // Slowness X for 30 seconds
        }
    }

    public static class SpacePickle extends EternalPickleItem {
        public SpacePickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            HitResult hitResult = user.raycast(100.0D, 0.0F, false);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
                user.requestTeleport(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            }
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            user.requestTeleport(user.getX() + 100000, user.getY() + 200, user.getZ() + 100000);
            // Sends player to Far-land
        }
    }
}
