package net.pbzpooh.testparticlemod;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffekBridgeParticle extends Particle {

    private final ResourceLocation effectLocation;
    private int timer = 0;

    protected EffekBridgeParticle(ClientLevel level, double x, double y, double z,
                                  ResourceLocation effectLocation) {
        super(level, x, y, z);
        this.effectLocation = effectLocation;
        // Make this dummy particle invisible and very short-lived
        this.lifetime = 2;
        this.alpha = 0f;
    }

    @Override
    public void tick() {
        if (timer == 0) {
            timer++;
            // Fire the Effekseer effect using THIS particle's coordinates
            // this.x/y/z is correct because vanilla set them properly before tick()
            ParticleEmitterInfo info = new ParticleEmitterInfo(effectLocation);
            AAALevel.addParticle(this.level, false, info.position(this.x, this.y, this.z));
        }
        super.tick();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float v) {
        
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        // Invisible — we don't want to render this dummy particle at all
        return ParticleRenderType.NO_RENDER;
    }

    // -----------------------------------------------------------------------
    // Provider — one per effect, holds the ResourceLocation
    // -----------------------------------------------------------------------
    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final ResourceLocation effectLocation;

        public Provider(SpriteSet sprites, ResourceLocation effectLocation) {
            this.effectLocation = effectLocation;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            // Return the dummy particle — it fires the effect on its first tick
            return new EffekBridgeParticle(level, x, y, z, effectLocation);
        }
    }
}