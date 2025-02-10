package com.model.tank.entities.cannonball;

import com.model.tank.entities.tank.TankEntity;
import com.model.tank.resource.data.CannonballData;
import com.model.tank.utils.CannonballType;
import com.model.tank.utils.ExplodeHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CannonballEntity extends Projectile implements GeoEntity {
    public CannonballEntity(EntityType<? extends Projectile> entityType, Level level, TankEntity owner, CannonballData data){
        super(entityType, level);
        this.setOwner(owner);
        fromCannonballData(data);
    }
    public CannonballEntity(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    private static final EntityDataAccessor<Integer> COUNTER = SynchedEntityData.defineId(CannonballEntity.class, EntityDataSerializers.INT);
    public float entityDamage = 20;
    public CannonballType type;
    private int life = 100;
    public float TNTmass;
    public double speed;

    @Override
    protected void defineSynchedData() {
        this.entityData.define(COUNTER, 0);
    }

    @Override
    public void tick() {
        if(!this.level().isClientSide){
            Vec3 endPos = this.position().add(this.getDeltaMovement());
            BlockHitResult blockHitResult = this.level().clip(new ClipContext(this.position(), endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this));
            if(blockHitResult.getType() != HitResult.Type.MISS)endPos = blockHitResult.getLocation();
            for (TankEntity tankEntity : this.level().getEntitiesOfClass(
                    TankEntity.class,
                    new AABB(this.position().add(20, 20, 20),
                            this.position().add(20, 20, 20)))) {
                this.onHitTankEntity(this.position(), endPos, tankEntity);
            }
            AABB aabb = this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0);
            Entity lastEntity = null;
            for(Entity entity : this.level().getEntities(this, aabb)) {
                if (entity.getBoundingBox().clip(this.position(), endPos).orElse(null) != null) {
                    if (lastEntity == null || lastEntity.position().distanceTo(this.position()) > entity .position().distanceTo(this.position()))
                        lastEntity = entity;
                }
            }
            if(lastEntity != null)this.onHitEntity(new EntityHitResult(lastEntity));
            this.onHitBlock(blockHitResult);
            if (this.tickCount >= this.life - 1) {
                this.discard();
            }
        }
        super.tick();
    }

    public void fromCannonballData(CannonballData data){
        this.entityDamage = data.entityDamage;
        this.type = data.type;
        this.speed = data.speed;
        this.life = data.life;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if(entity instanceof LivingEntity livingEntity){
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), this.entityDamage);
            switch (this.type){
                case HE,HEAT,HESH -> ExplodeHelper.createExplode(this.getOwner(), this.TNTmass, this.position());
            }
        }
        discard();
    }
    protected void onHitTankEntity(Vec3 startPos,Vec3 endPos, TankEntity tank){
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(pResult.getType() == HitResult.Type.MISS)return;
        super.onHitBlock(pResult);
        switch (this.type){
            case HE,HEAT,HESH -> ExplodeHelper.createExplode(this.getOwner(), this.TNTmass, this.position());
        }
        discard();
    }

    public void shoot(Entity Shooter, float XRot, float YRot) {
        super.shootFromRotation(Shooter, XRot, YRot, 0, (float)speed/20, 0);
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }

}
