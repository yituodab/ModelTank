package com.model.tank.entities.tanks;

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


public class TankEntity extends Entity implements GeoEntity {
    //public static List<Tank> tanks = new ArrayList<Tank>();
    public ResourceLocation modelLocation;
    public ResourceLocation textureLocation;
    public List<Module> modules = new ArrayList<>();
    public double maxSpeed;// m/s
    private double tickSpeed = maxSpeed / 20;
    public TankEntity(EntityType<?> p_19870_, Level p_19871_, Tank tank) {
        super(p_19870_, p_19871_);
        for(Module m : tank.modules){
            modules.add(m.copy());
        }
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

    @Override
    protected boolean canRide(Entity p_20339_) {
        return true;
    }

    @Override
    protected void defineSynchedData() {}
    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}
    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }
}
