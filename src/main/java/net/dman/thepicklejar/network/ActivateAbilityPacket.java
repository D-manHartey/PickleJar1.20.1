package net.dman.thepicklejar.network;

import net.dman.thepicklejar.item.ModItems;
import net.dman.thepicklejar.item.custom.EternalPickleItem;
import net.dman.thepicklejar.item.custom.EternalPickles;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

/**
 * Packet for activating eternal pickle abilities
 * Sent from client to server when ability key is pressed
 */
public final class ActivateAbilityPacket implements FabricPacket {

    public static final Identifier ID = new Identifier("the-pickle-jar", "activate_ability");
    public static final PacketType<ActivateAbilityPacket> TYPE = PacketType.create(ID, ActivateAbilityPacket::new);

    private final Hand hand;

    public ActivateAbilityPacket(Hand hand) {
        this.hand = hand;
    }

    public ActivateAbilityPacket(PacketByteBuf buf) {
        this.hand = buf.readBoolean() ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(hand == Hand.OFF_HAND);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(TYPE,
                (packet, player, responseSender) -> {
            player.getServer().execute(() -> {
                ItemStack stack = player.getStackInHand(packet.hand);

                if (stack.getItem() instanceof EternalPickleItem
                        || stack.isOf(ModItems.ETERNAL_PICKLE_BOWL)) {
                    EternalPickles.triggerAbilityForItem(stack, player);
                }
            });
        });
    }
}