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

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            applyConsequence(player);
        }

        return super.finishUsing(stack, world, user);
    }

    protected void applyConsequence(PlayerEntity player) {
    }
}
