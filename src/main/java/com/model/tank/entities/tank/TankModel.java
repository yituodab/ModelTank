package com.model.tank.entities.tank;

import com.model.tank.ModularTank;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TankModel extends GeoModel<TankEntity> {
    @Override
    public ResourceLocation getModelResource(TankEntity tank) {
        return tank.getModelLocation() != null ? tank.getModelLocation() : new ResourceLocation(ModularTank.MODID, "geo/model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TankEntity tank) {
        return tank.getTextureLocation() != null ? tank.getTextureLocation() : new ResourceLocation(ModularTank.MODID, "textures/texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TankEntity tankEntity) {return null;// tankEntity.getTank().modelLocation;
    }
}
