package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Prevents player from jumping after consuming Time Pickle and with Pancaked Effect
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void thepicklejar$blockRestrictedJump(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        StatusEffectInstance slowness = player.getStatusEffect(StatusEffects.SLOWNESS);
        if (player.hasStatusEffect(ModEffects.PANCAKED)
                || (slowness != null && slowness.getAmplifier() >= 10)) {
            ci.cancel();
        }
    }
}
