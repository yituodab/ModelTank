package com.model.tank.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.model.tank.ModelTank.IsInGame;
import static com.model.tank.client.key.KeyRegister.MODEL_TANK_CATEGORY;
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class MoveKey {
    public static final KeyMapping W_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W,"key.modeltank.move.w");
    public static final KeyMapping S_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_S,"key.modeltank.move.s");
    public static final KeyMapping A_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_A,"key.modeltank.move.a");
    public static final KeyMapping D_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_D,"key.modeltank.move.d");
    @SubscribeEvent
    public static void onAimPress(InputEvent.MouseButton.Post event) {
        if (IsInGame) {
            if(W_KEY.matchesMouse(event.getButton())) {

            }
        }
    }
}
