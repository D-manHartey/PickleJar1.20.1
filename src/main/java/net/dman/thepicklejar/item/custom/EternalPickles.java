package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.component.ModComponents;
import net.dman.thepicklejar.component.PicklePowerComponent;
import net.dman.thepicklejar.effect.ModEffects;
import net.dman.thepicklejar.event.EventListeners;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.util.MobDespawnTracker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

/**
 * EternalPickles - Main class for all eternal pickle abilities
 * Handles ability triggering, cooldown management, and special items like the bowl
 * FIXED: Properly handles bowl ability activation and spawns 20 mobs for Reality Pickle
 */
public final class EternalPickles {
    public static final int ABILITY_COOLDOWN = 20 * 60;

    private static final double POWER_FORWARD_RANGE = 18.0D;
    private static final double POWER_FORWARD_RADIUS = 2.5D;
    private static final float POWER_FORWARD_DAMAGE = 10.0F;
    private static final float POWER_REAR_DAMAGE = 3.0F;

    private static final double SOUL_RADIUS = 100.0D;
    private static final int SOUL_VEIL_DURATION = 20 * 20;

    private static final int REALITY_DURATION = 20 * 240;

    private EternalPickles() {
    }

    public static void triggerAbilityForItem(ItemStack itemStack, PlayerEntity player) {

        if (!(player instanceof ServerPlayerEntity serverPlayer) || player.getWorld().isClient) {
            return;
        }

        if (itemStack.isOf(ModItems.ETERNAL_PICKLE_BOWL)) {
            triggerBowlAbility(serverPlayer);
            return;
        }
        int abilityIndex = getAbilityIndexForItem(itemStack.getItem());
        if (abilityIndex >= 0) {
            triggerAbility(serverPlayer, abilityIndex);
        }
    }

    private static void triggerBowlAbility(ServerPlayerEntity player) {
        int abilityIndex = ModComponents.PICKLE_POWER.get(player).getSelectedAbility();
        if (abilityIndex < 0 || abilityIndex > 5) {
            player.sendMessage(Text.literal("§cNo pickle Selected! " +
                    "Press B and pick ya poison."), true);
            return;
        }
        triggerAbility(player, abilityIndex);
    }

    private static void triggerAbility(ServerPlayerEntity player, int abilityIndex) {
        PicklePowerComponent powers = ModComponents.PICKLE_POWER.get(player);

            if (powers.isOnCooldown()) {
                long remainingTicks = powers.getRemainingCooldownTicks();
                long remainingSeconds = Math.max(1L, (remainingTicks + 19L) / 20L);
                player.sendMessage(Text.literal("§cPickle Recharging! "
                                + remainingSeconds + "s remaining"), true);
                return;
            }
            if (!executeAbility(player, abilityIndex)) {
                return;
            }
            powers.startCooldown(ABILITY_COOLDOWN);
            player.sendMessage(Text.literal("§a" + abilityName(abilityIndex) +
                            " mobilized!"), true);
        }

        private static int getAbilityIndexForItem(Item item) {
        if (item == ModItems.POWER_PICKLE) return 0;
        if (item == ModItems.MIND_PICKLE) return 1;
        if (item == ModItems.REALITY_PICKLE) return 2;
        if (item == ModItems.SOUL_PICKLE) return 3;
        if (item == ModItems.TIME_PICKLE) return 4;
        if (item == ModItems.SPACE_PICKLE) return 5;
       return -1;
    }

    private static String abilityName(int abilityIndex) {
        return switch (abilityIndex) {
          case 0 -> "Power Pickle";
          case 1 -> "Mind Pickle";
          case 2 -> "Reality Pickle";
          case 3 -> "Soul Pickle";
          case 4 -> "Time Pickle";
          case 5 -> "Space Pickle";
            default -> "Unknown ability";
        };
    }

    //Execute ability by index
    private static boolean executeAbility(ServerPlayerEntity player, int abilityIndex) {
        return switch (abilityIndex) {
            case 0 -> { triggerPowerAbility(player); yield true; }
            case 1 -> { triggerMindAbility(player); yield true; }
            case 2 -> { triggerRealityAbility(player); yield true; }
            case 3 -> { triggerSoulAbility(player); yield true; }
            case 4 -> { triggerTimeAbility(player); yield true; }
            case 5 -> triggerSpaceAbility(player);
            default -> false;
        };
    }

    // ==================== ABILITY IMPLEMENTATIONS ====================

    private static void triggerPowerAbility(ServerPlayerEntity player) {
        // ABILITY: Directional Warden blast
        ServerWorld world = player.getServerWorld();
        Vec3d origin = player.getEyePos();
        Vec3d direction = player.getRotationVec(1.0F).normalize();

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 2.0F, 1.0F);

        for (double distance = 1.0D; distance <= POWER_FORWARD_DAMAGE; distance += 1.25D) {
            Vec3d point = origin.add(direction.multiply(distance));
            world.spawnParticles(ParticleTypes.SONIC_BOOM,
                    point.x, point.y, point.z, 1,
                    0.0D, 0.0D, 0.0D, 0.0D);
        }

        Box forwardSearch = new Box(
                origin,
                origin.add(direction.multiply(POWER_FORWARD_RANGE))).expand(POWER_FORWARD_RADIUS);

        for (LivingEntity target : world.getEntitiesByClass(LivingEntity.class, forwardSearch,
                candidate -> candidate != player && candidate.isAlive())) {
            Vec3d offset = target.getPos().subtract(origin);
            double forwardDistance = offset.dotProduct(direction);
            double sidewaysSquared = offset.lengthSquared() - forwardDistance * forwardDistance;

            if (forwardDistance < 0.0D
                    || forwardDistance > POWER_FORWARD_RANGE
                    || sidewaysSquared > POWER_FORWARD_RADIUS * POWER_FORWARD_RADIUS) {
                continue;
            }

            target.damage(player.getDamageSources().sonicBoom(player), POWER_FORWARD_DAMAGE);
            target.addVelocity(direction.x * 1.75D, 0.28D, direction.z * 1.75D);
            target.velocityModified = true;
        }

        Vec3d rearCenter = player.getPos()
                .subtract(direction.multiply(1.5D)).add(0.0D, 0.8D, 0.0D);
        Box rearSearch = new Box(rearCenter, rearCenter).expand(2.0D);

        for (LivingEntity target : world.getEntitiesByClass(LivingEntity.class, rearSearch,
                candidate -> candidate != player && candidate.isAlive())) {
            target.damage(player.getDamageSources().sonicBoom(player), POWER_REAR_DAMAGE);
            target.addVelocity(-direction.x * 0.40D, 0.08D, -direction.z * 0.40D);
            target.velocityModified = true;
        }
    }

    private static void triggerMindAbility(ServerPlayerEntity player) {
        // ABILITY: Haste III & Night vision
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE,
                6000, 2, false, false, true));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                6000, 0, false, false, true));
    }

    private static void triggerRealityAbility(ServerPlayerEntity player) {
        final int duration = 4_800;
        // ABILITY: Spawn 20 hostile mobs around the player & Invisibility even with armor
        player.addStatusEffect(new StatusEffectInstance(ModEffects.REALITY_CLOAK, REALITY_DURATION,
                0, false, false, false));
        spawnRealityMobs(player);
    }
    private static void triggerSoulAbility(ServerPlayerEntity player) {
        // ABILITY: Hides Player Health bar
        ServerWorld world = player.getServerWorld();
        double maxDistanceSquared = SOUL_RADIUS * SOUL_RADIUS;

        for (ServerPlayerEntity target : world.getPlayers()) {
            if (target != player && target.squaredDistanceTo(player) <= maxDistanceSquared) {
                target.addStatusEffect(new StatusEffectInstance(ModEffects.SOUL_VEIL, SOUL_VEIL_DURATION,
                        0, false, false, false
                ));
            }
        }
    }

    private static void triggerTimeAbility(ServerPlayerEntity player) {
        // ABILITY: Speed IV for 30 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,
                800, 3, false, false, true));
        TimePickle.applyRadiusSlowness(player);
    }

    private static boolean triggerSpaceAbility(ServerPlayerEntity player) {
        // ABILITY: Teleport to where you're looking (100 blocks away if in air)
        return EventListeners.executeSpaceTeleport(player.getWorld(), player);
    }

    private static void spawnRealityMobs(ServerPlayerEntity player) {
        ServerWorld world = player.getServerWorld();
        Vec3d playerPos = player.getPos();
        for (int i = 0; i < 20; i++) {
            double angle = Math.PI * 2.0D * i / 20.0D;
            MobEntity mob = createRealityMob(world, player.getRandom().nextInt(3));

            mob.refreshPositionAndAngles
                    (playerPos.x + Math.cos(angle) * 6.0D,
                    playerPos.y, playerPos.z + Math.sin(angle) * 6.0D,
                            player.getRandom().nextFloat() * 360.0F, 0.0F);

            if (world.spawnEntity(mob)) {
                mob.setPersistent();
                mob.disableExperienceDropping();
                MobDespawnTracker.trackMobForDespawn(mob, player.getUuid());
            }
        }
        player.sendMessage(net.minecraft.text.Text.literal("§5Reality Pickle - Illusions Materialized!"),
                true);
    }

    private static MobEntity createRealityMob(ServerWorld world, int type) {
        return switch (type) {
          case 0 -> new IllusionerEntity(EntityType.ILLUSIONER, world);
          case 1 -> new VindicatorEntity(EntityType.VINDICATOR, world);
            default -> new EvokerEntity(EntityType.EVOKER, world);
        };
    }
}