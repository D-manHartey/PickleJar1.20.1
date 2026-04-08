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
    private static final Identifier TEXTURE = new Identifier(ThePickleJar.MOD_ID, "textures/gui/giardiniera_altar_gui.png");

    // Sprite sizes (from texture sheet)
    private static final int LEFT_ARROW_W   = 45;
    private static final int LEFT_ARROW_H   = 76;
    private static final int MIDDLE_ARROW_W = 19;
    private static final int MIDDLE_ARROW_H = 52;
    private static final int RIGHT_ARROW_W  = 44;
    private static final int RIGHT_ARROW_H  = 76;
    private static final int EYES_W         = 22;
    private static final int EYES_H         = 10;


    public GiardinieraAltarScreen(GiardinieraAltarScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 178;
        this.backgroundHeight = 240;
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY = 5;

        // Hide the player inventory label
        playerInventoryTitleX = 10000; // Moves it off-screen
        playerInventoryTitleY = 10000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // Draw main green GUI panel: texture (0, 4), size 178x222
        context.drawTexture(TEXTURE, x, y, 0, 4, backgroundWidth, backgroundHeight);

        // Draw red fuel panel: texture (178, 8), size 75x81
        // Positioned immediately to the right of the green panel
        context.drawTexture(TEXTURE, x + backgroundWidth, y + 4, 178, 8, 75, 81);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        // Animations drawn AFTER slots so they appear on top
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawText(textRenderer, title, x + titleX, y + titleY,0x008000, false);

        renderProgressArrows(context, x, y);
        renderFuelIndicator(context, x, y);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private int scaleProgress(int spriteSize) {
        int progress = handler.getProgress();
        int maxProgress = handler.getMaxProgress();
        return maxProgress != 0 && progress != 0 ? progress * spriteSize / maxProgress : 0;
    }

    private int scaleFuel(int spriteSize) {
        int fuelTime = handler.getFuelTime();
        int maxFuelTime = handler.getMaxFuelTime();
        return maxFuelTime != 0 && fuelTime != 0 ? fuelTime * spriteSize / maxFuelTime : 0;
    }

    private void renderProgressArrows(DrawContext context, int x, int y) {
        if (!handler.isCrafting()) return;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        // Left arrow: GUI (30, 43), sprite (179, 180), size 45x76 — fills downward
        int leftH = scaleProgress(LEFT_ARROW_H);
        context.drawTexture(TEXTURE,
                x + 30, y + 39,
                179, 180,
                LEFT_ARROW_W, leftH);

        // Middle arrow: GUI (79, 43), sprite (181, 93), size 19x52 — fills downward
        int midH = scaleProgress(MIDDLE_ARROW_H);
        context.drawTexture(TEXTURE,
                x + 79, y + 38,
                181, 93,
                MIDDLE_ARROW_W, midH);

        // Right arrow: GUI (103, 43), sprite (211, 103), size 44x76 — fills downward
        int rightH = scaleProgress(RIGHT_ARROW_H);
        context.drawTexture(TEXTURE,
                x + 103, y + 39,
                211, 103,
                RIGHT_ARROW_W, rightH);
    }

    private void renderFuelIndicator(DrawContext context, int x, int y) {
        if (!handler.isConsumingFuel()) return;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        // Eyes: GUI (204, 21), sprite (209, 92), size 22x10 — fills downward
        int eyesH = scaleFuel(EYES_H);
        context.drawTexture(TEXTURE,
                x + 204, y + 17 + (EYES_H - eyesH),
                209, 92 + (EYES_H - eyesH),
                EYES_W, eyesH);
    }
}