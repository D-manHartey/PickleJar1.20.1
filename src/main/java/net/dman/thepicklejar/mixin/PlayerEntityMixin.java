package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.util.LifeStealManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to inject phasing tick handler into PlayerEntity.tick()
 * This ensures phasing is properly handled every tick
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // Prevents player from jumping after consuming Time Pickle
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void blockJumpDuringTimePickleConsequence(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        StatusEffectInstance slowness = player.getStatusEffect(StatusEffects.SLOWNESS);

        if (slowness != null && slowness.getAmplifier() >= 10) {
            ci.cancel();
        }
    }

    /**
     * Inject into PlayerEntity.attack() to handle life steal on every hit
     * This triggers when a player attacks any entity, not just kills
     */
    @Inject(method = "attack", at = @At("HEAD"))
    private void onPlayerAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Check if target is a living entity
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;

            // Handle life steal if player has it active
            LifeStealManager.handleAttack(player, livingTarget);
        }
    }
}
