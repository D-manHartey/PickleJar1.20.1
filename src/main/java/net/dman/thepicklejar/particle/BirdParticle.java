package net.dman.thepicklejar.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public final class BirdParticle extends SpriteBillboardParticle {
    private final SpriteProvider sprites;

    private BirdParticle(ClientWorld world, double x, double y, double z,
                         double velocityX, double velocityY, double velocityZ,
                         SpriteProvider sprites) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.sprites = sprites;
        this.maxAge = 16 + this.random.nextInt(8);
        this.scale = 0.35F;
        this.gravityStrength = 0.0F;
        this.velocityMultiplier = 0.92F;
        this.collidesWithWorld = false;
        this.setSpriteForAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.sprites);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static final class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world,
                                       double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
            return new BirdParticle(world, x, y, z, velocityX, velocityY, velocityZ, sprites);
        }
    }
}
