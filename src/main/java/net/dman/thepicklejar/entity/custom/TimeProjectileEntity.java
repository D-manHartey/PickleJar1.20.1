package net.dman.thepicklejar.entity.custom;

import net.dman.thepicklejar.entity.ModEntities;
import net.dman.thepicklejar.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class TimeProjectileEntity extends ThrownItemEntity {
    public TimeProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public TimeProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.TIME_PROJECTILE, owner, world);
        // Laser will be fast af
        this.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0f, 5.0f, 0.0f);
        // turn off gravity to make it fly straight
        this.setNoGravity(true);
    }

    public TimeProjectileEntity(World world, double x, double y, double z) {
        super(ModEntities.TIME_PROJECTILE, x, y, z, world);
        this.setNoGravity(true);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.TIME_PICKLE;
    }

    @Override
    public void tick() {
        super.tick();

        // Green particles to make it look like a laser
        if (this.getWorld().isClient()) {
            for (int i = 0; i < 3; i++) {
                double offsetX = this.random.nextDouble() - 0.5 * 0.2;
                double offsetY = this.random.nextDouble() - 0.5 * 0.2;
                double offsetZ = this.random.nextDouble() - 0.5 * 0.2;

                this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER,
                        this.getX() + offsetX,
                        this.getY() + offsetY,
                        this.getZ() + offsetZ,
                        0.0D, 0.0D, 0.0D);
            }
        }

        // Erase after 2 seconds so it doesn't go weee forever
        if (this.age > 40) {
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        // Prevent from hitting Owner
        if (entity == this.getOwner()) {
            return;
        }

        // Apply Slowness to living entities
        if (entity instanceof LivingEntity livingEntity) {
            // Time Stoper for 5 seconds
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 10, false, true, true));

            // Dealing a smudge of damage
            entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 1.0F);
        }

        // Remove Projectile on hit
        if (!this.getWorld().isClient()) {
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        // Remove Projectile when hitting a block
        if (!this.getWorld().isClient()) {
            this.discard();
        }
    }
}
