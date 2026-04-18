package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.event.EventListeners;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.util.LifeStealManager;
import net.dman.thepicklejar.util.MobDespawnTracker;
import net.dman.thepicklejar.util.PlayerAbilityManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.*;

/**
 * EternalPickles - Main class for all eternal pickle abilities
 * Handles ability triggering, cooldown management, and special items like the bowl
 * FIXED: Properly handles bowl ability activation and spawns 20 mobs for Reality Pickle
 */
public class EternalPickles {
    // Cooldown duration: 60 seconds = 1200 ticks
    public static final int ABILITY_COOLDOWN = 1200;

    // Static HashMap to track cooldowns: playerUUID -> (abilityName -> expirationTime)
    private static final Map<UUID, Map<String, Long>> COOLDOWNS = new HashMap<>();

    /**
     * Trigger ability for an item
     * Called when player presses V key with an item in hand
     */
    public static void triggerAbilityForItem(ItemStack itemStack, PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
        if (player.getWorld().isClient) return;

        // Check if it's the Eternal Pickle Bowl
        if (itemStack.getItem() == ModItems.ETERNAL_PICKLE_BOWL) {
            // Get the selected ability index from the bowl
            int selectedAbilityIndex = PlayerAbilityManager.getSelectedAbility(serverPlayer);

            if (selectedAbilityIndex < 0) {
                player.sendMessage(net.minecraft.text.Text.literal("§cNo pickle Selected! Press B and pick yo poison."), false);
                return;
            }

            // Get ability name from index
            String abilityName = getAbilityNameFromIndex(selectedAbilityIndex);

            // Check if ability is on cooldown
            if (isOnCooldown(serverPlayer, abilityName)) {
                int remainingSeconds = getRemainingCooldown(serverPlayer, abilityName) / 20;
                player.sendMessage(
                        net.minecraft.text.Text.literal("§cPickle Recharging! " + remainingSeconds + "s remaining"),
                        false
                );
                return;
            }

            boolean abilitySucceeded = executeAbility(serverPlayer, selectedAbilityIndex);

            // Set cooldown
            if (abilitySucceeded) {
                setCooldown(serverPlayer, abilityName);
            }

            // Send confirmation message
            player.sendMessage(
                    net.minecraft.text.Text.literal("§a" + abilityName + " Mobilized!"),
                    false
            );
            return;
        }

        // For regular pickle items
        String itemName = itemStack.getItem().getClass().getSimpleName();

        // Check if held item is an eternal pickle
        if (itemStack.getItem() instanceof EternalPickleItem) {
            // Get ability name from the pickle
            String abilityName = getAbilityNameForItem(itemStack);

            // Check if ability is on cooldown
            if (isOnCooldown(serverPlayer, itemName)) {
                int remainingSeconds = getRemainingCooldown(serverPlayer, itemName) / 20;
                player.sendMessage(
                        Text.literal("§cPickle Recharging! " + remainingSeconds + "s remaining"),
                        false
                );
                return;
            }

            // Execute the ability
            boolean abilitySucceeded = executeAbilityByName(serverPlayer, abilityName);

            // Set cooldown - ONLY for non-Space Pickle or if Space Pickle teleport succeeded
            // For Space Pickle, cooldown is handled inside triggerSpaceAbility()
            if (abilitySucceeded) {
                setCooldown(serverPlayer, itemName);
            }

            // Send confirmation message
            player.sendMessage(
                    net.minecraft.text.Text.literal("§a" + abilityName + " Mobilized!"),
                    false
            );
        }
    }

    /**
     * Get ability name from index (0-5)
     */
    private static String getAbilityNameFromIndex(int index) {
        switch (index) {
            case 0: return "Power Pickle";
            case 1: return "Mind Pickle";
            case 2: return "Reality Pickle";
            case 3: return "Soul Pickle";
            case 4: return "Time Pickle";
            case 5: return "Space Pickle";
            default: return "Unknown";
        }
    }

    /**
     * Execute ability by index
     */
    private static boolean executeAbility(ServerPlayerEntity player, int abilityIndex) {
        switch (abilityIndex) {
            case 0:
                triggerPowerAbility(player);
                return true;
            case 1:
                triggerMindAbility(player);
                return true;
            case 2:
                triggerRealityAbility(player);
                return true;
            case 3:
                triggerSoulAbility(player);
                return true;
            case 4:
                triggerTimeAbility(player);
                return true;
            case 5:
                return triggerSpaceAbility(player);
            default:
                return true;
        }
    }

    /**
     * Execute ability by name
     */
    private static boolean executeAbilityByName(ServerPlayerEntity player, String abilityName) {
        switch (abilityName.toLowerCase()) {
            case "power pickle":
                triggerPowerAbility(player);
                return true;
            case "mind pickle":
                triggerMindAbility(player);
                return true;
            case "reality pickle":
                triggerRealityAbility(player);
                return true;
            case "soul pickle":
                triggerSoulAbility(player);
                return true;
            case "time pickle":
                triggerTimeAbility(player);
                return true;
            case "space pickle":
                return triggerSpaceAbility(player);
            default:
                return true;
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

        return "Unknown";
    }

    // ==================== ABILITY IMPLEMENTATIONS ====================

    private static void triggerPowerAbility(ServerPlayerEntity player) {
        // ABILITY: Strength III & Resistance III
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 2, false, false, true));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 500, 2, false, false, true));
    }

    private static void triggerMindAbility(ServerPlayerEntity player) {
        // ABILITY: Haste III & Night vision
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 6000, 2, false, false, true));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 6000, 0, false, false, true));
    }

    private static void triggerRealityAbility(ServerPlayerEntity player) {
        // ABILITY: Spawn 20 hostile mobs around the player & Invisibility
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 4800, 0, false, false, true));
        spawnRealityMobs(player);
    }

    private static void triggerSoulAbility(ServerPlayerEntity player) {
        // ABILITY: Life steal for 2 minutes
        LifeStealManager.enableLifeSteal(player);
    }

    private static void triggerTimeAbility(ServerPlayerEntity player) {
        // ABILITY: Speed IV for 30 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 800, 3, false, false, true));
        TimePickle.applyRadiusSlowness(player);
    }

    private static boolean triggerSpaceAbility(ServerPlayerEntity player) {
        // ABILITY: Teleport to where you're looking (100 blocks away if in air)
        // FIXED: Only apply cooldown if teleport is successful
        return EventListeners.executeSpaceTeleport(player.getWorld(), player);
    }

    /**
     * Spawn 20 mobs around the player in a circle pattern
     * Mobs despawn after a short time
     */
    private static void spawnRealityMobs(ServerPlayerEntity player) {
        net.minecraft.util.math.Vec3d playerPos = player.getPos();

        int mobCount = 20;
        double radius = 6.0; // Distance from player

        // Despawn timer
        List<MobEntity> spawnedMobs = new ArrayList<>();

        for (int i = 0; i < mobCount; i++) {
            double angle = (Math.PI * 2 / mobCount) * i;
            double x = playerPos.x + Math.cos(angle) * radius;
            double z = playerPos.z + Math.sin(angle) * radius;
            double y = playerPos.y;

            // Randomly select mob type
            int mobType = player.getRandom().nextInt(3);

            try {
                switch (mobType) {
                    case 0: // Illusioner
                        net.minecraft.entity.mob.IllusionerEntity illusioner = new net.minecraft.entity.mob.IllusionerEntity(
                                EntityType.ILLUSIONER,
                                player.getWorld()
                        );
                        illusioner.setPosition(x, y, z);
                        player.getWorld().spawnEntity(illusioner);
                        spawnedMobs.add(illusioner);
                        break;

                    case 1: // Vindicator
                        net.minecraft.entity.mob.VindicatorEntity vindicator = new net.minecraft.entity.mob.VindicatorEntity(
                                EntityType.VINDICATOR,
                                player.getWorld()
                        );
                        vindicator.setPosition(x, y, z);
                        player.getWorld().spawnEntity(vindicator);
                        spawnedMobs.add(vindicator);
                        break;

                    case 2: // Evoker
                        net.minecraft.entity.mob.EvokerEntity evoker = new net.minecraft.entity.mob.EvokerEntity(
                                EntityType.EVOKER,
                                player.getWorld()
                        );
                        evoker.setPosition(x, y, z);
                        player.getWorld().spawnEntity(evoker);
                        spawnedMobs.add(evoker);
                        break;
                }
            } catch (Exception e) {
                // Silently fail for individual mobs
            }
        }

        MobDespawnTracker.trackMobsForDespawn(spawnedMobs);

        // Send message to player
        player.sendMessage(
                net.minecraft.text.Text.literal("§5Reality Pickle - Illusions Materialized!"),
                false
        );
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

    // ==================== COOLDOWN MANAGEMENT (HASHMAP-BASED) ====================

    /**
     * Get or create the cooldown map for a player
     */
    private static Map<String, Long> getPlayerCooldowns(ServerPlayerEntity player) {
        return COOLDOWNS.computeIfAbsent(player.getUuid(), k -> new HashMap<>());
    }

    /**
     * Check if an ability is on cooldown
     */
    public static boolean isOnCooldown(ServerPlayerEntity player, String abilityName) {
        try {
            Map<String, Long> playerCooldowns = getPlayerCooldowns(player);

            if (playerCooldowns.containsKey(abilityName)) {
                long expirationTime = playerCooldowns.get(abilityName);
                return System.currentTimeMillis() < expirationTime;
            }
        } catch (Exception e) {
            // Silently fail - cooldown system is not critical
        }
        return false;
    }

    /**
     * Get remaining cooldown in ticks
     */
    public static int getRemainingCooldown(ServerPlayerEntity player, String abilityName) {
        try {
            Map<String, Long> playerCooldowns = getPlayerCooldowns(player);

            if (playerCooldowns.containsKey(abilityName)) {
                long expirationTime = playerCooldowns.get(abilityName);
                long remainingMs = expirationTime - System.currentTimeMillis();
                if (remainingMs > 0) {
                    return (int) (remainingMs / 50); // Convert ms to ticks (1 tick = 50ms)
                }
            }
        } catch (Exception e) {
            // Silently fail
        }
        return 0;
    }

    /**
     * Set cooldown for an ability
     */
    public static void setCooldown(ServerPlayerEntity player, String abilityName) {
        try {
            Map<String, Long> playerCooldowns = getPlayerCooldowns(player);

            // Set the cooldown expiration time
            long expirationTime = System.currentTimeMillis() + (ABILITY_COOLDOWN * 50); // Convert ticks to ms
            playerCooldowns.put(abilityName, expirationTime);
        } catch (Exception e) {
            // Silently fail - cooldown system is not critical
        }
    }

    /**
     * Tick cooldowns (clean up expired cooldowns)
     * Call this from ServerTickEvent
     */
    public static void tickCooldowns(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        try {
            UUID playerUuid = serverPlayer.getUuid();

            // If player has no cooldowns, skip
            if (!COOLDOWNS.containsKey(playerUuid)) {
                return;
            }

            Map<String, Long> playerCooldowns = COOLDOWNS.get(playerUuid);
            long currentTime = System.currentTimeMillis();

            // Remove expired cooldowns
            playerCooldowns.entrySet().removeIf(entry -> currentTime >= entry.getValue());

            // If no cooldowns left, remove the player entry to save memory
            if (playerCooldowns.isEmpty()) {
                COOLDOWNS.remove(playerUuid);
            }
        } catch (Exception e) {
            // Silently fail
        }
    }

    /**
     * Clear all cooldowns for a player (called when player leaves)
     */
    public static void clearPlayerCooldowns(PlayerEntity player) {
        COOLDOWNS.remove(player.getUuid());
    }
}