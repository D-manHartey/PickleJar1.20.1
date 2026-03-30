package net.dman.thepicklejar.sound;

import net.dman.thepicklejar.ThePickleJar;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent INKBLOT_MALLET_HIT_1 = registerSoundEvent("inkblot_mallet_hit_1");
    public static final SoundEvent INKBLOT_MALLET_HIT_2 = registerSoundEvent("inkblot_mallet_hit_2");
    public static final SoundEvent INKBLOT_MALLET_HIT_3 = registerSoundEvent("inkblot_mallet_hit_3");

    public static final SoundEvent RAGGIDY_SCYTHE_HIT_1 = registerSoundEvent("raggidy_scythe_hit_1");
    public static final SoundEvent RAGGIDY_SCYTHE_HIT_2 = registerSoundEvent("raggidy_scythe_hit_2");
    public static final SoundEvent RAGGIDY_SCYTHE_HIT_3 = registerSoundEvent("raggidy_scythe_hit_3");

    public static final SoundEvent PHIL_BLOCK_BREAK= registerSoundEvent("phil_block_break");
    public static final SoundEvent PHIL_BLOCK_STEP = registerSoundEvent("phil_block_step");
    public static final SoundEvent PHIL_BLOCK_PLACE = registerSoundEvent("phil_block_place");
    public static final SoundEvent PHIL_BLOCK_HIT = registerSoundEvent("phil_block_hit");
    public static final SoundEvent PHIL_BLOCK_FALL = registerSoundEvent("phil_block_fall");

    public static final BlockSoundGroup PHIL_BLOCK_SOUNDS = new BlockSoundGroup(1f, 1f,
            ModSounds.PHIL_BLOCK_BREAK, ModSounds.PHIL_BLOCK_STEP, ModSounds.PHIL_BLOCK_PLACE,
            ModSounds.PHIL_BLOCK_HIT, ModSounds.PHIL_BLOCK_FALL);

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ThePickleJar.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ThePickleJar.LOGGER.info("Registering Sounds for" + ThePickleJar.MOD_ID);
    }

}
