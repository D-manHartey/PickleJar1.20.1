package net.dman.thepicklejar.screen;

import net.dman.thepicklejar.ThePickleJar;
import net.dman.thepicklejar.network.SetBowlAbilityPacket;
import net.dman.thepicklejar.util.PlayerAbilityManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * EternalPickleBowlSelectionScreen - GUI for selecting which pickle ability to use
 * FIXED: Added selection feedback and proper ability selection
 */
public class EternalPickleBowlSelectionScreen extends Screen {
    private static final Identifier TEXTURE = new Identifier(ThePickleJar.MOD_ID,
            "textures/gui/eternal_pickle_bowl_gui.png");

    // GUI panel dimensions
    private static final int PANEL_WIDTH = 124;
    private static final int PANEL_HEIGHT = 36;

    // Slot Definitions
    private static final int[] SLOT_X = {9, 27, 45, 63, 81, 99};
    private static final int[] SLOT_Y = {10, 10, 10, 10, 10, 10};

    // Sprite Definitions
    private static final int[] SPRITE_X = {139, 157, 175, 193, 211, 228};
    private static final int[] SPRITE_Y = {10, 10, 10, 10, 10, 10};
    private static final int SPRITE_WIDTH = 16;
    private static final int SPRITE_HEIGHT = 16;

    // Pickle names for tooltips
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
    private int hoveredSlot = -1;  // Track which slot is hovered
    private int selectedSlot = -1; // Track which slot is selected
    private long selectionTime = 0; // Time when selection was made

    public EternalPickleBowlSelectionScreen() {
        super(Text.literal("Select Ability"));
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
        this.renderBackground(context);

        // Bind texture
        this.client.getTextureManager().bindTexture(TEXTURE);

        // Main GUI panel
        context.drawTexture(TEXTURE, this.guiLeft, this.guiTop, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, 256, 256);

        // Draw pickle slots
        hoveredSlot = -1;  // Reset hovered slot
        for (int i = 0; i < 6; i++) {
            int slotX = this.guiLeft + SLOT_X[i];
            int slotY = this.guiTop + SLOT_Y[i];

            // Draw slot background (light gray)
            context.fill(slotX, slotY, slotX + 16, slotY + 16, 0xFF8B8B8B);

            // Draw pickle sprite
            context.drawTexture(TEXTURE, slotX, slotY, 0, SPRITE_X[i], SPRITE_Y[i], SPRITE_WIDTH, SPRITE_HEIGHT, 256, 256);

            // Check if hovering
            if (mouseX >= slotX && mouseX < slotX + 16 && mouseY >= slotY && mouseY < slotY + 16) {
                // Highlight if hovering (green)
                context.fill(slotX, slotY, slotX + 16, slotY + 16, 0x4400FF00);
                hoveredSlot = i;  // Store hovered slot
            }

            // Highlight if selected (yellow/gold)
            if (i == selectedSlot) {
                context.fill(slotX, slotY, slotX + 16, slotY + 16, 0x44FFFF00);
            }
        }

        // Draw tooltip if hovering
        if (hoveredSlot >= 0 && hoveredSlot < PICKLE_NAMES.length) {
            Text tooltipText = Text.literal(PICKLE_NAMES[hoveredSlot]);
            context.drawTooltip(this.textRenderer, tooltipText, mouseX, mouseY);
        }

        // Draw selection confirmation message
        if (selectedSlot >= 0 && selectedSlot < PICKLE_NAMES.length) {
            long timeSinceSelection = System.currentTimeMillis() - selectionTime;

            // Show message for 2 seconds (2000ms)
            if (timeSinceSelection < 2000) {
                String message = "§a✓ " + PICKLE_NAMES[selectedSlot] + " Selected!";
                int textWidth = this.textRenderer.getWidth(message);
                int textX = this.centerX - textWidth / 2;
                int textY = this.guiTop - 20;

                context.drawText(this.textRenderer, message, textX, textY, 0xFFFFFF, true);
            } else {
                // Auto-close after 2 seconds
                this.close();
            }
        }

        super.render(context, mouseX, mouseY, delta);
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
                    PlayerAbilityManager.setSelectedAbilityIndex(i);
                    new SetBowlAbilityPacket(i).send();

                    // Mark as selected and show feedback
                    selectedSlot = i;
                    selectionTime = System.currentTimeMillis();

                    // Send message to player
                    this.client.player.sendMessage(
                            Text.literal("§a✓ " + PICKLE_NAMES[i] + " Selected!"),
                            true // Show above hotbar
                    );

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
