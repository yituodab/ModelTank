package com.model.tank.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class EntityHelper {
    public static List<EntityHitResult> findEntitiesOnPath(Projectile projectile, Vec3 startPos, Vec3 endPos){
        List<EntityHitResult> hitResults = new ArrayList<>();
        List<Entity> entities = projectile.level().getEntities(projectile, new AABB(startPos, endPos).inflate(1.0));
        Entity owner = projectile.getOwner();
        for(Entity entity : entities){
            if(entity.equals(owner)){
                continue;
            }
            if(owner != null && owner.getVehicle() != null && entity.equals(owner.getVehicle())){
                continue;
            }
            if(!entity.isAlive()){
                continue;
            }
            AABB boundingBox = entity.getBoundingBox();
            Vec3 hitPos = boundingBox.clip(startPos, endPos).orElse(null);
            if(hitPos == null){
                continue;
            }
            hitResults.add(new EntityHitResult(entity, hitPos));
        }
        MathUtils.sort(hitResults, pair -> pair.getLeft().getLocation().distanceTo(startPos) > pair.getRight().getLocation().distanceTo(startPos));
        return hitResults;
    }
}
