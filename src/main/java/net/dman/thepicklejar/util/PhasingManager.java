package net.dman.thepicklejar.util;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * PhasingManager - Implements Origins-style phantom phasing
 * Players can walk through solid blocks EXCEPT obsidian
 * Based on Origins mod phantom origin mechanics
 */
public class PhasingManager {
    // Map to track how long players have been phasing (in ticks)
    private static final Map<UUID, Integer> phasingPlayers = new HashMap<>();

    // 3 minutes phasing = 3600 ticks
    public static final int MAX_PHASING_TICKS = 3600;

    /*
     * Start phasing for a player (Origins phantom-style)
     */
    public static void startPhasing(PlayerEntity player) {
        phasingPlayers.put(player.getUuid(), MAX_PHASING_TICKS);
        player.noClip = true; // Enable noclip
    }

    /*
     * Check if a player is currently phasing
     */
    public static boolean isPhasing(PlayerEntity player) {
        return phasingPlayers.containsKey(player.getUuid()) && phasingPlayers.get(player.getUuid()) > 0;
    }

    /*
     * Tick the phasing duration for all active players
     * Call this from ServerTickEvents.END_SERVER_TICK listener
     */
    public static void tickPhasing() {
        phasingPlayers.entrySet().removeIf(uuidIntegerEntry -> {
            int remainingTicks = uuidIntegerEntry.getValue() - 1;
            if (remainingTicks <= 0) {
                return true;
            }
            uuidIntegerEntry.setValue(remainingTicks);
            return false;
        });
    }

    /*
     * Handle phasing collision detection
     * Called every tick via mixin to check if player is in unphasable blocks
     *
     * Origins-style: Players can phase through all blocks EXCEPT obsidian
     */
    public static void handlePlayerPhasingTick(PlayerEntity player) {
        if (!isPhasing(player)) {
            // Disable noclip if not phasing
            if (player.noClip && !player.isSpectator()) {
                player.noClip = false;
            }
            return;
        }

        // Keep noclip enabled while phasing
        player.noClip = true;

        // Check if player is inside an obsidian block (unphasable)
        BlockPos playerPos = player.getBlockPos();
        if (isInUnphasableBlock(player.getWorld(), playerPos, player)) {
            // Push player out of obsidian
            pushPlayerOutOfBlock(player, playerPos);
        }

        // Optional: Add gravity compensation for easier control
        if (player.getVelocity().y < -0.1) {
            // Reduce falling speed slightly
            player.setVelocity(player.getVelocity().x, player.getVelocity().y * 0.95, player.getVelocity().z);
        }
    }

    /*
     * Check if player is inside an unphasable block (obsidian)
     */
    private static boolean isInUnphasableBlock(World world, BlockPos pos, PlayerEntity player) {
        // Check the block at player's position
        if (world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN) {
            return true;
        }

        // Check blocks around player's bounding box
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {
                    mutable.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                    if (world.getBlockState(mutable).getBlock() == Blocks.OBSIDIAN) {
                        // Check if player's bounding box overlaps with obsidian
                        if (player.getBoundingBox().intersects(world.getBlockState(mutable).getOutlineShape(world, mutable).getBoundingBox().offset(mutable))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
     * Push player out of unphasable blocks
     * Finds nearest safe position and teleports player there
     */
    private static void pushPlayerOutOfBlock(PlayerEntity player, BlockPos blockPos) {
        World world = player.getWorld();

        // Try to find a safe block nearby (not obsidian)
        for (int distance = 1; distance <= 5; distance++) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int x = -distance; x <= distance; x++) {
                for (int y = -distance; y <= distance; y++) {
                    for (int z = -distance; z <= distance; z++) {
                        mutable.set(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);

                        // Check if block is safe (not obsidian and allows movement)
                        if (isSafeBlock(world, mutable)) {
                            // Found a safe block, move player there
                            player.setPosition(mutable.getX() + 0.5, mutable.getY() + 1, mutable.getZ() + 0.5);
                            return;
                        }
                    }
                }
            }
        }
    }

    /*
     * Check if a block is safe to move to
     * Safe = not obsidian and allows movement
     */
    private static boolean isSafeBlock(World world, BlockPos pos) {
        var blockState = world.getBlockState(pos);

        // Don't go into obsidian
        if (blockState.getBlock() == Blocks.OBSIDIAN) {
            return false;
        }

        // Check if block has collision (solid blocks)
        // In 1.20.1, use getCollisionShape to check if block is solid
        var collisionShape = blockState.getCollisionShape(world, pos);

        // If collision shape is empty, block is air-like and safe
        return collisionShape.isEmpty();
    }

    /*
     * Stop phasing for a player
     */
    public static void stopPhasing(PlayerEntity player) {
        phasingPlayers.remove(player.getUuid());
        player.noClip = false;
    }

    /*
     * Get remaining phasing time in ticks
     */
    public static int getRemainingPhasingTime(PlayerEntity player) {
        return phasingPlayers.getOrDefault(player.getUuid(), 0);
    }
}
