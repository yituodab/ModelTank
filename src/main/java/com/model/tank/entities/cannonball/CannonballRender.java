package com.model.tank.entities.cannonball;

import com.model.tank.entities.tank.TankEntity;
import com.model.tank.entities.tank.TankModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CannonballRender extends GeoEntityRenderer<CannonballEntity> {
    public CannonballRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CannonballModel());
    }
}
