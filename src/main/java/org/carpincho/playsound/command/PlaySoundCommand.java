package org.carpincho.playsound.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;



public class PlaySoundCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("customplaysound")
                        .then(CommandManager.argument("sound", IdentifierArgumentType.identifier())
                                .suggests((context, builder) -> {
                                    // Sugerencias de sonidos disponibles
                                    for (Identifier soundId : Registries.SOUND_EVENT.getIds()) {
                                        builder.suggest(soundId.toString());
                                    }
                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    // Obtener el ID del sonido desde los argumentos
                                    Identifier soundId = IdentifierArgumentType.getIdentifier(context, "sound");
                                    SoundEvent soundEvent = Registries.SOUND_EVENT.get(soundId);

                                    if (soundEvent != null) {
                                        // Construir el comando vanilla /playsound
                                        String command = String.format(
                                                "playsound %s master @a", // Reproduce el sonido globalmente
                                                soundId.toString()
                                        );

                                        // Ejecutar el comando en el servidor
                                        ServerCommandSource source = context.getSource();
                                        try {
                                            // Analizar el comando usando StringReader
                                            StringReader reader = new StringReader(command);
                                            source.getServer().getCommandManager().getDispatcher().execute(reader, source);

                                            // Activar la animación del HUD



                                            // Enviar retroalimentación al jugador
                                            source.sendFeedback(() -> Text.of("Playing sound: " + soundId), true);
                                            return 1; // Éxito
                                        } catch (Exception e) {
                                            // Enviar error si ocurre una excepción
                                            source.sendError(Text.of("Error executing playsound: " + e.getMessage()));
                                            return 0; // Error
                                        }
                                    } else {
                                        // Mensaje si el sonido no es válido
                                        context.getSource().sendError(Text.of("Invalid sound ID: " + soundId));
                                        return 0; // Error
                                    }
                                }))
        );
    }
}