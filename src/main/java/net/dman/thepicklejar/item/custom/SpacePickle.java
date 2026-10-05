package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.event.EventListeners;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpacePickle extends EternalPickleItem{
    private static final int RANDOM_TELEPORT_RADIUS = 100_000;
    private static final int RANDOM_TELEPORT_ATTEMPTS = 12;

    public SpacePickle(Settings settings) {
        super(settings);
    }

    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return;
        }

        ServerWorld world = serverPlayer.getServerWorld();
        for (int attempt = 0; attempt < RANDOM_TELEPORT_ATTEMPTS; attempt++) {
         int x = serverPlayer.getBlockX() + serverPlayer.getRandom().nextInt
                 (RANDOM_TELEPORT_RADIUS * 2 + 1) - RANDOM_TELEPORT_RADIUS;
         int z = serverPlayer.getBlockZ() + serverPlayer.getRandom().nextInt
                 (RANDOM_TELEPORT_RADIUS * 2 + 1) - RANDOM_TELEPORT_RADIUS;
         int topY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos ground = EventListeners.findSafeGround(world, new BlockPos(x, topY, z));

            if (ground != null) {
                serverPlayer.teleport(
                        world,
                        ground.getX() + 0.5D,
                        ground.getY() + 1.0D,
                        ground.getZ() + 0.5D,
                        serverPlayer.getYaw(),
                        serverPlayer.getPitch()
                );
                return;
            }
        }

        serverPlayer.sendMessage(Text.literal("§cRuh-Roh, no safe places found, oopsies."), true);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.the-pickle-jar.space_pickle.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
