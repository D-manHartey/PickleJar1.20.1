package net.dman.thepicklejar.block.entity;

import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.recipe.GiardinieraAltarRecipe;
import net.dman.thepicklejar.screen.GiardinieraAltarScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GiardinieraAltarBlockEntity extends BlockEntity implements
        ExtendedScreenHandlerFactory, ImplementedInventory {
    // 5 Slots: 3 Ingredients (0, 1 , 2), 1 Output (3), 1 Sacrifice (4)
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    private static final int INGREDIENT_SLOT_1 = 0;
    private static final int INGREDIENT_SLOT_2 = 1;
    private static final int INGREDIENT_SLOT_3 = 2;
    private static final int OUTPUT_SLOT = 3;
    private static final int FUEL_SLOT = 4;


    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public GiardinieraAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GIARDINIERA_ALTAR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GiardinieraAltarBlockEntity.this.progress;
                    case 1 -> GiardinieraAltarBlockEntity.this.maxProgress;
                    case 2 -> GiardinieraAltarBlockEntity.this.fuelTime;
                    case 3 -> GiardinieraAltarBlockEntity.this.maxFuelTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GiardinieraAltarBlockEntity.this.progress = value;
                    case 1 -> GiardinieraAltarBlockEntity.this.maxProgress = value;
                    case 2 -> GiardinieraAltarBlockEntity.this.fuelTime = value;
                    case 3 -> GiardinieraAltarBlockEntity.this.maxFuelTime = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    public ItemStack getRenderStack() {
        if (this.getStack(OUTPUT_SLOT).isEmpty()) {
            return this.getStack(INGREDIENT_SLOT_2);
        } else {
            return this.getStack(OUTPUT_SLOT);
        }
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Giardiniera Altar");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("giardiniera_altar.progress", progress);
        nbt.putInt("giardiniera_altar.fuelTime", fuelTime);
        nbt.putInt("giardiniera_altar.maxFuelTime", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("giardiniera_altar.progress");
        fuelTime = nbt.getInt("giardiniera_altar.fuelTime");
        maxFuelTime = nbt.getInt("giardiniera_altar.maxFuelTime");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GiardinieraAltarScreenHandler(syncId, playerInventory, this,
                this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        // Handle fuel consumption
        if (isConsumingFuel()) {
            this.fuelTime--;
        }

        // Example logic for starting crafting
        if (hasRecipe()) {
            // Consume fuel if needed
            if (!isConsumingFuel() && hasFuelInSlot()) {
                consumeFuel();
            }

            if (isConsumingFuel()) {
                this.progress++;
                markDirty(world, pos, state);

                if (this.progress >= this.maxProgress) {
                    craftItem();
                    resetProgress();
                }
            } else {
                resetProgress();
                markDirty(world, pos, state);
            }
        } else {
            resetProgress();
            markDirty(world, pos, state);
        }
    }

    private boolean isConsumingFuel() {
        return this.fuelTime > 0;
    }

    private boolean hasFuelInSlot() {
        return !this.getStack(FUEL_SLOT).isEmpty() && this.getStack(FUEL_SLOT).getItem() ==
                ModItems.SALSA_SOUL; // put Fuel Item later
    }

    private void consumeFuel() {
        this.fuelTime = 400; // Standard duration
        this.maxFuelTime = this.fuelTime;
        this.removeStack(FUEL_SLOT, 1);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        Optional<GiardinieraAltarRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty() || this.world == null) {
            return;
        }

        ItemStack result = recipe.get().getOutput(this.world.getRegistryManager()).copy();

        this.removeStack(INGREDIENT_SLOT_1, 1);
        this.removeStack(INGREDIENT_SLOT_2, 1);
        this.removeStack(INGREDIENT_SLOT_3, 1);

        if (this.getStack(OUTPUT_SLOT).isEmpty()) {
            this.setStack(OUTPUT_SLOT, result);
        } else {
            this.getStack(OUTPUT_SLOT).increment(result.getCount());
        }
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<GiardinieraAltarRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty() || this.world == null) {
            return false;
        }

        ItemStack result = recipe.get().getOutput(this.world.getRegistryManager());
        return canInsertAmountIntoOutputSlot(result)
                && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<GiardinieraAltarRecipe> getCurrentRecipe() {
        if (this.world == null) {
            return Optional.empty();
        }

        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }

        return this.world.getRecipeManager().getFirstMatch(
                GiardinieraAltarRecipe.Type.INSTANCE,
                inv,
                this.world
        );
    }


    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).isEmpty()
                || this.getStack(OUTPUT_SLOT).getItem() == item;
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <=
                getStack(OUTPUT_SLOT).getMaxCount();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
