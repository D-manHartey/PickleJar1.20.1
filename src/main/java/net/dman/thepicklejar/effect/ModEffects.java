package net.dman.thepicklejar.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final StatusEffect PANCAKED = PancakedEffect();
    public static final StatusEffect CIRCLINGBIRDIES = CirclingBirdiesEffect();

    public static void registerEffects() {

        Registry.register(Registries.STATUS_EFFECT, new Identifier("the-pickle-jar", "pancaked"), PANCAKED);
        Registry.register(Registries.STATUS_EFFECT, new Identifier("the-pickle-jar", "circlingbirdies"), CIRCLINGBIRDIES);

    }
}
