package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void thepicklejar$reverseBirdiesMovement(
            boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null
        || !MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.CIRCLING_BIRDIES)) {
            return;
        }

        Input input = (Input) (Object) this;
        input.movementForward = -input.movementForward;
        input.movementSideways = -input.movementSideways;

        boolean forward = input.pressingForward;
        input.pressingForward = input.pressingBack;
        input.pressingBack = forward;

        boolean left = input.pressingLeft;
        input.pressingLeft = input.pressingRight;
        input.pressingRight = left;
    }
}
