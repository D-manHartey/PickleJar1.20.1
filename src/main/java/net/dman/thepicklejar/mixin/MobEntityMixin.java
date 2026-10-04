package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.util.BffsTargeting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void thepicklejar$rejectBffsProtectedTarget(LivingEntity target, CallbackInfo ci) {
        MobEntity self = (MobEntity) (Object) this;
        if (target != null && BffsTargeting.blocksTarget(self, target)) {
            ci.cancel();
        }
    }
}
