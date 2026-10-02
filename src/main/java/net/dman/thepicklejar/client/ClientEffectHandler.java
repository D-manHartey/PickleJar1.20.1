package net.dman.thepicklejar.client;

import net.dman.thepicklejar.effect.ModEffects;
import net.dman.thepicklejar.mixin.GameRendererAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public final class ClientEffectHandler {
    private static final Identifier CIRCLING_BIRDIES_BLUR = new Identifier(
            "the-pickle-jar", "shaders/post/circling_birdies_blur.json");

    private static boolean ownsBirdiesBlur;

    private ClientEffectHandler() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientEffectHandler::tick);
    }

    private static void tick(MinecraftClient client) {
        boolean affected = client.player != null
                && client.player.hasStatusEffect(ModEffects.CIRCLING_BIRDIES);

        if (affected) {
            if (!ownsBirdiesBlur || client.gameRenderer.getPostProcessor() == null) {
                ((GameRendererAccessor) client.gameRenderer).thepicklejar$loadPostProcessor(
                        CIRCLING_BIRDIES_BLUR);
                ownsBirdiesBlur = true;
            }
            return;
        }

        if (ownsBirdiesBlur) {
            client.gameRenderer.disablePostProcessor();
            ownsBirdiesBlur = false;
        }
    }
}
