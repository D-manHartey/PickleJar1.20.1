package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.util.BffsTargeting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin {
    @Inject(method = "isValidTarget", at = @At("HEAD"), cancellable = true)
    private void thepicklejar$rejectBffsProtectedWardenTarget(
            Entity target,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (BffsTargeting.isProtectedPlayer(target)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "updateAttackTarget", at = @At("HEAD"), cancellable = true)
    private void thepicklejar$blockBffsProtectedWardenBrainTarget(
            LivingEntity target,
            CallbackInfo ci
    ) {
        if (BffsTargeting.isProtectedPlayer(target)) {
            ci.cancel();
        }
    }

    @Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
    private void thepicklejar$blockBffsProtectedWardenAttack(
            Entity target,
            CallbackInfoReturnable<Boolean> cir
    ) {
        WardenEntity self = (WardenEntity) (Object) this;
        if (BffsTargeting.blocksTarget(self, target)) {
            cir.setReturnValue(false);
        }
    }
}
