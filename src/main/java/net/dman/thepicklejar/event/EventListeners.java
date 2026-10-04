package net.dman.thepicklejar.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class EventListeners {
    private EventListeners() {
    }

    public static void registerEvents() {
    }

    //Executes the Space Pickle teleportation ability
    // find the block the player is looking at (max 100 blocks)
    public static boolean executeSpaceTeleport(World world, PlayerEntity user) {
        HitResult hitResult = user.raycast(100.0d, 0.0f, false);
        BlockPos safeGround = null;

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            safeGround = ((BlockHitResult) hitResult).getBlockPos();
            if (!isSafeGround(world, safeGround)) {
                safeGround = null;
            }
        } else {
            Vec3d direction = user.getRotationVec(1.0f).normalize().multiply(100.0d);
            Vec3d destination = user.getPos().add(direction);
            BlockPos start = new BlockPos(
                    (int) Math.floor(destination.x),
                    (int) Math.floor(destination.y),
                    (int) Math.floor(destination.z)
            );
            safeGround = findSafeGround(world, start);
        }
        // No safe block found - teleport failed
            if (safeGround == null) {
                user.sendMessage(Text.literal("§cCan't teleport to air dingus!"), true);
                return false; // Teleport failed - no cooldown
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
            user.requestTeleport(
                    safeGround.getX() + 0.5D,
                    safeGround.getY() + 1.0D,
                    safeGround.getZ() + 0.5D
            );

            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
            return true;
        }

    public static BlockPos findSafeGround(World world, BlockPos startPos) {
        int startY = Math.min(startPos.getY(), world.getTopY() - 3);
        int minimumY = Math.max(world.getBottomY(), startY - 256);

        for (int y = startY; y >= minimumY; y--) {
            BlockPos ground = new BlockPos(startPos.getX(), y, startPos.getZ());
            if (isSafeGround(world, ground)) {
                return ground;
            }
        }
        return null;
    }

    public static boolean isSafeGround(World world, BlockPos ground) {
    return world.getBlockState(ground).isFullCube(world, ground)
            && world.getBlockState(ground.up()).isAir()
            && world.getBlockState(ground.up(2)).isAir();
    }
}
