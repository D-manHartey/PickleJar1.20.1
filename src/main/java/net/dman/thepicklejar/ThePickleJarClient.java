package net.dman.thepicklejar;

import net.dman.thepicklejar.block.ModBlocks;
import net.dman.thepicklejar.block.entity.ModBlockEntities;
import net.dman.thepicklejar.block.entity.renderer.GiardinieraAltarBlockEntityRenderer;
import net.dman.thepicklejar.event.KeyEventHandler;
import net.dman.thepicklejar.screen.GiardinieraAltarScreen;
import net.dman.thepicklejar.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ThePickleJarClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEANUT_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TEA_LEAVES_CROP, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GREEN_CHRYSANTHEMUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_GREEN_CHRYSANTHEMUM, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PHIL_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PHIL_TRAPDOOR, RenderLayer.getCutout());

        HandledScreens.register(ModScreenHandlers.GIARDINIERA_ALTAR_SCREEN_HANDLER, GiardinieraAltarScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.GIARDINIERA_ALTAR_BLOCK_ENTITY, GiardinieraAltarBlockEntityRenderer::new);

        ModKeybindings.registerKeybindings();

        KeyEventHandler.registerKeyEvents();
    }
}
