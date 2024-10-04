package com.model.tank.entities;

import com.model.tank.entities.tanks.TankEntity;
import com.model.tank.utils.HitBox;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

public class Cannonball extends Projectile {
    public HitBox hitBox = new HitBox(this.position(),0.1,0.1,0.1);
    protected Cannonball(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    private static final EntityDataAccessor<Integer> COUNTER = SynchedEntityData.defineId(Cannonball.class, EntityDataSerializers.INT);
    @Override
    protected void defineSynchedData() {
        this.entityData.define(COUNTER, 0);
    }

    @Override
    public void tick() {
        if(!this.level().isClientSide){
            for(Entity entity : this.level().getEntities(this, new AABB(this.position().add(20,20,20),
                    this.position().add(20,20,20)))){
                if(entity instanceof TankEntity tank){

                }
            }
        }
        super.tick();
    }

    @Override
    protected void onHit(HitResult p_37260_) {
        super.onHit(p_37260_);
    }
}
