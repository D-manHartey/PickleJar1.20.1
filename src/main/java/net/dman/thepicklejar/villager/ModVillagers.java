package net.dman.thepicklejar.villager;

import com.google.common.collect.ImmutableSet;
import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {

    public static final RegistryKey<PointOfInterestType> PHIL_POI_KEY = poiKey("philpoi");
    public static final PointOfInterestType PHIL_POI = registerPoi("philpoi", ModBlocks.PHIL_BLOCK);

    public static final VillagerProfession ZANY_WORSHIPPER = registerProfession("zany_worshipper", PHIL_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(ThePickleJar.MOD_ID, name),
                new VillagerProfession(name, entry -> entry.matchesKey(type),
                        entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK));
    }

    private static PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(ThePickleJar.MOD_ID, name), 1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> poiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(ThePickleJar.MOD_ID, name));
    }

    public static void registerVillagers() {
        ThePickleJar.LOGGER.info("Registering Villagers" + ThePickleJar.MOD_ID);
    }
}
