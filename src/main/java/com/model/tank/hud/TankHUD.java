package com.model.tank.hud;

import com.model.tank.ModularTank;
import com.model.tank.entities.tank.TankEntity;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.Tank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Map;

public class TankHUD implements IGuiOverlay {
    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float v, int weight, int height) {
        if(Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle() instanceof TankEntity tank) {
            int cannonballTypeNumber = tank.getCannonballs().size();
            int renderStartX = weight / 2 - (cannonballTypeNumber*16);//cannonballTypeNumber/2*16
            int renderStartY = height;
            int number = 0;
            guiGraphics.setColor(1,1,1,1);
            for (Map.Entry<Tank.Cannonball, Integer> entry : tank.getCannonballs().entrySet()) {
                Tank.Cannonball c = entry.getKey();
                Integer i = entry.getValue();
                guiGraphics.blit(new ResourceLocation(ModularTank.MODID, "textures/cannonballs/" +
                                DataManager.CANNONBALLS.get(c.id).type.toString().toLowerCase() + ".png"),
                        renderStartX + number * 32, renderStartY, 0, 0, 32, 32, 32, 32);
                guiGraphics.drawString(Minecraft.getInstance().font, i.toString(), renderStartX + number * 32 + 28, renderStartY - 10, 0);
                number += 1;
            }
        }
    }
}
