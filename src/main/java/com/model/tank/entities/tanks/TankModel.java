package com.model.tank.entities.tanks;

import com.model.tank.ModelTank;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TankModel extends GeoModel<TankEntity> {
    @Override
    public ResourceLocation getModelResource(TankEntity tankEntity) {
        return tankEntity.getTank().modelLocation;
    }

    @Override
    public ResourceLocation getTextureResource(TankEntity tankEntity) {
        return tankEntity.getTank().textureLocation;
    }

    @Override
    public ResourceLocation getAnimationResource(TankEntity tankEntity) {return null;// tankEntity.getTank().modelLocation;
    }
}
