package com.model.tank.api.client.interfaces;

import com.model.tank.entities.CannonballEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;

public interface ITargetEntity {
    boolean onCannonballHit(CannonballEntity cannonball, EntityHitResult result, DamageSource source, float damage);
}
