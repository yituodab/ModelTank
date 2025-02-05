package com.model.tank.entities.tank;

import com.model.tank.ModularTank;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TankModel extends GeoModel<TankEntity> {
    @Override
    public ResourceLocation getModelResource(TankEntity tank) {
        return tank.textureLocation != null ? tank.textureLocation : new ResourceLocation(ModularTank.MODID, "textures/texture.png");
    }

    @Override
    public ResourceLocation getTextureResource(TankEntity tank) {
        return tank.modelLocation != null ? tank.modelLocation : new ResourceLocation(ModularTank.MODID, "geo/model.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(TankEntity tankEntity) {return null;// tankEntity.getTank().modelLocation;
    }
}
