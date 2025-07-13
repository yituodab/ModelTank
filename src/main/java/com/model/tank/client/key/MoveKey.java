package com.model.tank.client.key;

import com.model.tank.entities.TankEntity;
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

import static com.model.tank.ModularTank.IsInGame;
import static com.model.tank.client.key.KeyRegister.MODULAR_TANK_CATEGORY;
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class MoveKey {
    public static final KeyMapping UP_KEY = new KeyMapping("key.modulartank.move.up", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W,MODULAR_TANK_CATEGORY);
    public static final KeyMapping DOWN_KEY = new KeyMapping("key.modulartank.move.down", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_S,MODULAR_TANK_CATEGORY);
    public static final KeyMapping LEFT_KEY = new KeyMapping("key.modulartank.move.left", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_A,MODULAR_TANK_CATEGORY);
    public static final KeyMapping RIGHT_KEY = new KeyMapping("key.modulartank.move.right", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_D,MODULAR_TANK_CATEGORY);
    @SubscribeEvent
    public static void onMovePress(InputEvent.Key event) {
        if (IsInGame) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null || player.isSpectator()) {
                return;
            }
            if (!(player.getVehicle() instanceof TankEntity)) {
                return;
            }
            boolean up = UP_KEY.isDown();
            boolean down = DOWN_KEY.isDown();
            boolean left = LEFT_KEY.isDown();
            boolean right = RIGHT_KEY.isDown();
            ((TankEntity) player.getVehicle()).setInput(up,down,left,right);
        }
    }
}
