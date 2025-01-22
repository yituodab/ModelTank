package com.model.tank.entities.tanks;

import com.model.tank.api.entity.AbstractTank;
import com.model.tank.resource.data.Tank;
import com.model.tank.resource.data.Module;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;


public class TankEntity extends AbstractTank {
    private double tickSpeed = maxSpeed / 20;
    public TankEntity(EntityType<?> p_19870_, Level p_19871_, Tank tank) {
        super(p_19870_, p_19871_, tank);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public TankEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public void load(CompoundTag p_20259_) {
        super.load(p_20259_);
        //this.tank = EntityRegister.TANKS.get(p_20259_.getString("tank"));
    }

}
