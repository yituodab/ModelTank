package com.model.tank.entities.plane;

import com.model.tank.utils.Plane;
import com.model.tank.init.TankRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;


public class PlaneEntity extends Entity implements GeoEntity {
    //public static List<Tank> tanks = new ArrayList<Tank>();
    private Plane plane;
    public PlaneEntity(EntityType<?> p_19870_, Level p_19871_, Plane plane) {
        super(p_19870_, p_19871_);
        this.plane = plane;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public PlaneEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        this.plane = null;//TankRegister.TANKS.get("M1A2");
    }

    @Override
    public void load(CompoundTag p_20259_) {
        super.load(p_20259_);
        //this.tank = TankRegister.TANKS.get(p_20259_.getString("tank"));
    }

    public Plane getPlane() {
        return plane;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }
}
