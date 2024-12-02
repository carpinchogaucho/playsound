package org.carpincho.playsound.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

public class CustomSoundClient {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier("playsound", "custom_sound_packet"), (client, handler, buf, responseSender) -> {
            Identifier soundId = buf.readIdentifier();
            SoundEvent soundEvent = Registries.SOUND_EVENT.get(soundId);

            if (soundEvent != null) {
                client.execute(() -> {
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(soundEvent, 1.0F));
                });
            } else {
                System.out.println("Received invalid sound ID: " + soundId);
            }
        });
    }
}