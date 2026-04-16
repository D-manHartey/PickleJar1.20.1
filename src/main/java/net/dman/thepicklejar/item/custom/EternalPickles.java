package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.util.LifeStealManager;
import net.dman.thepicklejar.util.PhasingManager;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

/**
 * Complete EternalPickles implementation with all 6 pickle types
 * Handles abilities (via keybind) and consequences (via eating)
 */
public class EternalPickles {

    // Cooldown tracking (in ticks)
    private static final int ABILITY_COOLDOWN = 1200; // 60 seconds

    /**
     * Trigger ability for a specific pickle item
     * Called from KeyEventHandler when ability key is pressed
     */
    public static void triggerAbilityForItem(World world, PlayerEntity player, ItemStack stack, EternalPickleItem pickle) {
        if (world.isClient) return;

        String pickleType = getPickleType(stack);
        if (pickleType == null) return;

        // Check cooldown
        if (isOnCooldown(player, pickleType)) {
            player.sendMessage(net.minecraft.text.Text.literal("§cAbility on cooldown!"), true);
            return;
        }

        // Trigger ability based on pickle type
        switch (pickleType) {
            case "power_pickle" -> triggerPowerAbility(player);
            case "mind_pickle" -> triggerMindAbility(player);
            case "reality_pickle" -> triggerRealityAbility(player);
            case "soul_pickle" -> triggerSoulAbility(player);
            case "time_pickle" -> triggerTimeAbility(player);
            case "space_pickle" -> triggerSpaceAbility(player);
        }

        // Set cooldown
        setCooldown(player, pickleType);
    }

    /**
     * Apply consequence for a specific pickle item
     * Called from EternalPickleItem.finishUsing() when eaten
     */
    public static void applyConsequenceForItem(PlayerEntity player, ItemStack stack) {
        String pickleType = getPickleType(stack);
        if (pickleType == null) return;

        switch (pickleType) {
            case "power_pickle" -> applyPowerConsequence(player);
            case "mind_pickle" -> applyMindConsequence(player);
            case "reality_pickle" -> applyRealityConsequence(player);
            case "soul_pickle" -> applySoulConsequence(player);
            case "time_pickle" -> applyTimeConsequence(player);
            case "space_pickle" -> applySpaceConsequence(player);
        }
    }

    // ==================== POWER PICKLE ====================

    private static void triggerPowerAbility(PlayerEntity player) {
        // ABILITY: Strength III for 10 seconds
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.STRENGTH,
                200,  // 10 seconds
                2,    // Strength III
                false,
                false
        ));
        player.sendMessage(net.minecraft.text.Text.literal("§6Power Pickle Activated! Strength III for 10s"), true);
    }

    private static void applyPowerConsequence(PlayerEntity player) {
        // CONSEQUENCE: Wither I for 30 seconds (when eaten)
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.WITHER,
                600,  // 30 seconds
                0,    // Wither I
                false,
                false
        ));
        player.sendMessage(net.minecraft.text.Text.literal("§4Power Pickle Consequence! Wither I for 30s"), true);
    }

    // ==================== MIND PICKLE ====================

    private static void triggerMindAbility(PlayerEntity player) {
        // ABILITY: Haste III for 5 minutes
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HASTE,
                6000,  // 5 minutes
                2,     // Haste III
                false,
                false
        ));
        player.sendMessage(net.minecraft.text.Text.literal("§9Mind Pickle Activated! Haste III for 5m"), true);
    }

    private static void applyMindConsequence(PlayerEntity player) {
        // CONSEQUENCE: Nausea VI for 2 minutes (when eaten)
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.NAUSEA,
                2400,  // 2 minutes
                5,     // Nausea VI
                false,
                false
        ));
        player.sendMessage(net.minecraft.text.Text.literal("§5Mind Pickle Consequence! Nausea VI for 2m"), true);
    }

    // ==================== REALITY PICKLE ====================

    private static void triggerRealityAbility(PlayerEntity player) {
        // ABILITY: Enable phasing for 3 minutes
        PhasingManager.startPhasing(player);
        player.sendMessage(net.minecraft.text.Text.literal("§bReality Pickle Activated! Phasing for 3m"), true);
    }

    private static void applyRealityConsequence(PlayerEntity player) {
        // CONSEQUENCE: Darkness for 2m 10s (when eaten)
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.DARKNESS,
                2600,  // 2m 10s
                0,
                false,
                false
        ));
        player.sendMessage(net.minecraft.text.Text.literal("§0Reality Pickle Consequence! Darkness for 2m 10s"), true);
    }

    // ==================== SOUL PICKLE ====================

    private static void triggerSoulAbility(PlayerEntity player) {
        // ABILITY: Enable life steal for 2 minutes
        LifeStealManager.enableLifeSteal(player);
        player.sendMessage(net.minecraft.text.Text.literal("§dSoul Pickle Activated! Life Steal for 2m"), true);
    }

    private static void applySoulConsequence(PlayerEntity player) {
        // CONSEQUENCE: Levitation VII for 90 seconds (when eaten)
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.LEVITATION,
                1800,  // 90 seconds
                6,     // Levitation VII
                false,
                false
        ));
        player.sendMessage(net.minecraft.text.Text.literal("§eSoul Pickle Consequence! Levitation VII for 90s"), true);
    }

    // ==================== TIME PICKLE ====================

    private static void triggerTimeAbility(PlayerEntity player) {
        // ABILITY: Speed III for 30 seconds
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SPEED,
                600,  // 30 seconds
                2,    // Speed III
                false,
                false
        ));
        player.sendMessage(net.minecraft.text.Text.literal("§eTime Pickle Activated! Speed III for 30s"), true);
    }

    private static void applyTimeConsequence(PlayerEntity player) {
        // CONSEQUENCE: Slowness X for 30 seconds (when eaten)
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SLOWNESS,
                600,  // 30 seconds
                9,    // Slowness X
                false,
                false
        ));
        player.sendMessage(net.minecraft.text.Text.literal("§8Time Pickle Consequence! Slowness X for 30s"), true);
    }

    // ==================== SPACE PICKLE ====================

    private static void triggerSpaceAbility(PlayerEntity player) {
        // ABILITY: Teleport to looked-at block
        var raycast = player.raycast(100, 0, false);
        if (raycast.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK) {
            var blockHit = (net.minecraft.util.hit.BlockHitResult) raycast;
            var blockPos = blockHit.getBlockPos();

            // Teleport player above the block (proper 1.20.1 API)
            if (player instanceof ServerPlayerEntity serverPlayer) {
                ServerWorld serverWorld = (ServerWorld) serverPlayer.getWorld();
                serverPlayer.teleport(
                        serverWorld,
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 1.5,
                        blockPos.getZ() + 0.5,
                        0,  // yaw
                        0   // pitch
                );
            }
            player.sendMessage(net.minecraft.text.Text.literal("§3Space Pickle Activated! Teleported to block"), true);
        } else {
            player.sendMessage(net.minecraft.text.Text.literal("§cNo block in sight!"), true);
        }
    }

    private static void applySpaceConsequence(PlayerEntity player) {
        // CONSEQUENCE: Teleport to far lands (random location far away)
        double randomX = player.getX() + (Math.random() - 0.5) * 1000;
        double randomZ = player.getZ() + (Math.random() - 0.5) * 1000;

        // Find safe Y coordinate (fixed API for 1.20.1)
        var world = player.getWorld();
        double randomY = 100;
        for (int y = 256; y > 0; y--) {
            var blockState = world.getBlockState(new net.minecraft.util.math.BlockPos((int)randomX, y, (int)randomZ));
            // Use isAir() instead of getMaterial()
            if (!blockState.isAir()) {
                randomY = y + 1;
                break;
            }
        }

        // Teleport using proper 1.20.1 API
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ServerWorld serverWorld = (ServerWorld) serverPlayer.getWorld();
            serverPlayer.teleport(
                    serverWorld,
                    randomX,
                    randomY,
                    randomZ,
                    0,  // yaw
                    0   // pitch
            );
        }
        player.sendMessage(net.minecraft.text.Text.literal("§3Space Pickle Consequence! Teleported to far lands"), true);
    }

    // ==================== COOLDOWN MANAGEMENT ====================

    /**
     * Check if an ability is on cooldown using command tags
     * Tags format: "pickle_cooldown_ABILITYNAME_TIMESTAMP"
     */
    public static boolean isOnCooldown(PlayerEntity player, String abilityName) {
        if (!(player instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer)) return false;

        String tagPrefix = "pickle_cooldown_" + abilityName + "_";

        for (String tag : serverPlayer.getCommandTags()) {
            if (tag.startsWith(tagPrefix)) {
                try {
                    long cooldownEndTime = Long.parseLong(tag.substring(tagPrefix.length()));
                    long currentTime = System.currentTimeMillis();
                    return currentTime < cooldownEndTime;
                } catch (NumberFormatException e) {
                    // Invalid tag format, skip
                }
            }
        }
        return false;
    }

    /**
     * Set cooldown for an ability using command tags
     * Stores the end time of the cooldown in the tag
     */
    public static void setCooldown(PlayerEntity player, String abilityName) {
        if (!(player instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer)) return;

        String tagPrefix = "pickle_cooldown_" + abilityName + "_";

        // Remove old cooldown tag if it exists
        for (String tag : new java.util.ArrayList<>(serverPlayer.getCommandTags())) {
            if (tag.startsWith(tagPrefix)) {
                serverPlayer.removeScoreboardTag(tag);
            }
        }

        // Add new cooldown tag with end time
        long cooldownEndTime = System.currentTimeMillis() + (ABILITY_COOLDOWN * 50); // Convert ticks to milliseconds
        String cooldownTag = tagPrefix + cooldownEndTime;
        serverPlayer.addCommandTag(cooldownTag);
    }

    /**
     * Tick cooldowns (decrement and remove expired ones)
     * Call this from server tick event
     */
    public static void tickCooldowns(PlayerEntity player) {
        if (!(player instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer)) return;

        long currentTime = System.currentTimeMillis();
        java.util.List<String> tagsToRemove = new java.util.ArrayList<>();

        for (String tag : serverPlayer.getCommandTags()) {
            if (tag.startsWith("pickle_cooldown_")) {
                try {
                    String[] parts = tag.split("_");
                    if (parts.length >= 3) {
                        long cooldownEndTime = Long.parseLong(parts[parts.length - 1]);
                        if (currentTime >= cooldownEndTime) {
                            tagsToRemove.add(tag);
                        }
                    }
                } catch (NumberFormatException e) {
                    // Invalid tag format, skip
                }
            }
        }

        // Remove expired cooldown tags
        for (String tag : tagsToRemove) {
            serverPlayer.removeScoreboardTag(tag);
        }
    }


    // ==================== UTILITY METHODS ====================

    /**
     * Get the pickle type from an ItemStack
     */
    private static String getPickleType(ItemStack stack) {
        String itemId = stack.getItem().toString();

        if (itemId.contains("power_pickle")) return "power_pickle";
        if (itemId.contains("mind_pickle")) return "mind_pickle";
        if (itemId.contains("reality_pickle")) return "reality_pickle";
        if (itemId.contains("soul_pickle")) return "soul_pickle";
        if (itemId.contains("time_pickle")) return "time_pickle";
        if (itemId.contains("space_pickle")) return "space_pickle";

        return null;
    }

    // ==================== PICKLE ITEM CLASSES ====================

    public static class PowerPickle extends EternalPickleItem {
        public PowerPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void applyConsequence(PlayerEntity player) {
            EternalPickles.applyPowerConsequence(player);
        }
    }

    public static class MindPickle extends EternalPickleItem {
        public MindPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void applyConsequence(PlayerEntity player) {
            EternalPickles.applyMindConsequence(player);
        }
    }

    public static class RealityPickle extends EternalPickleItem {
        public RealityPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void applyConsequence(PlayerEntity player) {
            EternalPickles.applyRealityConsequence(player);
        }
    }

    public static class SoulPickle extends EternalPickleItem {
        public SoulPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void applyConsequence(PlayerEntity player) {
            EternalPickles.applySoulConsequence(player);
        }
    }

    public static class TimePickle extends EternalPickleItem {
        public TimePickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void applyConsequence(PlayerEntity player) {
            EternalPickles.applyTimeConsequence(player);
        }
    }

    public static class SpacePickle extends EternalPickleItem {
        public SpacePickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void applyConsequence(PlayerEntity player) {
            EternalPickles.applySpaceConsequence(player);
        }
    }
}
