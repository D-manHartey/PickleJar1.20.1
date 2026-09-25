package net.dman.thepicklejar.mixin;

import net.dman.thepicklejar.util.MobDespawnTracker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class RealityMobLootMixin {
    @Inject(method = "dropLoot", at =  @At("HEAD"), cancellable = true)
    private void thepicklejar$preventRealityMobLoot(
            DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self instanceof MobEntity mob && MobDespawnTracker.isRealityMob(mob)) {
            ci.cancel();
        }
    }
}
