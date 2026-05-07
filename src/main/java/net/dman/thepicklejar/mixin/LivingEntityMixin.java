package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.util.LifeStealManager;
import net.dman.thepicklejar.util.MobDespawnTracker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void blockRealityMobOwnerDamage(DamageSource source, float amount,
                                            CallbackInfoReturnable<Boolean> cir) {
        LivingEntity target = (LivingEntity) (Object) this;

        if (target instanceof PlayerEntity player && source.getAttacker() instanceof MobEntity mob) {
            if (MobDespawnTracker.isRealityMobOwner(mob, player.getUuid())) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "damage", at = @At("RETURN"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && source.getAttacker() instanceof PlayerEntity player) {
            LivingEntity target = (LivingEntity)(Object)this;
            LifeStealManager.handleAttack(player, target);
        }
    }
}
