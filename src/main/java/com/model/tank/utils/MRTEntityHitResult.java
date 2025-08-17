package com.model.tank.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class MRTEntityHitResult extends EntityHitResult {
    private final Vec3 start_pos;
    private final Vec3 end_pos;
    public MRTEntityHitResult(Entity pEntity, Vec3 hitPos, Vec3 startPos, Vec3 endPos) {
        super(pEntity, hitPos);
        start_pos = startPos;
        end_pos = endPos;
    }
    public MRTEntityHitResult(Entity entity, Vec3 startPos, Vec3 endPos){
        super(entity);
        start_pos = startPos;
        end_pos = endPos;
    }
    public MRTEntityHitResult(EntityHitResult entityHitResult, Vec3 startPos, Vec3 endPos){
        super(entityHitResult.getEntity(),entityHitResult.getLocation());
        start_pos = startPos;
        end_pos = endPos;
    }

    public Vec3 getStartPos() {
        return start_pos;
    }

    public Vec3 getEndPos() {
        return end_pos;
    }

    @Override
    public double distanceTo(Entity pEntity) {
        return this.location.distanceTo(pEntity.position());
    }
}
