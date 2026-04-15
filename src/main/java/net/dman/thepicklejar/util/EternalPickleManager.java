package net.dman.thepicklejar.util;

import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.item.custom.EternalPickleItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class EternalPickleManager {

    public static void checkInventoryPenalties(PlayerEntity player) {
        if (player.getWorld().isClient) return;

        // Counts how many Eternal Pickles the player has
        int pickleCount = 0;
        boolean hasEternalBowl = false;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() instanceof EternalPickleItem) {
                pickleCount++;
            } else if (stack.getItem() == ModItems.ETERNAL_PICKLE_BOWL) {
                hasEternalBowl = true;
            }
        }

        // If the player has the Eternal Bowl, it negates all penalties
        if (hasEternalBowl) return;

        // Apply penalties based on Pickle Quantity
        if (pickleCount >= 3) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 0, false, false, true));
        }
        if (pickleCount >= 4) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200, 2, false, false, true));
        }
        if (pickleCount >= 5) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0, false, false, true));
        }
        if (pickleCount >= 6) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0, false, false, true));
        }
    }
}
