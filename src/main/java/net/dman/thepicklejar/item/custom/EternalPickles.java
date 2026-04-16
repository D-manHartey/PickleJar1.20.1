package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.util.LifeStealManager;
import net.dman.thepicklejar.util.PhasingManager;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

/**
 * EternalPickles - Main class for all eternal pickle abilities
 * Handles ability triggering, cooldown management, and special items like the bowl
 */
public class EternalPickles {
    // Cooldown duration: 60 seconds = 1200 ticks
    public static final int ABILITY_COOLDOWN = 1200;

    /**
     * Trigger ability for an item
     * Called when player presses V key with an item in hand
     */
    public static void triggerAbilityForItem(ItemStack itemStack, PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
        if (player.getWorld().isClient) return;

        String itemName = itemStack.getItem().getClass().getSimpleName();

        // Special handling for Eternal Pickle Bowl
        if (itemStack.getItem() == ModItems.ETERNAL_PICKLE_BOWL) {
            // Bowl has no active ability, just show message
            player.sendMessage(net.minecraft.text.Text.literal("§6Eternal Pickle Bowl - No active ability"), false);
            return;
        }

        // Check if ability is on cooldown
        if (isOnCooldown(serverPlayer, itemName)) {
            int remainingSeconds = getRemainingCooldown(serverPlayer, itemName) / 20;
            player.sendMessage(
                    net.minecraft.text.Text.literal("§cAbility on cooldown! " + remainingSeconds + "s remaining"),
                    false
            );
            return;
        }

        // Trigger the ability based on item type
        if (itemStack.getItem() instanceof EternalPickleItem) {
            EternalPickleItem pickleItem = (EternalPickleItem) itemStack.getItem();

            // Get ability name from the pickle
            String abilityName = getAbilityNameForItem(itemStack);

            // Execute the ability
            executeAbility(serverPlayer, abilityName);

            // Set cooldown
            setCooldown(serverPlayer, itemName);

            // Send confirmation message
            player.sendMessage(
                    net.minecraft.text.Text.literal("§a" + abilityName + " Activated!"),
                    false
            );
        }
    }

    /**
     * Execute ability based on ability name
     */
    private static void executeAbility(ServerPlayerEntity player, String abilityName) {
        switch (abilityName.toLowerCase()) {
            case "power_pickle":
                triggerPowerAbility(player);
                break;
            case "mind_pickle":
                triggerMindAbility(player);
                break;
            case "reality_pickle":
                triggerRealityAbility(player);
                break;
            case "soul_pickle":
                triggerSoulAbility(player);
                break;
            case "time_pickle":
                triggerTimeAbility(player);
                break;
            case "space_pickle":
                triggerSpaceAbility(player);
                break;
        }
    }

    /**
     * Get ability name for an item
     */
    private static String getAbilityNameForItem(ItemStack itemStack) {
        String className = itemStack.getItem().getClass().getSimpleName();

        if (className.contains("PowerPickle")) return "Power Pickle";
        if (className.contains("MindPickle")) return "Mind Pickle";
        if (className.contains("RealityPickle")) return "Reality Pickle";
        if (className.contains("SoulPickle")) return "Soul Pickle";
        if (className.contains("TimePickle")) return "Time Pickle";
        if (className.contains("SpacePickle")) return "Space Pickle";
        if (itemStack.getItem() == ModItems.ETERNAL_PICKLE_BOWL) return "Eternal Pickle Bowl";

        return "Unknown";
    }

    // ==================== ABILITY IMPLEMENTATIONS ====================

    private static void triggerPowerAbility(ServerPlayerEntity player) {
        // ABILITY: Strength III for 10 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 2, false, false, true));
    }

    private static void triggerMindAbility(ServerPlayerEntity player) {
        // ABILITY: Haste III for 5 minutes
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 6000, 2, false, false, true));
    }

    private static void triggerRealityAbility(ServerPlayerEntity player) {
        // ABILITY: Phasing for 3 minutes (Origins-style)
        PhasingManager.startPhasing(player);
    }

    private static void triggerSoulAbility(ServerPlayerEntity player) {
        // ABILITY: Life steal for 2 minutes
        LifeStealManager.enableLifeSteal(player);
    }

    private static void triggerTimeAbility(ServerPlayerEntity player) {
        // ABILITY: Speed III for 30 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 2, false, false, true));
    }

    private static void triggerSpaceAbility(ServerPlayerEntity player) {
        // ABILITY: Teleport to looked-at block
        BlockPos targetBlock = getTargetBlock(player);
        if (targetBlock != null) {
            player.teleport(
                    (net.minecraft.server.world.ServerWorld) player.getWorld(),
                    targetBlock.getX() + 0.5,
                    targetBlock.getY() + 1,
                    targetBlock.getZ() + 0.5,
                    0,
                    0
            );
        } else {
            player.sendMessage(net.minecraft.text.Text.literal("§cNo block in sight!"), false);
        }
    }

    /**
     * Get the block the player is looking at
     */
    private static BlockPos getTargetBlock(PlayerEntity player) {
        HitResult raycast = player.raycast(100, 0, false);

        // Check if raycast hit a block
        if (raycast.getType() == HitResult.Type.BLOCK) {
            // Cast to BlockHitResult to get block position
            BlockHitResult blockHit = (BlockHitResult) raycast;
            return blockHit.getBlockPos();
        }

        return null;
    }

    // ==================== COOLDOWN MANAGEMENT ====================

    /**
     * Check if an ability is on cooldown
     */
    public static boolean isOnCooldown(ServerPlayerEntity player, String abilityName) {
        String key = "pickle_cooldown_" + abilityName;
        var tags = player.getCommandTags();

        for (String tag : tags) {
            if (tag.startsWith(key)) {
                try {
                    long timestamp = Long.parseLong(tag.substring(key.length() + 1));
                    return System.currentTimeMillis() < timestamp;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Get remaining cooldown in ticks
     */
    public static int getRemainingCooldown(ServerPlayerEntity player, String abilityName) {
        String key = "pickle_cooldown_" + abilityName;
        var tags = player.getCommandTags();

        for (String tag : tags) {
            if (tag.startsWith(key)) {
                try {
                    long timestamp = Long.parseLong(tag.substring(key.length() + 1));
                    long remainingMs = timestamp - System.currentTimeMillis();
                    return (int) (remainingMs / 50); // Convert ms to ticks (1 tick = 50ms)
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * Set cooldown for an ability
     */
    public static void setCooldown(ServerPlayerEntity player, String abilityName) {
        String key = "pickle_cooldown_" + abilityName;

        // Remove old cooldown tag if it exists
        var tagsToRemove = new java.util.HashSet<String>();
        for (String tag : player.getCommandTags()) {
            if (tag.startsWith(key)) {
                tagsToRemove.add(tag);
            }
        }
        for (String tag : tagsToRemove) {
            player.getCommandTags().remove(tag);
        }

        // Add new cooldown tag with timestamp
        long expirationTime = System.currentTimeMillis() + (ABILITY_COOLDOWN * 50); // Convert ticks to ms
        String cooldownTag = key + "_" + expirationTime;
        player.getCommandTags().add(cooldownTag);
    }

    /**
     * Tick cooldowns (decrement all active cooldowns)
     * Call this from ServerTickEvent
     */
    public static void tickCooldowns(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        var tagsToRemove = new java.util.HashSet<String>();
        long currentTime = System.currentTimeMillis();

        for (String tag : serverPlayer.getCommandTags()) {
            if (tag.startsWith("pickle_cooldown_")) {
                try {
                    long timestamp = Long.parseLong(tag.substring(tag.lastIndexOf("_") + 1));
                    if (currentTime >= timestamp) {
                        tagsToRemove.add(tag);
                    }
                } catch (NumberFormatException e) {
                    // Invalid tag, remove it
                    tagsToRemove.add(tag);
                }
            }
        }

        for (String tag : tagsToRemove) {
            serverPlayer.getCommandTags().remove(tag);
        }
    }
}