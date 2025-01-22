package com.model.tank.client.key;

import com.model.tank.api.client.interfaces.ILocalPlayer;
import com.model.tank.utils.MathUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.model.tank.ModularTank.IsInGame;
import static com.model.tank.client.key.KeyRegister.MODEL_TANK_CATEGORY;
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class AimKey {
    private static double defaultFOV;
    public static final KeyMapping AIM_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT,"key.modeltank.aim");
    @SubscribeEvent
    public static void aim(InputEvent.MouseButton.Post event){
        if (IsInGame && AIM_KEY.matchesMouse(event.getButton())){
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null || player.isSpectator()) {
                return;
            }
            //if (!(player.getVehicle() instanceof TankEntity)) {
            //    return;
            //}
            if (player instanceof ILocalPlayer iLocalPlayer) {
                if (event.getAction() == GLFW.GLFW_PRESS) {
                    ((ILocalPlayer)player).aim(true);
                }
                if (event.getAction() == GLFW.GLFW_RELEASE) {
                    ((ILocalPlayer)player).aim(false);
                }
            }
        }
    }
    @SubscribeEvent
    public static void atAim(ViewportEvent.ComputeFov event){
        if (!event.usedConfiguredFov()) {
            return; // 只修改世界渲染的 fov，因此如果是手部渲染 fov 事件，则返回
        }
        Entity entity = event.getCamera().getEntity();
        if(entity instanceof LocalPlayer player){
            if(((ILocalPlayer)player).isAim()){
                defaultFOV = event.getFOV();
                event.setFOV(MathUtils.magnificationToFov(3,event.getFOV()));
            }
            else event.setFOV(defaultFOV);
        }
    }
}
