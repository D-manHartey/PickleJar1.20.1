package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.event.EventListeners;
import net.dman.thepicklejar.util.LifeStealManager;
import net.dman.thepicklejar.util.PhasingManager;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EternalPickles {

    // Cooldown tracking for abilities (in ticks)
    private static final int ABILITY_COOLDOWN = 1200; // 60 seconds

    /*
     * Trigger ability for a specific pickle item
     * Called by KeyEventHandler when keybind is pressed
     */
    public static void triggerAbilityForItem(World world, PlayerEntity player, ItemStack stack,
                                             EternalPickleItem pickle) {
        if (world.isClient) return;

        // Determine which pickle type and call its ability
        if (pickle instanceof PowerPickle) {
            ((PowerPickle) pickle).useAbility(world, player, stack);
        } else if (pickle instanceof MindPickle) {
            ((MindPickle) pickle).useAbility(world, player, stack);
        } else if (pickle instanceof RealityPickle) {
            ((RealityPickle) pickle).useAbility(world, player, stack);
        } else if (pickle instanceof SoulPickle) {
            ((SoulPickle) pickle).useAbility(world, player, stack);
        } else if (pickle instanceof TimePickle) {
            ((TimePickle) pickle).useAbility(world, player, stack);
        } else if (pickle instanceof SpacePickle) {
            ((SpacePickle) pickle).useAbility(world, player, stack);
        }
    }

    // ==================== POWER PICKLE ====================
    public static class PowerPickle extends EternalPickleItem {
        public PowerPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            if (isOnCooldown(user, "power_pickle")) return;

            // ABILITY ONLY: Strength III for 10 seconds
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 2));
            setCooldown(user, "power_pickle", ABILITY_COOLDOWN);
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            // CONSEQUENCE ONLY: Wither I for 30 seconds (when eaten)
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 30 * 20, 0));
        }
    }

    // ==================== MIND PICKLE ====================
    public static class MindPickle extends EternalPickleItem {
        public MindPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            if (isOnCooldown(user, "mind_pickle")) return;

            // ABILITY ONLY: Haste III for 5 minutes
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 6000, 2));
            setCooldown(user, "mind_pickle", ABILITY_COOLDOWN);
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            // CONSEQUENCE ONLY: Nausea VI for 2 minutes (when eaten)
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 60 * 40, 6));
        }
    }

    // ==================== REALITY PICKLE ====================
    public static class RealityPickle extends EternalPickleItem {
        public RealityPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            if (isOnCooldown(user, "reality_pickle")) return;

            // ABILITY ONLY: Start phasing for 3 minutes
            PhasingManager.startPhasing(user);
            setCooldown(user, "reality_pickle", ABILITY_COOLDOWN);
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            // CONSEQUENCE ONLY: Darkness for 2m 10s (when eaten)
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 65 * 40, 0));
        }
    }

    // ==================== SOUL PICKLE ====================
    public static class SoulPickle extends EternalPickleItem {
        public SoulPickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            if (isOnCooldown(user, "soul_pickle")) return;

            // ABILITY ONLY: Enable life steal for 2 minutes
            LifeStealManager.enableLifeSteal(user);
            setCooldown(user, "soul_pickle", ABILITY_COOLDOWN);
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            // CONSEQUENCE ONLY: Levitation VII for 90s (when eaten)
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 90 * 20, 7));
        }
    }

    // ==================== TIME PICKLE ====================
    public static class TimePickle extends EternalPickleItem {
        public TimePickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            if (isOnCooldown(user, "time_pickle")) return;

            // ABILITY ONLY: Shoot time projectile
            // This would call your time projectile spawning code
            // For now, apply Slowness to nearby enemies as placeholder
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 2));
            setCooldown(user, "time_pickle", ABILITY_COOLDOWN);
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            // CONSEQUENCE ONLY: Slowness X for 30s (when eaten)
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30 * 20, 10));
        }
    }

    // ==================== SPACE PICKLE ====================
    public static class SpacePickle extends EternalPickleItem {
        public SpacePickle(Settings settings) {
            super(settings);
        }

        @Override
        protected void useAbility(World world, PlayerEntity user, ItemStack stack) {
            if (isOnCooldown(user, "space_pickle")) return;

            // ABILITY ONLY: Teleport to looked-at block (100 blocks away)
            var hitResult = user.raycast(100, 0, false);
            if (hitResult.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK) {
                var blockHit = (net.minecraft.util.hit.BlockHitResult) hitResult;
                var blockPos = blockHit.getBlockPos();
                user.requestTeleport(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);
            }

            setCooldown(user, "space_pickle", ABILITY_COOLDOWN);
        }

        @Override
        protected void applyConsequence(PlayerEntity user) {
            // CONSEQUENCE ONLY: Teleport to far lands (when eaten)
            user.requestTeleport(user.getX() + 100000, user.getY() + 200, user.getZ() + 100000);
        }
    }

    // ==================== COOLDOWN MANAGEMENT ====================

    private static boolean isOnCooldown(PlayerEntity player, String abilityName) {
        String cooldownTag = "cooldown_" + abilityName;
        int storedCooldown = player.getCommandTags().stream()
                .filter(tag -> tag.startsWith(cooldownTag + ":"))
                .map(tag -> {
                    try {
                        return Integer.parseInt(tag.substring(cooldownTag.length() + 1));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .findFirst()
                .orElse(0);
        return storedCooldown > 0;
    }

    private static void setCooldown(PlayerEntity player, String abilityName, int cooldownTicks) {
        String cooldownTag = "cooldown_" + abilityName;
        player.getCommandTags().removeIf(tag -> tag.startsWith(cooldownTag + ":"));
        player.getCommandTags().add(cooldownTag + ":" + cooldownTicks);
    }

    public static void tickCooldowns(PlayerEntity player) {
        var cooldownTags = player.getCommandTags().stream()
                .filter(tag -> tag.startsWith("cooldown_"))
                .toList();

        for (String tag : cooldownTags) {
            try {
                String[] parts = tag.split(":");
                if (parts.length == 2) {
                    int cooldown = Integer.parseInt(parts[1]);
                    if (cooldown > 1) {
                        player.getCommandTags().remove(tag);
                        player.getCommandTags().add(parts[0] + ":" + (cooldown - 1));
                    } else {
                        player.getCommandTags().remove(tag);
                    }
                }
            } catch (NumberFormatException e) {
                // Skip malformed tags
            }
        }
    }
}
