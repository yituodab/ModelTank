package com.model.tank.entities.tank;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TankRender extends GeoEntityRenderer<TankEntity> {
    public TankRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TankModel());
    }
}
