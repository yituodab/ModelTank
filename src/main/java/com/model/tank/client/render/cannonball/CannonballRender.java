package com.model.tank.client.render.cannonball;

import com.model.tank.entities.CannonballEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CannonballRender extends GeoEntityRenderer<CannonballEntity> {
    public CannonballRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CannonballModel());
    }
}
