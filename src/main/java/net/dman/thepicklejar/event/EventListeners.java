package net.dman.thepicklejar.event;

import net.dman.thepicklejar.entity.custom.TimeProjectileEntity;
import net.dman.thepicklejar.util.LifeStealManager;
import net.dman.thepicklejar.util.PhasingManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EventListeners {

    public static void registerEvents() {
        // Register server tick event for phasing and life steal timers
        ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
            PhasingManager.tickPhasing();
            LifeStealManager.tickLifeSteal();

            // phasing Movement for all players
            for (PlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
                PhasingManager.handlePlayerPhasingTick(player);
            }
        });

        // Register attack event for Life Steal
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity instanceof PlayerEntity player && killedEntity instanceof LivingEntity) {
                LifeStealManager.handleAttack(player, (LivingEntity) killedEntity);
            }
        });

        // Register hit event for life steal (triggers on any hit, not just kills)
        // Note: This might require a mixin into PlayerEntity.attack() for exact implementation
        // but for now we can use a placeholder method that you'll call from the mixin
    }

    /*
     * Executes the Space Pickle teleportation ability
     */
    public static void executeSpaceTeleport(World world, PlayerEntity user) {
        // Raycast to find the block the player is looking at (max 100 blocks)
        HitResult hitResult = user.raycast(100.0d, 0.0f, false);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            BlockPos targetPos = blockHit.getBlockPos().up(); // Teleport on top of the block

            // Create ender-man teleport particles at start location
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);

            // Teleport Players
            user.requestTeleport(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);

            // Playing of ze sound
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        } else {
            // If looking at air, teleport 100 blocks in that direction
            Vec3d lookVec = user.getRotationVec(1.0f).normalize().multiply(100.0d);
            user.requestTeleport(user.getX() + lookVec.x, user.getY() + lookVec.y, user.getZ() + lookVec.z);

            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

    /*
     * Executes the Time Pickle projectile ability
     */
    public static void executeTimeProjectile(World world, PlayerEntity user) {
        if (!world.isClient) {
            TimeProjectileEntity projectile = new TimeProjectileEntity(world, user);
            // Set position at eye level
            projectile.setPos(user.getX(), user.getY() - 0.1, user.getZ());
            // Fest Projectile
            projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 5.0f, 0.0f);

            world.spawnEntity(projectile);

            //play laser sound
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 1.0f, 2.0f);
        }
    }
}
