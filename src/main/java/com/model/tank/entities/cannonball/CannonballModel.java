package com.model.tank.entities.cannonball;

import com.model.tank.ModularTank;
import com.model.tank.entities.tank.TankEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CannonballModel extends GeoModel<CannonballEntity> {

    @Override
    public ResourceLocation getModelResource(CannonballEntity cannonballEntity) {
        return new ResourceLocation(ModularTank.MODID, "geo/cannonballs/"+cannonballEntity.type.toString().toLowerCase()+".json");
    }

    @Override
    public ResourceLocation getTextureResource(CannonballEntity cannonballEntity) {
        return new ResourceLocation(ModularTank.MODID,"textures/cannonballs/"+cannonballEntity.type.toString().toLowerCase()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(CannonballEntity cannonballEntity) {
        return null;
    }
}
