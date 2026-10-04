package net.dman.thepicklejar.item.custom;

import net.dman.thepicklejar.effect.ModEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class FriendshipPickleItem extends Item {
    public static final int BFFS_DURATION = 100 * 120 * 10;

    public FriendshipPickleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);

        if (!world.isClient && user instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(ModEffects.BFFS, BFFS_DURATION,
            0, false, true, true));
        }
        return result;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.the-pickle-jar.friendship_pickle.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
