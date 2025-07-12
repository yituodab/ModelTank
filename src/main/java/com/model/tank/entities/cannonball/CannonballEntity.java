package com.model.tank.entities.cannonball;

import com.model.tank.entities.tank.TankEntity;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.CannonballData;
import com.model.tank.utils.CannonballType;
import com.model.tank.utils.ExplodeHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CannonballEntity extends Projectile implements GeoEntity, IEntityAdditionalSpawnData {
    public CannonballEntity(EntityType<? extends Projectile> entityType, Level level, TankEntity owner, CannonballData data, ResourceLocation id){
        super(entityType, level);
        this.setOwner(owner);
        this.id = id;
        fromCannonballData(data);
    }
    public CannonballEntity(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    public float entityDamage = 20;
    public CannonballType type;
    private ResourceLocation id;
    private int life = 100;
    public float TNTmass;
    public double speed;

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.level().isClientSide){
            Vec3 startPos = this.position();
            Vec3 endPos = this.position().add(this.getDeltaMovement());
            BlockHitResult blockHitResult = this.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this));
            if(blockHitResult.getType() != HitResult.Type.MISS)endPos = blockHitResult.getLocation();
            AABB aabb = this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0);
            List<Entity> onHitEntities = new ArrayList<>();
            // 对实体进行命中判定
            for(Entity entity : this.level().getEntities(this, aabb)) {
                if (entity.getBoundingBox().clip(startPos, endPos).orElse(null) != null) {
                    onHitEntities.add(entity);
                }
            }
            if(!onHitEntities.isEmpty()){
                TankEntity lastTank = null;
                for(Entity entity : onHitEntities) {
                    if(lastTank != null && distanceTo(lastTank) < distanceTo(entity)){
                        continue;
                    }
                    if(entity instanceof TankEntity tank){
                        lastTank = tank;
                        onHitTankEntity(startPos,endPos,tank);
                        continue;
                    }
                    this.onHitEntity(new EntityHitResult(entity));
                }
                discard();
                return;
            }
            this.onHitBlock(blockHitResult);
            if (this.tickCount >= this.life - 1) {
                this.discard();
            }
        }
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
            livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), this.entityDamage);
            switch (this.type){
                case HE,HEAT,HESH -> ExplodeHelper.createExplode(Objects.requireNonNull(this.getOwner()), this.TNTmass, this.position());
            }
        }
    }
    protected void onHitTankEntity(Vec3 startPos,Vec3 endPos, TankEntity tank){
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(pResult.getType() == HitResult.Type.MISS)return;
        super.onHitBlock(pResult);
        switch (this.type){
            case HE,HEAT,HESH -> ExplodeHelper.createExplode(Objects.requireNonNull(this.getOwner()), this.TNTmass, this.position());
            case AP,APBC,APC,APCBC,APCR,APDS,APFSDS -> this.level().destroyBlock(pResult.getBlockPos(),true,this.getOwner());
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
    public AnimatableInstanceCache getAnimatableInstanceCache() {return GeckoLibUtil.createInstanceCache(this);}

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeResourceLocation(id);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf friendlyByteBuf) {
        this.id = friendlyByteBuf.readResourceLocation();
        fromCannonballData(DataManager.CANNONBALLS.get(id));
    }
}
