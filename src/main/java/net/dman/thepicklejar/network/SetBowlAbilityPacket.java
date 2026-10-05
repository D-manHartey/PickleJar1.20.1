package net.dman.thepicklejar.network;

import net.dman.thepicklejar.component.ModComponents;
import net.dman.thepicklejar.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public final class SetBowlAbilityPacket implements FabricPacket {
    public static final Identifier ID = new Identifier("the-pickle-jar", "set_bowl_ability");
    public static final PacketType<SetBowlAbilityPacket> TYPE = PacketType.create
            (ID, SetBowlAbilityPacket::new);

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

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(TYPE,
                (packet, player, responseSender) ->
            player.getServer().execute(() -> {
                boolean holdingBowl = player.getMainHandStack().isOf(ModItems.ETERNAL_PICKLE_BOWL)
                        || player.getOffHandStack().isOf(ModItems.ETERNAL_PICKLE_BOWL);

                if (holdingBowl && packet.abilityIndex >= 0 && packet.abilityIndex < 6) {
                    ModComponents.PICKLE_POWER.get(player)
                            .setSelectedAbility(packet.abilityIndex);
                }
        }));
    }
}