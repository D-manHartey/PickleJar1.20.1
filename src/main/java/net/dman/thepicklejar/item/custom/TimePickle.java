package net.dman.thepicklejar.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Time Pickle - Grants Speed III for 30 seconds
 * Ability: Slows all entities in a radius
 * Consequence: Slowness effect when eaten
 */
public class TimePickle extends EternalPickleItem {

    // Radius for the slow effect (in blocks)
    private static final double EFFECT_RADIUS = 35.0;

    // Duration of slowness effect
    private static final int SLOWNESS_DURATION = 400;

    // Slowness level
    private static final int SLOWNESS_LEVEL = 3; // Slowness IV

    public TimePickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Slowness effect
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,
                2400, 10, false, false, true));
    }

    /**
     * Apply slowness effect to all entities in a radius
     * Called when ability is activated (V key)
     * Slows all entities within 20 blocks for 10 seconds
     */
    public static void applyRadiusSlowness(ServerPlayerEntity player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        // Create a box around the player for the effect radius
        Box effectBox = new Box(
                x - EFFECT_RADIUS, y - EFFECT_RADIUS, z - EFFECT_RADIUS,
                x + EFFECT_RADIUS, y + EFFECT_RADIUS, z + EFFECT_RADIUS
        );

        // Get all entities in the box
        var entities = player.getWorld().getEntitiesByClass(
                LivingEntity.class,
                effectBox,
                entity -> entity != player // Exclude the player that activates it
        );

        // Apply slowness to all entities
        int affectedCount = 0;
        for (LivingEntity entity : entities) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,
                    SLOWNESS_DURATION, SLOWNESS_LEVEL, false, false, true
            ));
            affectedCount++;
        }

        // Send message to player
        player.sendMessage(
                Text.literal("§9Time Pickle - " + affectedCount + " Dweebs Time Shifted! (35 block radius)"
                ),
                true
        );
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.the-pickle-jar.time_pickle.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
