package net.dman.thepicklejar.datagen;

import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.item.ModItems;
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
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PICKLOLIUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PICKLOLIUM_DEPOSIT);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.PICKLOLIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_PICKLOLIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.RADIOACTIVE_PICKLOLIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.TOON_STEEL, Models.GENERATED);

        itemModelGenerator.register(ModItems.BROKEN_TELEPHONE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_PICKLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.GHERKIN_SOUL, Models.GENERATED);

        itemModelGenerator.registerArmor(((ArmorItem) ModItems.TOON_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.TOON_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.TOON_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.TOON_BOOTS));

    }
}
