package net.dman.thepicklejar.client;

import net.dman.thepicklejar.effect.ModEffects;
import net.dman.thepicklejar.mixin.GameRendererAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public final class ClientEffectHandler {
    private static boolean birdiesBlurEnabled;

    private ClientEffectHandler() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register
                (ClientEffectHandler::tick);
    }

    private static void tick(MinecraftClient client) {
        boolean affected = client.player != null
                && client.player.hasStatusEffect(ModEffects.CIRCLING_BIRDIES);

        if (affected && !birdiesBlurEnabled) {
            ((GameRendererAccessor) client.gameRenderer).thepicklejar$loadPostProcessor(
                    new Identifier("minecraft", "shaders/post/blur.json"));
            birdiesBlurEnabled = true;
        } else if (!affected && birdiesBlurEnabled) {
            client.gameRenderer.disablePostProcessor();
            birdiesBlurEnabled = false;
        }
    }
}
