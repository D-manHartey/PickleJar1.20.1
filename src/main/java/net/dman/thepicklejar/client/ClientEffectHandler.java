package net.dman.thepicklejar.client;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.dman.thepicklejar.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public final class ClientEffectHandler {
    private static final ManagedShaderEffect CIRCLING_BIRDIES_BLUR =
            ShaderEffectManager.getInstance().manage(
                    new Identifier(
                            "the-pickle-jar", "shaders/post/circling_birdies_blur.json"
                    )
            );

    private ClientEffectHandler() {
    }

    public static void register() {
        ShaderEffectRenderCallback.EVENT.register(ClientEffectHandler::renderBirdiesBlur);
    }

    private static void renderBirdiesBlur(float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null
        && client.player.hasStatusEffect(ModEffects.CIRCLING_BIRDIES)) {
            CIRCLING_BIRDIES_BLUR.render(tickDelta);
        }
    }
}
