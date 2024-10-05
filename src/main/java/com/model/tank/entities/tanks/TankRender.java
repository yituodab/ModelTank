package com.model.tank.entities.tanks;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TankRender extends GeoEntityRenderer<TankEntity> {
    public TankRender(EntityRendererProvider.Context renderManager, TankModel model) {
        super(renderManager, model);
    }
}
