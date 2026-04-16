package net.dman.thepicklejar.network;

import net.dman.thepicklejar.item.custom.EternalPickleItem;
import net.dman.thepicklejar.item.custom.EternalPickles;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

/**
 * Packet for activating eternal pickle abilities
 * Sent from client to server when ability key is pressed
 */
public class ActivateAbilityPacket implements FabricPacket {

    // Packet identifier
    public static final Identifier ID = new Identifier("thepicklejar", "activate_ability");
    public static final PacketType<ActivateAbilityPacket> TYPE = PacketType.create(ID, ActivateAbilityPacket::new);

    private final ItemStack stack;

    /**
     * Constructor for creating packet with ItemStack
     */
    public ActivateAbilityPacket(ItemStack stack) {
        this.stack = stack.copy();
    }

    /**
     * Constructor for reading packet from buffer
     */
    public ActivateAbilityPacket(PacketByteBuf buf) {
        this.stack = buf.readItemStack();
    }

    /**
     * Write packet to buffer
     */
    @Override
    public void write(PacketByteBuf buf) {
        buf.writeItemStack(stack);
    }

    /**
     * Get packet type
     */
    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    /**
     * Get the ItemStack from packet
     */
    public ItemStack getStack() {
        return stack;
    }

    public void send() {
        ClientPlayNetworking.send(this);
    }

    /**
     * Register packet handlers
     * Call this from your main mod class
     */
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (packet, player, responseSender) -> {
            // Execute on server thread
            player.getServer().execute(() -> {
                ItemStack stack = packet.getStack();

                // Check if item is an eternal pickle
                if (stack.getItem() instanceof EternalPickleItem pickle) {
                    // Trigger ability
                    EternalPickles.triggerAbilityForItem(
                            player.getWorld(),
                            player,
                            stack,
                            pickle
                    );
                }
            });
        });
    }
}
