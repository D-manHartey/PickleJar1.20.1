package net.dman.thepicklejar.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dman.thepicklejar.ThePickleJar;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GiardinieraAltarScreen extends HandledScreen<GiardinieraAltarScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ThePickleJar.MOD_ID,
            "textures/gui/giardiniera_altar_gui.png");

    // Sprite sizes (from texture sheet)
    private final int LEFT_ARROW_W = 45;
    private final int LEFT_ARROW_H = 76;
    private final int MIDDLE_ARROW_W = 19;
    private final int MIDDLE_ARROW_H = 52;
    private final int RIGHT_ARROW_W = 44;
    private final int RIGHT_ARROW_H = 76;
    private final int EYES_W = 22;
    private final int EYES_H = 10;

    public GiardinieraAltarScreen(GiardinieraAltarScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        // Set the background size to match the main green GUI panel (178 x 222)
        this.backgroundWidth = 178;
        this.backgroundHeight = 222;
    }

    @Override
    protected void init() {
        super.init();
        // Adjust title placement based on the 256x256 texture
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY = 5; // Adjust Y position based on texture design
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // Draw main green GUI background (176 x 221 pixels from texture coordinates 0, 4)
        context.drawTexture(TEXTURE, x, y, 0, 4, backgroundWidth, backgroundHeight);

        // Draw red fuel panel: texture (178, 8), size 75x81
        // Positioned immediately to the right of the green panel
        context.drawTexture(TEXTURE, x + backgroundWidth, y + 4, 178, 8, 75, 81);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        // Draw animations AFTER slots are rendered (on top)
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // Render progress arrows
        renderProgressArrow(context, x, y);
        // Render fuel indicator (glowing eyes)
        renderFuelIndicator(context, x, y);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()) return;

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

            // Left arrow: GUI (30, 43), sprite (179, 180), size 45x76 — fills downward
            int leftH = scaleProgress(LEFT_ARROW_H);
            context.drawTexture(TEXTURE,
                    x + 30, y + 43 + (LEFT_ARROW_H - leftH),
                    179, 180 + (LEFT_ARROW_H - leftH),
                    LEFT_ARROW_W, leftH);

            // Middle arrow: GUI (79, 43), sprite (181, 93), size 19x52 — fills downward
            int midH = scaleProgress(MIDDLE_ARROW_H);
            context.drawTexture(TEXTURE,
                    x + 79, y + 43 + (MIDDLE_ARROW_H - midH),
                    181, 93 + (MIDDLE_ARROW_H - midH),
                    MIDDLE_ARROW_W, midH);

            // Right arrow: GUI (103, 43), sprite (211, 103), size 44x76 — fills downward
            int rightH = scaleProgress(RIGHT_ARROW_H);
            context.drawTexture(TEXTURE,
                    x + 103, y + 43 + (RIGHT_ARROW_H - rightH),
                    211, 103 + (RIGHT_ARROW_H - rightH),
                    RIGHT_ARROW_W, rightH);
        }
}

    private void renderFuelIndicator(DrawContext context, int x, int y) {
        if (handler.isConsumingFuel()) return;

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

            // Eyes
            // GUI position: (204, 21), sprite: (209, 92), size: 22x10
            // Burns from left to right as fuel is consumed (width shrinks)
            int eyesW = handler.getScaledFuelProgress(EYES_W);
            context.drawTexture(TEXTURE,
                    x + 204, y + 21,   // screen position (fixed, doesn't move)
                    209, 92,            // texture u, v
                    eyesW, EYES_H);     // scaled width, fixed height
    }
}
