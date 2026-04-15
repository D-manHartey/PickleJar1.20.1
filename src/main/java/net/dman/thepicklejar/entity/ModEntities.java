package net.dman.thepicklejar.entity;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.entity.custom.TimeProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TimeProjectileEntity> TIME_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ThePickleJar.MOD_ID, "time_projectile"),
            FabricEntityTypeBuilder.<TimeProjectileEntity>create(SpawnGroup.MISC, TimeProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(10)
                    .build()
    );
}
