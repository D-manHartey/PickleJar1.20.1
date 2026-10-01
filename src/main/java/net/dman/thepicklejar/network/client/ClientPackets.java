package net.dman.thepicklejar.network.client;

import net.dman.thepicklejar.network.ActivateAbilityPacket;
import net.dman.thepicklejar.network.SetBowlAbilityPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Hand;

public final class ClientPackets {
    private ClientPackets() {
    }

    public static void sendAbilityActivation(Hand hand) {
        ClientPlayNetworking.send(new ActivateAbilityPacket(hand));
    }

    public static void sendBowlAbilitySelection(int abilityIndex) {
        ClientPlayNetworking.send(new SetBowlAbilityPacket(abilityIndex));
    }
}
