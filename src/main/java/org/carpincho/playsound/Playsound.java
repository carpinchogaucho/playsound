package org.carpincho.playsound;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.carpincho.playsound.command.PlaySoundCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Playsound implements ModInitializer {
    public static final String MOD_ID = "Playsound";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            PlaySoundCommand.register(dispatcher);
        });




        LOGGER.info("¡El mod {} se ha inicializado correctamente!", MOD_ID);
    }
}
