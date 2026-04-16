package net.dman.thepicklejar.item.custom;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/*
 * Base class for eternal pickle items
 * Abilities are triggered via keybind (V key)
 * Consequences are applied when eaten
 */
public abstract class EternalPickleItem extends Item {
    public static final int COOLDOWN_TICKS = 70 * 20; // 70 seconds

    public EternalPickleItem(Settings settings) {
        super(settings.maxCount(1)); // Unstackable
    }

    /*
     * Called when player RIGHT-CLICKS with the pickle
     * This now ONLY handles eating, not abilities
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Checks if player is crouching to open GUI
        if (user.isSneaking()) {
            return TypedActionResult.pass(stack);
        }

        // Allow normal eating behavior
        // The item will be consumed and finishUsing() will be called
        return TypedActionResult.pass(stack);
    }

    /*
     * Called when the player FINISHES eating/consuming the item
     * This applies the CONSEQUENCE only
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, net.minecraft.entity.LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            if (!world.isClient) {
                // Apply the consequence when eaten
                applyConsequence(player);
            }
        }

        // Call parent to handle normal food consumption
        return super.finishUsing(stack, world, user);
    }

    /*
     * Override this in subclasses to define the ability
     * Called when player PRESSES THE KEYBIND (V key by default)
     */
    protected abstract void useAbility(World world, PlayerEntity user, ItemStack stack);

    /*
     * Override this in subclasses to define the consequence
     * Called when player EATS the item
     */
    protected abstract void applyConsequence(PlayerEntity user);
}
