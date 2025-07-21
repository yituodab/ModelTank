package com.model.tank.client.key;

import com.model.tank.api.client.interfaces.ILocalPlayer;
import com.model.tank.entities.TankEntity;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

import static com.model.tank.ModularTank.IsInGame;
import static com.model.tank.client.key.KeyRegister.MODULAR_TANK_CATEGORY;
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ChangeCurrentCannonballKey {
    public static final KeyMapping KEY_1 = new KeyMapping("key.mrt.1", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_1,MODULAR_TANK_CATEGORY);
    public static final KeyMapping KEY_2 = new KeyMapping("key.mrt.2", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_2,MODULAR_TANK_CATEGORY);
    public static final KeyMapping KEY_3 = new KeyMapping("key.mrt.3", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_3,MODULAR_TANK_CATEGORY);
    public static final KeyMapping KEY_4 = new KeyMapping("key.mrt.4", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_4,MODULAR_TANK_CATEGORY);
    public static final KeyMapping KEY_5 = new KeyMapping("key.mrt.5", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_5,MODULAR_TANK_CATEGORY);
    public static final KeyMapping KEY_6 = new KeyMapping("key.mrt.6", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_6,MODULAR_TANK_CATEGORY);
    public static final KeyMapping KEY_7 = new KeyMapping("key.mrt.7", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_7,MODULAR_TANK_CATEGORY);
    public static final KeyMapping KEY_8 = new KeyMapping("key.mrt.8", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_8,MODULAR_TANK_CATEGORY);
    public static final KeyMapping KEY_9 = new KeyMapping("key.mrt.9", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_9,MODULAR_TANK_CATEGORY);
    public static final KeyMapping[] KEY_MAPPINGS = {KEY_1,KEY_2,KEY_3,KEY_4,KEY_5,KEY_6,KEY_7,KEY_8,KEY_9};
    @SubscribeEvent
    public static void onChangeCurrentCannonball(InputEvent.Key event){
        if (IsInGame){
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null || player.isSpectator()) {
                return;
            }
            if (player.getVehicle() instanceof TankEntity tank) {
                int i = 0;
                for (ResourceLocation id : tank.getCannonballs().keySet()) {
                    if (KEY_MAPPINGS[i].isDown()) {
                        tank.setCurrentCannonball(id);
                    }
                    i++;
                    if(i==10)return;
                }
            }
        }
    }
}
