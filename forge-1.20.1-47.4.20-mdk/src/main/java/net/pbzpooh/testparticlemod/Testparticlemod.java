package net.pbzpooh.testparticlemod;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.resources.ResourceLocation;

@Mod(Testparticlemod.MOD_ID)
public class Testparticlemod {

    public static final String MOD_ID = "testparticlemod";

    public Testparticlemod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    // -----------------------------------------------------------------------
    // Register one SimpleParticleType per effect.
    // The name here must match the .efkefc filename and the EmitterInfo path.
    // -----------------------------------------------------------------------
    public static class ModParticles {

        public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
                DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);

        public static final RegistryObject<SimpleParticleType> BIZZARD =
                PARTICLE_TYPES.register("bizzard", () -> new SimpleParticleType(false));

        public static final RegistryObject<SimpleParticleType> SAM =
                PARTICLE_TYPES.register("sam", () -> new SimpleParticleType(false));
    }

    // -----------------------------------------------------------------------
    // Client-side: register the provider for each particle type
    // -----------------------------------------------------------------------
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(
                    ModParticles.BIZZARD.get(),
                    sprites -> new EffekBridgeParticle.Provider(
                            sprites,
                            new ResourceLocation(MOD_ID, "bizzard")
                    )
            );
            event.registerSpriteSet(
                    ModParticles.SAM.get(),
                    sprites -> new EffekBridgeParticle.Provider(
                            sprites,
                            new ResourceLocation(MOD_ID, "sam")
                    )
            );
        }
    }
}