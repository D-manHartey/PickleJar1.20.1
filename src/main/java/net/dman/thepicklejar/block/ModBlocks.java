package net.dman.thepicklejar.block;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.block.custom.GiardinieraAltarBlock;
import net.dman.thepicklejar.block.custom.PeanutButterCropBlock;
import net.dman.thepicklejar.block.custom.PhilBlock;
import net.dman.thepicklejar.block.custom.TeaLeavesCropBlock;
import net.dman.thepicklejar.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    // Metal Blocks
    public static final Block PICKLOLIUM_DEPOSIT = registerBlock("picklolium_deposit",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS).sounds(BlockSoundGroup.SLIME).strength(35.0f), UniformIntProvider.create(4, 7)));
    public static final Block PICKLOLIUM_BLOCK = registerBlock("picklolium_block",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.SLIME)));
    public static final Block CHUTNEY_DEPOSIT = registerBlock("chutney_deposit",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS).sounds(BlockSoundGroup.CORAL).strength(35.0f), UniformIntProvider.create(3, 6)));
    public static final Block CHUTNEY_BLOCK = registerBlock("chutney_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.CORAL)));

    // Crop Blocks
    public static final Block PEANUT_CROP = Registry.register(Registries.BLOCK, new Identifier(ThePickleJar.MOD_ID, "peanut_crop"),
            new PeanutButterCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block TEA_LEAVES_CROP = Registry.register(Registries.BLOCK, new Identifier(ThePickleJar.MOD_ID, "tea_leaves_crop"),
            new TeaLeavesCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));

    // Flowers
    public static final Block GREEN_CHRYSANTHEMUM = registerBlock("green_chrysanthemum",
            new FlowerBlock(StatusEffects.INSTANT_HEALTH, 3,
                    FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().noCollision()));
    public static final Block POTTED_GREEN_CHRYSANTHEMUM = Registry.register(Registries.BLOCK, new Identifier(ThePickleJar.MOD_ID, "potted_green_chrysanthemum"),
            new FlowerPotBlock(GREEN_CHRYSANTHEMUM,  FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));

    // Phil Section
    public static final Block PHIL_BLOCK = registerBlock("phil_block",
            new PhilBlock(FabricBlockSettings.copyOf(Blocks.MUD).sounds(ModSounds.PHIL_BLOCK_SOUNDS)));

    public static final Block PHIL_STAIRS = registerBlock("phil_stairs",
            new StairsBlock(ModBlocks.PHIL_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.SHROOMLIGHT).sounds(ModSounds.PHIL_BLOCK_SOUNDS)));
    public static final Block PHIL_SLAB = registerBlock("phil_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.SHROOMLIGHT).sounds(ModSounds.PHIL_BLOCK_SOUNDS)));

    public static final Block PHIL_BUTTON = registerBlock("phil_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OCHRE_FROGLIGHT), BlockSetType.MANGROVE, 10, true));
    public static final Block PHIL_PRESSURE_PLATE = registerBlock("phil_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                    FabricBlockSettings.copyOf(Blocks.OCHRE_FROGLIGHT), BlockSetType.MANGROVE));

    public static final Block PHIL_FENCE = registerBlock("phil_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_FENCE)));
    public static final Block PHIL_FENCE_GATE = registerBlock("phil_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_FENCE_GATE), WoodType.MANGROVE));
    public static final Block PHIL_WALL = registerBlock("phil_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.TUBE_CORAL_FAN)));

    public static final Block PHIL_DOOR = registerBlock("phil_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_DOOR), BlockSetType.MANGROVE));
    public static final Block PHIL_TRAPDOOR = registerBlock("phil_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_TRAPDOOR), BlockSetType.MANGROVE));

    // Ultimate Workstation
    public static final Block GIARDINIERA_ALTAR = registerBlock("giardiniera_altar",
            new GiardinieraAltarBlock(FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE).nonOpaque()));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ThePickleJar.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ThePickleJar.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        ThePickleJar.LOGGER.info("Registering ModBlocks for " + ThePickleJar.MOD_ID);
    }
}
