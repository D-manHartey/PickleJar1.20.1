package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.util.BffsTargeting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HoglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoglinEntity.class)
public abstract class HoglinEntityMixin {
    @Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
    private void thepicklejar$blockBffsProtectedHoglinAttack(
            Entity target,
            CallbackInfoReturnable<Boolean> cir
    ) {
        HoglinEntity self = (HoglinEntity) (Object) this;
        if (BffsTargeting.blocksTarget(self, target)) {
            cir.setReturnValue(false);
        }
    }
}
