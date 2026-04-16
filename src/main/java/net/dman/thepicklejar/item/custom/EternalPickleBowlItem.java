package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Eternal Pickle Bowl Item
 *
 * The bowl itself provides protection from side effects ONLY when held in inventory
 * It does NOT affect individual eternal pickles in the inventory
 *
 * When eaten:
 * - Applies Resistance III for 30 seconds (protection effect)
 * - Does NOT negate side effects from other pickles
 */
public class EternalPickleBowlItem extends Item {

    public EternalPickleBowlItem(Settings settings) {
        super(settings);
    }

    /**
     * Check if player has the bowl in their inventory
     * If yes, apply protection effect
     */
    public static void tickBowlProtection(PlayerEntity player) {
        // Check if player has the bowl in inventory
        boolean hasBowl = false;
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() instanceof EternalPickleBowlItem) {
                hasBowl = true;
                break;
            }
        }

        // Also check offhand
        if (!hasBowl && player.getOffHandStack().getItem() instanceof EternalPickleBowlItem) {
            hasBowl = true;
        }

        if (hasBowl) {
            // Apply Resistance effect to protect from damage
            // This is the ONLY effect the bowl provides
            if (!player.hasStatusEffect(StatusEffects.RESISTANCE)) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.RESISTANCE,
                        20,  // 1 second duration (refreshed every tick)
                        0,   // Resistance I
                        false,
                        false
                ));
            }
        }
    }

    /**
     * When the bowl is eaten, apply a temporary resistance boost
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, net.minecraft.entity.LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            // Apply Resistance III for 30 seconds when eaten
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.RESISTANCE,
                    600,  // 30 seconds
                    2,    // Resistance III
                    false,
                    false
            ));

            // Decrement stack
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        return super.finishUsing(stack, world, user);
    }
}
