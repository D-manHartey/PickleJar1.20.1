package net.dman.thepicklejar.screen;

import net.dman.thepicklejar.block.entity.GiardinieraAltarBlockEntity;
import net.dman.thepicklejar.item.ModItems;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.Slot;

public class GiardinieraAltarScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final GiardinieraAltarBlockEntity blockEntity;

    //Client-side constructor
    public GiardinieraAltarScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf)
    {
        this(syncId, inventory,
                inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(4)); // 4 properties: progress, maxProgress, fuelTime, maxFuelTime
    }

    // Server-side constructor
    public GiardinieraAltarScreenHandler(int syncId, PlayerInventory playerInventory,
                                         BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.GIARDINIERA_ALTAR_SCREEN_HANDLER, syncId);

        // 5 slots total: 3 ingredients + 1 output + 1 fuel
        checkSize(((Inventory) blockEntity), 5);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;
        this.blockEntity = ((GiardinieraAltarBlockEntity) blockEntity);

        // Add slots for the block entity
        // Texture layout: Main green GUI is 176x221 (0, 4 to 176, 225)
        // Fuel panel is 74x80 (178, 8 to 252, 88)

        // 3 Ingredient slots
        this.addSlot(new Slot(inventory, 0, 26, 17));
        this.addSlot(new Slot(inventory, 1, 81, 17));
        this.addSlot(new Slot(inventory, 2, 135, 17));

        // 1 Output slot
        this.addSlot(new Slot(inventory, 3, 81, 97));

        // 1 Fuel slot (inside fuel panel on the right)
        // Fuel panel starts at x=176 (relative to GUI), so absolute position is 176 + offset
        // Inside the fuel panel, position the fuel slot appropriately
        this.addSlot(new GiardinieraFuelSlot(inventory, 4, 207, 54)); // Fuel/Sacrifice

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getProgress() {
        return this.propertyDelegate.get(0);
    }

    public int getMaxProgress() {
        return this.propertyDelegate.get(1);
    }

    public boolean isConsumingFuel() {
        return propertyDelegate.get(2) > 0;
    }

    public int getFuelTime() {
        return this.propertyDelegate.get(2);
    }

    public int getMaxFuelTime() {
        return this.propertyDelegate.get(3);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            // If clicking from altar slots (ingredients, output, or fuel)
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // If clicking from player inventory
                // Try fuel slot first (slot 4)
                if (!this.insertItem(originalStack, 4, 5, false)) {
                    // Then try ingredient slots (slots 0-2)
                    if (!this.insertItem(originalStack, 0, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 9 + l * 18, 140 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 9 + i * 18, 198));
        }
    }

    /**
     * Custom fuel slot for the Giardiniera Altar
     * Accepts standard Minecraft fuel items and prevents items from being dropped on the ground
     */
    private static class GiardinieraFuelSlot extends Slot {
        public GiardinieraFuelSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return isFuel(stack);
        }

        @Override
        public int getMaxItemCount(ItemStack stack) {
            return 64;
        }

        /**
         * Check if an item is valid fuel
         * Accepts: coal, charcoal, wood, logs, planks, sticks, and other burnable items
         */
        private static boolean isFuel(ItemStack stack) {
            if (stack.isEmpty()) return false;

            return stack.getItem() ==
                    ModItems.SALSA_SOUL;
        }
    }
}
