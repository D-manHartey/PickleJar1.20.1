package net.dman.thepicklejar.item.custom;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

/*
 * Base class for Eternal Pickle items
 * Handles both ability activation (via keybind) and eating (consequences)
 */
public class EternalPickleItem extends Item {

    public EternalPickleItem(Settings settings) {
        super(settings);
    }

    /*
     * Called when the item is eaten (right-click to consume)
     * Applies the consequence effect
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            // Apply consequence when eaten
            applyConsequence(player);

            // Decrement stack
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        return super.finishUsing(stack, world, user);
    }

    /*
     * Apply the consequence effect for this pickle
     * Override in subclasses to provide specific consequences
     */
    protected void applyConsequence(PlayerEntity player) {
        // Base implementation - override in subclasses
    }

    /*
     * Check if this pickle is on cooldown
     */
    protected boolean isOnCooldown(PlayerEntity player, String abilityName) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return false; // Can't check cooldown on client
        }
        return EternalPickles.isOnCooldown(serverPlayer, abilityName);
    }

    /*
     * Set cooldown for this pickle's ability
     */
    protected void setCooldown(PlayerEntity player, String abilityName) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return; // Can't set cooldown on client
        }
        EternalPickles.setCooldown(serverPlayer, abilityName);
    }
}
