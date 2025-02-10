package com.model.tank.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.model.tank.ModularTank.IsInGame;
import static com.model.tank.client.key.KeyRegister.MODULAR_TANK_CATEGORY;
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class MoveKey {
    public static final KeyMapping W_KEY = new KeyMapping("key.modulartank.move.w", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W,MODULAR_TANK_CATEGORY);
    public static final KeyMapping S_KEY = new KeyMapping("key.modulartank.move.s", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_S,MODULAR_TANK_CATEGORY);
    public static final KeyMapping A_KEY = new KeyMapping("key.modulartank.move.a", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_A,MODULAR_TANK_CATEGORY);
    public static final KeyMapping D_KEY = new KeyMapping("key.modulartank.move.d", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_D,MODULAR_TANK_CATEGORY);
    @SubscribeEvent
    public static void onAimPress(InputEvent.MouseButton.Post event) {
        if (IsInGame) {
            if(W_KEY.matchesMouse(event.getButton())) {

            }
        }
    }
}
