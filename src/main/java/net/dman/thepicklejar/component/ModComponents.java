package net.dman.thepicklejar.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.dman.thepicklejar.ThePickleJar;
import net.minecraft.util.Identifier;

public final class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<PicklePowerComponent> PICKLE_POWER =
            ComponentRegistryV3.INSTANCE.getOrCreate(
                    new Identifier(ThePickleJar.MOD_ID, "pickle_power"),
                    PicklePowerComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PICKLE_POWER,
                PicklePowerComponent::new,
                RespawnCopyStrategy.ALWAYS_COPY);
    }
}
