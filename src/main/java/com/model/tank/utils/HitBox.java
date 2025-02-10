package com.model.tank.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class HitBox extends AABB {
    public float angle = 0;
    public HitBox(Vec3 startPos, Vec3 endPos, float angle){
        super(startPos,endPos);
        this.angle = angle;
    }
    public HitBox(double pX1, double pY1, double pZ1, double pX2, double pY2, double pZ2,float angle) {
        super(pX1, pY1, pZ1, pX2, pY2, pZ2);
        this.angle = angle;
    }

    @Override
    public Optional<Vec3> clip(Vec3 pFrom, Vec3 pTo) {
        Vec3 from = pFrom.yRot(angle);
        Vec3 to = pTo.yRot(angle);
        return super.clip(from, to);
    }
}
