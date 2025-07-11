package com.model.tank.mixins;

import com.model.tank.api.client.interfaces.IEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class EntityMixin implements IEntity {
    @Shadow
    private EntityDimensions dimensions;

    @Override
    public void setDimensions(EntityDimensions dimensions) {
        this.dimensions = dimensions;
    }
    @Override
    public EntityDimensions getDimensions() {
        return this.dimensions;
    }

}
