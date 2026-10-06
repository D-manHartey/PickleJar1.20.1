package net.dman.thepicklejar.sound;

import dev.architectury.platform.Mod;
import net.dman.thepicklejar.ThePickleJar;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
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
    public static final SoundEvent PHIL_FENCEGATE_OPEN = registerSoundEvent("phil_fencegate_open");
    public static final SoundEvent PHIL_FENCEGATE_CLOSE = registerSoundEvent("phil_fencegate_close");
    public static final SoundEvent PHIL_BUTTON_PRESS = registerSoundEvent("phil_button_press");
    public static final SoundEvent PHIL_BUTTON_RELEASE = registerSoundEvent("phil_button_release");
    public static final SoundEvent PHIL_DOOR_OPEN = registerSoundEvent("phil_door_open");
    public static final SoundEvent PHIL_DOOR_CLOSE = registerSoundEvent("phil_door_close");
    public static final SoundEvent PHIL_PRESSUREPLATE_PRESS = registerSoundEvent("phil_pressureplate_press");
    public static final SoundEvent PHIL_PRESSUREPLATE_RELEASE = registerSoundEvent("phil_pressureplate_release");
    public static final SoundEvent PHIL_TRAPDOOR_OPEN = registerSoundEvent("phil_trapdoor_open");
    public static final SoundEvent PHIL_TRAPDOOR_CLOSE = registerSoundEvent("phil_trapdoor_close");

    public static final BlockSoundGroup PHIL_BLOCK_SOUNDS = new BlockSoundGroup(1f, 1f,
            ModSounds.PHIL_BLOCK_BREAK, ModSounds.PHIL_BLOCK_STEP, ModSounds.PHIL_BLOCK_PLACE,
            ModSounds.PHIL_BLOCK_HIT, ModSounds.PHIL_BLOCK_FALL);

    public static final BlockSetType PHIL_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.WARPED)
            .buttonClickOnSound(ModSounds.PHIL_BUTTON_PRESS).buttonClickOffSound(ModSounds.PHIL_BUTTON_RELEASE)
            .doorOpenSound(ModSounds.PHIL_DOOR_OPEN).doorCloseSound(ModSounds.PHIL_DOOR_CLOSE)
            .pressurePlateClickOnSound(ModSounds.PHIL_PRESSUREPLATE_PRESS)
            .pressurePlateClickOffSound(ModSounds.PHIL_PRESSUREPLATE_RELEASE)
            .trapdoorOpenSound(ModSounds.PHIL_TRAPDOOR_OPEN).trapdoorCloseSound(ModSounds.PHIL_TRAPDOOR_CLOSE)
            .build(new Identifier(ThePickleJar.MOD_ID, "phil_block_set_type"));

    public static final WoodType PHIL_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.WARPED)
            .fenceGateOpenSound(ModSounds.PHIL_FENCEGATE_OPEN)
            .fenceGateCloseSound(ModSounds.PHIL_FENCEGATE_CLOSE)
            .build(new Identifier(ThePickleJar.MOD_ID, "phil_wood_type"), PHIL_BLOCK_SET_TYPE);

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ThePickleJar.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ThePickleJar.LOGGER.info("Registering Sounds for" + ThePickleJar.MOD_ID);
    }

}
