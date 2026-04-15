package net.dman.thepicklejar.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.util.PlayerAbilityManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EternalPickleBowlSelectionScreen extends Screen {
    private static final Identifier TEXTURE = new Identifier(ThePickleJar.MOD_ID, "textures/gui/eternal_pickle_bowl_gui.png");

    // GUI panel dimensions
    private static final int PANEL_WIDTH = 124;
    private static final int PANEL_HEIGHT = 36;

    // Slot Definitions
    private static final int[] SLOT_X = {9, 33, 57, 81, 105, 9};
    private static final int[] SLOT_Y = {10, 10, 10, 10, 10, 24};

    // Sprite Definitions
    private static final int[] SPRITE_X = {139, 155, 171, 187, 203, 219};
    private static final int[] SPRITE_Y = {10, 10, 10, 10, 10, 10};
    private static final int SPRITE_WIDTH = 16;
    private static final int SPRITE_HEIGHT = 16;

    // Pickle names
    private static final String[] PICKLE_NAMES = {
            "Power Pickle",
            "Mind Pickle",
            "Reality Pickle",
            "Soul Pickle",
            "Time Pickle",
            "Space Pickle"
    };

    private int centerX;
    private int centerY;
    private int guiLeft;
    private int guiTop;

    public EternalPickleBowlSelectionScreen() {
        super(Text.literal("Rīcsiaþ anweald on þā ungearwe"));
    }

    @Override
    protected void init() {
        super.init();
        this.centerX = this.width / 2;
        this.centerY = this.height / 2;
        this.guiLeft = this.centerX - PANEL_WIDTH / 2;
        this.guiTop = this.centerY - PANEL_HEIGHT / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        this.client.getTextureManager().bindForSetup(TEXTURE);

        // Main GUI panel
        drawTexture(matrices, this.guiLeft, this.guiTop, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, 256, 256);

        // Draw pickle slots
        for (int i = 0; i < 6; i++) {
            int slotX = this.guiLeft + SLOT_X[i];
            int slotY = this.guiTop + SLOT_Y[i];

            // Draw slot background (light gray)
            fill(matrices, slotX, slotY, slotX + 16, slotY + 16, 0xFF8B8B8B);

            // Draw pickle sprite
            drawTexture(matrices, slotX, slotY, SPRITE_X[i], SPRITE_Y[i], SPRITE_WIDTH, SPRITE_HEIGHT, 256, 256);

            // Highlight if hovering
            if (mouseX >= slotX && mouseX < slotX + 16 && mouseY >= slotY && mouseY < slotY + 16) {
                fill(matrices, slotX, slotY, slotX + 16, slotY + 16, 0x4400FF00);
            }
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left click
            // Check if clicking on a pickle slot
            for (int i = 0; i < 6; i++) {
                int slotX = this.guiLeft + SLOT_X[i];
                int slotY = this.guiTop + SLOT_Y[i];

                if (mouseX >= slotX && mouseX < slotX + 16 && mouseY >= slotY && mouseY < slotY + 16) {
                    // Set the selected ability
                    PlayerAbilityManager.setSelectedAbility(this.client.player, i);
                    this.close();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void close() {
        this.client.setScreen(null);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}


