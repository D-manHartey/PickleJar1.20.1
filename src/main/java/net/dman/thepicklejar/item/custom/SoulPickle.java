package net.dman.thepicklejar.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Soul Pickle - Grants life steal ability for 2 minutes (ability)
 * Consequence: Wither effect when eaten
 */
public class SoulPickle extends EternalPickleItem{
    public SoulPickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Levitation effect
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.LEVITATION, 1800, 7, false, false, true)
            );
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.the-pickle-jar.soul_pickle.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
