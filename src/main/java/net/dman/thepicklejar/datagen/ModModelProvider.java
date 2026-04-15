package net.dman.thepicklejar.datagen;

import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.block.custom.TeaLeavesCropBlock;
import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.block.custom.PeanutButterCropBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        // Metal Blocks
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PICKLOLIUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PICKLOLIUM_DEPOSIT);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHUTNEY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHUTNEY_DEPOSIT);
        BlockStateModelGenerator.BlockTexturePool PhilPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PHIL_BLOCK);

        // Phil Collection
        PhilPool.button(ModBlocks.PHIL_BUTTON);
        PhilPool.stairs(ModBlocks.PHIL_STAIRS);
        PhilPool.slab(ModBlocks.PHIL_SLAB);
        PhilPool.pressurePlate(ModBlocks.PHIL_PRESSURE_PLATE);
        PhilPool.fence(ModBlocks.PHIL_FENCE);
        PhilPool.fenceGate(ModBlocks.PHIL_FENCE_GATE);
        PhilPool.wall(ModBlocks.PHIL_WALL);
        blockStateModelGenerator.registerDoor(ModBlocks.PHIL_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.PHIL_TRAPDOOR);

        // Crop Blocks
        blockStateModelGenerator.registerCrop(ModBlocks.PEANUT_CROP, PeanutButterCropBlock.AGE, 0, 1, 2, 3, 4, 5);
        blockStateModelGenerator.registerCrop(ModBlocks.TEA_LEAVES_CROP, TeaLeavesCropBlock.AGE, 0, 1, 2, 3, 4, 5, 6, 7, 8);

        // Flowers
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.GREEN_CHRYSANTHEMUM, ModBlocks.POTTED_GREEN_CHRYSANTHEMUM,
                BlockStateModelGenerator.TintType.NOT_TINTED);

        // Ultimate Workstation
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.GIARDINIERA_ALTAR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        // Metal
        itemModelGenerator.register(ModItems.PICKLOLIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_PICKLOLIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.RADIOACTIVE_PICKLOLIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.TOON_STEEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_CHUTNEY, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOLTEN_CHUTNEY, Models.GENERATED);

        // WIP stuff
        itemModelGenerator.register(ModItems.BROKEN_TELEPHONE, Models.GENERATED);

        // Food
        itemModelGenerator.register(ModItems.PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PICKLE_ALFREDO, Models.GENERATED);
        itemModelGenerator.register(ModItems.CUP_O_GREEN_TEA, Models.GENERATED);
        itemModelGenerator.register(ModItems.PEANUT_BUTTER, Models.GENERATED);
        itemModelGenerator.register(ModItems.GREEN_TEA_LEAVES, Models.GENERATED);

        // Fuel Sources
        itemModelGenerator.register(ModItems.GHERKIN_SOUL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SALSA_SOUL, Models.GENERATED);

        // Armor
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.TOON_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.TOON_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.TOON_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.TOON_BOOTS));

        // Eternal Jarred Energy
        itemModelGenerator.register(ModItems.JARRED_MIND, Models.GENERATED);
        itemModelGenerator.register(ModItems.JARRED_SPACE, Models.GENERATED);
        itemModelGenerator.register(ModItems.JARRED_REALITY, Models.GENERATED);
        itemModelGenerator.register(ModItems.JARRED_POWER, Models.GENERATED);
        itemModelGenerator.register(ModItems.JARRED_TIME, Models.GENERATED);
        itemModelGenerator.register(ModItems.JARRED_SOUL, Models.GENERATED);

        itemModelGenerator.register(ModItems.POWER_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MIND_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.REALITY_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MIND_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SPACE_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ETERNAL_BOWL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ETERNAL_PICKLE_BOWL, Models.GENERATED);

    }
}
