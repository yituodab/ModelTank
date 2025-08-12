package com.model.tank.client.gui;

import com.model.tank.ModularTank;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class TankRefitGui extends AbstractContainerScreen<TankRefitMenu> {
    public static final ResourceLocation REFIT_GUI_TEXTURE_LOCATION = new ResourceLocation(ModularTank.MODID, "textures/gui/tank_refit_gui.png");
    public TankRefitGui(TankRefitMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 338;
        this.imageHeight = 256;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        renderBackground(guiGraphics);
        guiGraphics.blit(REFIT_GUI_TEXTURE_LOCATION, leftPos, topPos, 0, 0, 196, 256);
    }
}
