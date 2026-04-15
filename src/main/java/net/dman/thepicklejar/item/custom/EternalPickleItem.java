package net.dman.thepicklejar.item.custom;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class EternalPickleItem extends Item {
    public static final int COOLDOWN_TICKS = 70 * 20; // 70 seconds

    public EternalPickleItem(Settings settings) {
        super(settings.maxCount(1)); //Unstackable
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Checks if player is crouching to open GUI
        if (user.isSneaking()) {
            return TypedActionResult.pass(stack);
        }

        if (!world.isClient) {
            // Applies unique abilities
            useAbility(world, user, stack);

            // Applies consequences in too many pickles
            applyConsequence(user);

            // Set cooldown
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    protected abstract void useAbility(World world, PlayerEntity user, ItemStack stack);

    protected abstract void applyConsequence(PlayerEntity user);
}
