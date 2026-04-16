package net.dman.thepicklejar.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * EternalPickleBowlItem - Utility item that negates penalties from other pickles
 * No active ability, no consequence when eaten
 */
public class EternalPickleBowlItem extends EternalPickleItem {

    public EternalPickleBowlItem(Settings settings) {
        super(settings);
    }

    /**
     * Override finishUsing to prevent any effects when eaten
     * The bowl has no consequence
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            // Bowl has no consequence, just consume it
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }

            // Send message
            player.sendMessage(
                    net.minecraft.text.Text.literal("§6You consumed the Eternal Pickle Bowl!"),
                    false
            );
        }

        return stack;
    }

    /**
     * Bowl has no consequence
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        // Bowl has no consequence - do nothing
    }
}
