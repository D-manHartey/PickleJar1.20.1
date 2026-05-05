package net.dman.thepicklejar.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Power Pickle - Grants Strength III for 10 seconds (ability)
 * Consequence: Weakness effect when eaten
 */
public class PowerPickle extends EternalPickleItem{
    public PowerPickle(Item.Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Wither effect
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.WITHER, 600, 2, false, false, true)
            );
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.the-pickle-jar.power_pickle.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
