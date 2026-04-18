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
 * Mind Pickle - Grants Haste III for 5 minutes (ability)
 * Consequence: Mining Fatigue effect when eaten
 */
public class MindPickle extends EternalPickleItem{
    public MindPickle(Settings settings) {
        super(settings);
    }

    /**
     * Apply consequence when eaten
     * Nausea effect
     */
    @Override
    protected void applyConsequence(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.NAUSEA, 2400, 7, false, false, true)
            );
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.the-pickle-jar.mind_pickle.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
