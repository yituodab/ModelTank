package com.model.tank.entities;

import com.model.tank.api.client.interfaces.ITargetEntity;
import com.model.tank.events.CannonballHitEntityEvent;
import com.model.tank.init.ModDamageTypes;
import com.model.tank.resource.DataLoader;
import com.model.tank.resource.data.Module;
import com.model.tank.resource.data.tank.CannonballData;
import com.model.tank.utils.CannonballType;
import com.model.tank.utils.EntityHelper;
import com.model.tank.utils.ExplodeHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            if(blockHitResult.getType() != HitResult.Type.MISS) {
                endPos = blockHitResult.getLocation();
            }
            List<EntityHitResult> onHitEntities = EntityHelper.findEntitiesOnPath(this, startPos, endPos);
            if(!onHitEntities.isEmpty()){
                for(EntityHitResult entityHitResult : onHitEntities) {
                    this.onHitEntity(entityHitResult);
                    if(this.isRemoved()){
                        return;
                    }
                }
                this.discard();
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
        Vec3 hitPos = pResult.getLocation();
        DamageSource damageSource = ModDamageTypes.create(this.level(), ModDamageTypes.Type.CANNONBALL, this, this.getOwner());
        float damage = this.entityDamage;
        if(entity instanceof ITargetEntity iTargetEntity){
            // 发布事件
            CannonballHitEntityEvent.TargetEntity event = new CannonballHitEntityEvent.TargetEntity(this, entity, hitPos, damageSource, damage);
            if(MinecraftForge.EVENT_BUS.post(event)){
                return;
            }
            boolean discard = iTargetEntity.onCannonballHit(this, new EntityHitResult(event.getEntity(), event.getHitPos()), event.getSource(), event.getDamage());
            if(discard){
                return;
            }
            return;
        }
        // 发布事件
        CannonballHitEntityEvent event = new CannonballHitEntityEvent(this, entity, hitPos, damageSource, damage);
        if(MinecraftForge.EVENT_BUS.post(event)){
            return;
        }
        // 更新变量
        entity = event.getEntity();
        hitPos = event.getHitPos();
        damageSource = event.getSource();
        damage = event.getDamage();
        if(entity == null){
            return;
        }
        // 造成伤害
        entity.hurt(damageSource, damage);
        switch (this.type) {
            case HE,HEAT,HESH -> {
                ExplodeHelper.createExplode(Objects.requireNonNull(this.getOwner()), this.TNTmass, hitPos);
                this.discard();
            }
        }
    }
    protected void onHitTankEntity(Vec3 startPos,Vec3 endPos, TankEntity tank){
        Module.Armor onHitArmor = null;
        Vec3 onHitPos = null;
        for(Module.Armor armor : tank.getArmors()){
            Optional<Vec3> vec3 = armor.getHitBox().clip(startPos,endPos);
            if(vec3.isPresent()){
                if((onHitArmor == null && onHitPos == null) || onHitPos.distanceTo(startPos) > vec3.get().distanceTo(startPos)){
                    onHitArmor = armor;
                    onHitPos = vec3.get();
                }
            }
        }
        if(onHitArmor == null)return;
        tank.discard();
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
        fromCannonballData(DataLoader.getCannonballData(id));
    }
}
