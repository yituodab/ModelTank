package com.model.tank.entities.tanks;

import com.model.tank.utils.Tank;
import com.model.tank.utils.TankRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.commands.SummonCommand;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;


public class TankEntity extends Entity implements GeoEntity {
    //public static List<Tank> tanks = new ArrayList<Tank>();
    private Tank tank;
    public TankEntity(EntityType<?> p_19870_, Level p_19871_, Tank tank) {
        super(p_19870_, p_19871_);
        this.tank = tank;
    }
    public TankEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        this.tank = TankRegister.TANKS.get("M1A2");
    }

    @Override
    public void load(CompoundTag p_20259_) {
        super.load(p_20259_);
        //this.tank = TankRegister.TANKS.get(p_20259_.getString("tank"));
    }

    public Tank getTank() {
        return tank;
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
        return null;
    }
}
