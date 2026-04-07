package net.dman.thepicklejar.block.entity;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<GiardinieraAltarBlockEntity> GIARDINIERA_ALTAR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ThePickleJar.MOD_ID, "giardiniera_altar_be"),
                    FabricBlockEntityTypeBuilder.create(GiardinieraAltarBlockEntity::new,
                            ModBlocks.GIADINIERA_ALTAR).build());

    public static void registerBlockEntities() {
        ThePickleJar.LOGGER.info("Registering Block Entities for " + ThePickleJar.MOD_ID);
    }
}
