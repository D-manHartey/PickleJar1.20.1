package net.dman.thepicklejar.particle;

import net.dman.thepicklejar.ThePickleJar;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModParticles {
    public static final DefaultParticleType BIRD = Registry.register(
            Registries.PARTICLE_TYPE, new Identifier(ThePickleJar.MOD_ID, "bird"),
            FabricParticleTypes.simple());

    private ModParticles() {
    }

    public static void registerParticles() {
    }
}
