package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.effect.ModEffects;
import net.dman.thepicklejar.util.MobDespawnTracker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void thepicklejar$blockRealityOwnerDamage
            (DamageSource source, float amount,
             CallbackInfoReturnable<Boolean> cir) {
        LivingEntity target = (LivingEntity) (Object) this;
        if (target instanceof PlayerEntity player && source.getAttacker()
                instanceof MobEntity mob
        && MobDespawnTracker.isRealityMobOwner(mob, player.getUuid())) {
                cir.setReturnValue(false);
            }
        }

        @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void thepicklejar$blockPancakedMobJump(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.hasStatusEffect(ModEffects.PANCAKED)) {
            ci.cancel();
        }
    }

    @Inject(method = "updatePotionVisibility", at = @At("TAIL"))
    private void thepicklejar$applyRealityCloakVisibility(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self.hasStatusEffect(ModEffects.REALITY_CLOAK)) {
            self.setInvisible(true);
        }
    }
}
