package net.dman.thepicklejar.datagen;

import net.dman.thepicklejar.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.PICKLOLIUM_DEPOSIT)
                .add(ModBlocks.PICKLOLIUM_BLOCK)
                .add(ModBlocks.CHUTNEY_BLOCK)
                .add(ModBlocks.CHUTNEY_DEPOSIT);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.PICKLOLIUM_DEPOSIT)
                .add(ModBlocks.PICKLOLIUM_BLOCK)
                .add(ModBlocks.CHUTNEY_BLOCK)
                .add(ModBlocks.CHUTNEY_DEPOSIT);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.PICKLOLIUM_DEPOSIT)
                .add(ModBlocks.PICKLOLIUM_BLOCK)
                .add(ModBlocks.CHUTNEY_BLOCK)
                .add(ModBlocks.CHUTNEY_DEPOSIT);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.PICKLOLIUM_DEPOSIT)
                .add(ModBlocks.PICKLOLIUM_BLOCK)
                .add(ModBlocks.CHUTNEY_BLOCK)
                .add(ModBlocks.CHUTNEY_DEPOSIT);

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
                .add(ModBlocks.PICKLOLIUM_DEPOSIT)
                .add(ModBlocks.PICKLOLIUM_BLOCK)
                .add(ModBlocks.CHUTNEY_BLOCK)
                .add(ModBlocks.CHUTNEY_DEPOSIT);

        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(ModBlocks.PHIL_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.PHIL_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.PHIL_WALL);
    }
}
