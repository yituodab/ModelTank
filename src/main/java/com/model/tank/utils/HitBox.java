package com.model.tank.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class HitBox extends AABB {
    public HitBox(double pX1, double pY1, double pZ1, double pX2, double pY2, double pZ2) {
        super(pX1, pY1, pZ1, pX2, pY2, pZ2);
    }
}
