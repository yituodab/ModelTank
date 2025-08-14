package com.model.tank.client.gui;

import com.model.tank.ModularTank;
import com.model.tank.entities.TankEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TankRefitGui extends Screen {
    public static final ResourceLocation REFIT_GUI_TEXTURE_LOCATION = new ResourceLocation(ModularTank.MODID, "textures/gui/tank_refit_gui.png");

    private final int imageWidth = 338;
    private final int imageHeight = 256;

    public TankRefitGui() {
        super(Component.literal("Tank Refit Gui"));
    }


    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int leftPos = (width - imageWidth) / 2;
        int topPos = (height - imageHeight) / 2;
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(REFIT_GUI_TEXTURE_LOCATION, leftPos, topPos, 0, 0, 196, 256);
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() instanceof TankEntity tankEntity) {
            pGuiGraphics.drawString(font, Component.translatable(tankEntity.getTankID().toString()), 5, 82, 0, false);
        }
    }
}
