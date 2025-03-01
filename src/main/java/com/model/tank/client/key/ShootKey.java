package com.model.tank.client.key;

import com.model.tank.api.client.interfaces.ILocalPlayer;
import com.model.tank.entities.tank.TankEntity;
import com.model.tank.network.C2S.ClientTankShoot;
import com.model.tank.network.NetWorkManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
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
public class ShootKey {
    public static final KeyMapping SHOOT_KEY = new KeyMapping("key.modeltank.shoot", KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_LEFT,MODULAR_TANK_CATEGORY);
    @SubscribeEvent
    public static void atShoot(InputEvent.MouseButton.Post event){
        if (IsInGame && SHOOT_KEY.matchesMouse(event.getButton())){
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null || player.isSpectator()) {
                return;
            }
            if (!(player.getVehicle() instanceof TankEntity)) {
                return;
            }
            if (player instanceof ILocalPlayer iLocalPlayer) {
                iLocalPlayer.shoot();
            }
        }
    }
    public static void shoot(TankEntity tank, TankEntity.Cannonball cannonball){
        if(cannonball != null){
            NetWorkManager.CHANNEL.sendToServer(new ClientTankShoot(tank.getId(), cannonball));
        }
    }
}
