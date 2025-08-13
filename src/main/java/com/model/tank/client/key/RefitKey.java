package com.model.tank.client.key;

import com.model.tank.client.gui.TankRefitGui;
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
public class RefitKey {
    public static final KeyMapping REFIT_KEY = new KeyMapping("key.mrt.refit", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_KEY_Z,MODULAR_TANK_CATEGORY);
    @SubscribeEvent
    public static void onShootPress(InputEvent.MouseButton.Post event){
        if (IsInGame && event.getAction() == GLFW.GLFW_PRESS && REFIT_KEY.matchesMouse(event.getButton())){
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null || player.isSpectator()) {
                return;
            }
            if (!(player.getVehicle() instanceof TankEntity)) {
                return;
            }
            if (Minecraft.getInstance().screen instanceof TankRefitGui){
                Minecraft.getInstance().setScreen(null);
                return;
            }
            Minecraft.getInstance().setScreen(new TankRefitGui());
        }
    }
}
