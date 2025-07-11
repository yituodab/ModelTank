package com.model.tank.entities.tank;

import com.model.tank.api.client.entity.ModEntityRender;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class TankRender extends ModEntityRender {
    public TankRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TankModel());
    }
}
