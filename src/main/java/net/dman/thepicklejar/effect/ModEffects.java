package net.dman.thepicklejar.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final StatusEffect PANCAKED = new PancakedEffect();
    public static final StatusEffect CIRCLING_BIRDIES = new CirclingBirdiesEffect();
    public static final StatusEffect REALITY_CLOAK = new RealityCloakEffect();
    public static final StatusEffect SOUL_VEIL = new SoulVeilEffect();

    private ModEffects() {

    }

    public static void registerEffects() {

        Registry.register(Registries.STATUS_EFFECT,
                new Identifier("the-pickle-jar", "pancaked"), PANCAKED);
        Registry.register(Registries.STATUS_EFFECT,
                new Identifier("the-pickle-jar", "circling_birdies"), CIRCLING_BIRDIES);
        Registry.register(Registries.STATUS_EFFECT,
                new Identifier("the-pickle-jar", "reality_cloak"), REALITY_CLOAK);
        Registry.register(Registries.STATUS_EFFECT,
                new Identifier("the-pickle-jar", "soul_veil"), SOUL_VEIL);

    }
}
