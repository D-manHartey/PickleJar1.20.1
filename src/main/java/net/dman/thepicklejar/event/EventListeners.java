package net.dman.thepicklejar.event;

import net.dman.thepicklejar.entity.custom.TimeProjectileEntity;
import net.dman.thepicklejar.item.custom.EternalPickles;
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

    //Phasing through blocks ability currently scrapped
    public static void registerEvents() {
        // Register server tick event for phasing and life steal timers
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            //PhasingManager.tickPhasing();
            LifeStealManager.tickLifeSteal();

            // phasing Movement for all players
            //for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
                //PhasingManager.handlePlayerPhasingTick(player);
                //EternalPickles.tickCooldowns(player);
            //}
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
            Vec3d teleportPos = user.getPos().add(lookVec);

            // Find the nearest solid block below the teleport destination
            BlockPos safePos = findSafeBlock(world, new BlockPos(
                    (int) teleportPos.x,
                    (int) teleportPos.y,
                    (int) teleportPos.z
            ));

            if (safePos != null) {
                // Teleport to the safe block position (on top of it)
                user.requestTeleport(safePos.getX() + 0.5, safePos.getY() + 1.0, safePos.getZ() + 0.5);
            } else {
                // Fallback: teleport to the original position if no safe block found
                user.requestTeleport(teleportPos.x, teleportPos.y, teleportPos.z);
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

    /*
     * Find a safe block to teleport to by searching downward from the target position
     * Returns the top of a solid block, or null if no solid block found within 256 blocks
     */
    private static BlockPos findSafeBlock(World world, BlockPos startPos) {
        // Search downward from the start position for up to 256 blocks
        for (int y = startPos.getY(); y >= startPos.getY() - -256; y--) {
            BlockPos checkPos = new BlockPos(startPos.getX(), y, startPos.getZ());

            // Check if the block is solid and the block above is air (safe to stand on)
            if (world.getBlockState(checkPos).isFullCube(world, checkPos) &&
            !world.getBlockState(checkPos.up()).isAir()) {
                return checkPos;
            }
        }

        // No safe block found, return null
        return null;
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
