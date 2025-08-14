package com.model.tank.api.client.interfaces;

import net.minecraft.world.entity.EntityDimensions;

public interface IEntity {

    void setDimensions(EntityDimensions dimensions);

    EntityDimensions getDimensions();
}
