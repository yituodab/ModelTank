package com.model.tank.api.client.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class ModEntity extends Entity implements GeoEntity {
    public ModEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
}
