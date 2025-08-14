package com.model.tank.events;

import com.model.tank.entities.CannonballEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;

public class CannonballHitEntityEvent extends Event {
    private final CannonballEntity cannonball;
    private final Vec3 hitPos;
    protected Entity entity;
    protected DamageSource source;
    protected float damage;
    public CannonballHitEntityEvent(CannonballEntity cannonball, Entity entity, Vec3 hitPos, DamageSource source, float damage) {
        this.cannonball = cannonball;
        this.hitPos = hitPos;
        this.entity = entity;
        this.source = source;
        this.damage = damage;
    }

    public CannonballEntity getCannonball() {
        return cannonball;
    }

    public Vec3 getHitPos() {
        return hitPos;
    }

    public Entity getEntity() {
        return entity;
    }

    public DamageSource getSource() {
        return source;
    }

    public float getDamage() {
        return damage;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setSource(DamageSource source) {
        this.source = source;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public static class TargetEntity extends CannonballHitEntityEvent{
        public TargetEntity(CannonballEntity cannonball, Entity entity, Vec3 hitPos, DamageSource source, float damage) {
            super(cannonball, entity, hitPos, source, damage);
        }
    }
}
