package com.model.tank.entities.tanks;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TankModel extends GeoModel<TankEntity> {
    @Override
    public ResourceLocation getModelResource(TankEntity tankEntity) {
        return tankEntity.tank.modelLocation;
    }

    @Override
    public ResourceLocation getTextureResource(TankEntity tankEntity) {
        return tankEntity.tank.textureLocation;
    }

    @Override
    public ResourceLocation getAnimationResource(TankEntity tankEntity) {
        return null;
    }
}
