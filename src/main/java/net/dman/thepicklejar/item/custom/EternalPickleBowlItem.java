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
 * The bowl itself does NOT provide any passive effects
 * It allows players to select and activate pickle abilities via keybind (separate from V key)
 *
 * When eaten:
 * - Does NOT apply any effects
 * - Simply consumed like normal food
 */
public class EternalPickleBowlItem extends Item {


    public EternalPickleBowlItem(Settings settings) {
        super(settings);
    }


    /**
     * When the bowl is eaten, simply consume it
     * No effects applied
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, net.minecraft.entity.LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            // Simply decrement stack - no effects
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        return super.finishUsing(stack, world, user);
    }
}
