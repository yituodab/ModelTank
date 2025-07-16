package com.model.tank.client.gui;

import com.model.tank.ModularTank;
import com.model.tank.api.client.interfaces.ILocalPlayer;
import com.model.tank.entities.TankEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Map;

public class TankHUD implements IGuiOverlay {
    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float v, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null && player.getVehicle() instanceof TankEntity tank) {
            int cannonballTypeNumber = tank.getCannonballs().size();
            int renderStartX = width / 2 - (cannonballTypeNumber * 16);//cannonballTypeNumber/2*32
            int renderStartY = height - 32;
            int number = 0;
            guiGraphics.setColor(1, 1, 1, 1);
            // 载具当前信息
            guiGraphics.drawString(minecraft.font,
                    Component.translatable("mrt.hud.currentSpeed").append((int)(tank.getCurrentSpeed()*72)+" km/h"),
                    4, height - 12, 0xFFFFFF, false);
            // 瞄具HUD
            if(((ILocalPlayer)player).isAim())
                guiGraphics.blit(new ResourceLocation(ModularTank.MODID, "textures/hud/aim.png"),
                    0,0,0,0,width,height,width,height);
            // 载具当前携带炮弹
            for (Map.Entry<TankEntity.Cannonball, Integer> entry : tank.getCannonballs().entrySet()) {
                TankEntity.Cannonball cannonball = entry.getKey();
                Integer i = entry.getValue();
                guiGraphics.blit(new ResourceLocation(ModularTank.MODID, "textures/hud/cannonball_icons/" +
                                cannonball.data().type.toString().toLowerCase() + ".png"),
                        renderStartX + number * 32, renderStartY, 0, 0, 32, 32, 32, 32);
                guiGraphics.drawString(minecraft.font, i.toString(), renderStartX + number * 32 + 28, height - 9, 0xFFFFFF, false);
                number += 1;
            }
        }
    }
}
