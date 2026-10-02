package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.effect.ModEffects;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Inject(method = "scale", at = @At("TAIL"))
    private void thepicklejar$flattenPancakedPlayer(
            AbstractClientPlayerEntity player,
            MatrixStack matrices,
            float amount,
            CallbackInfo ci) {
        if (player.hasStatusEffect(ModEffects.PANCAKED)) {
            matrices.translate(0.0D, 0.75D, 0.0D);
            matrices.scale(1.0F, 0.12F, 1.0F);
        }
    }
}
