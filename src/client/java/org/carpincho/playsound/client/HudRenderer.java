package org.carpincho.playsound.client;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class HudRenderer {
    private static final Identifier[] FRAMES = new Identifier[]{
            new Identifier("playsound", "textures/gui/hud_frame1.png"),
            new Identifier("playsound", "textures/gui/hud_frame2.png"),
            new Identifier("playsound", "textures/gui/hud_frame3.png")

    };
    private static final int FRAME_DURATION = 4;
    private static int ticks = 0;
    private static boolean active = false;


    public static void registerHud() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (active) {
                drawAnimation(drawContext);
                ticks++;
                if (ticks / FRAME_DURATION >= FRAMES.length) {
                    active = false;
                    ticks = 0;
                }
            }
        });
    }


    public static void activateAnimation() {
        active = true;
        ticks = 0;
    }


    private static void drawAnimation(DrawContext drawContext) {
        int frameIndex = (ticks / FRAME_DURATION) % FRAMES.length;
        Identifier currentFrame = FRAMES[frameIndex];
        int x = MinecraftClient.getInstance().getWindow().getWidth() - 150;
        int y = 10;
        drawContext.drawTexture(currentFrame, x, y, 0, 0, 128, 128); // Tamaño del cuadro
    }


    public static void registerCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("customplaysound").executes(context -> {
                activateAnimation();
                return 1;
            }));
        });
    }
}