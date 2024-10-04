package com.model.tank.entities.tanks;

import com.model.tank.utils.Tank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class TankEntity extends Entity {
    //public static List<Tank> tanks = new ArrayList<Tank>();
    public final Tank tank;
    public TankEntity(EntityType<?> p_19870_, Level p_19871_, Tank tank) {
        super(p_19870_, p_19871_);
        this.tank = tank;
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
}
