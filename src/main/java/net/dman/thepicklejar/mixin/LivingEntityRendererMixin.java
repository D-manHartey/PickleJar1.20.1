package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.effect.ModEffects;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "render",
            at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;scale(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;F)V",
            shift = At.Shift.AFTER))
    private void thepicklejar$flattenPancakedEntity(
            LivingEntity entity,
            float entityYaw,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            CallbackInfo ci
    ) {
        if (entity.hasStatusEffect(ModEffects.PANCAKED)) {
            matrices.translate(0.0D, 0.75D, 0.0D);
            matrices.scale(1.8F, 0.12F, 1.8F);
        }
    }
}
