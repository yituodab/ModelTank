package com.model.tank.api.client.interfaces;

import com.model.tank.entities.CannonballEntity;
import com.model.tank.utils.MRTEntityHitResult;
import net.minecraft.world.damagesource.DamageSource;

public interface ITargetEntity {
    boolean onCannonballHit(CannonballEntity cannonball, MRTEntityHitResult result, DamageSource source, float damage);
}
