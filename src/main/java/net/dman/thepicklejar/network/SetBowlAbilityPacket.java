package net.dman.thepicklejar.network;

import net.dman.thepicklejar.util.PlayerAbilityManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SetBowlAbilityPacket implements FabricPacket {
    public static final Identifier ID = new Identifier("thepicklejar", "set_bowl_ability");
    public static final PacketType<SetBowlAbilityPacket> TYPE = PacketType.create(ID,
            SetBowlAbilityPacket::new);

    private final int abilityIndex;

    public SetBowlAbilityPacket(int abilityIndex) {
        this.abilityIndex = abilityIndex;
    }

    public SetBowlAbilityPacket(PacketByteBuf buf) {
        this.abilityIndex = buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(abilityIndex);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void send() {
        ClientPlayNetworking.send(this);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (packet, player, responseSender) -> {
            player.getServer().execute(() -> PlayerAbilityManager.setSelectedAbility(player,
                    packet.abilityIndex));
        });
    }
}
