package com.model.tank.utils;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ModDimensions extends EntityDimensions {
    public final float length;
    @Deprecated
    public ModDimensions(float pWidth, float pHeight, boolean pFixed) {
        super(pWidth, pHeight, pFixed);
        this.length = 0;
    }
    public ModDimensions(float width,float height,float length,boolean fixed){
        super(width,height,fixed);
        this.length = length;
    }

    @Override
    public AABB makeBoundingBox(Vec3 pPos) {
        return this.makeBoundingBox(pPos.x,pPos.y,pPos.z);
    }

    @Override
    public AABB makeBoundingBox(double pX, double pY, double pZ) {
        return makeBoundingBox(pX,pY,pZ,0,0);
    }
    public HitBox makeBoundingBox(Vec3 pos, float XRot, float YRot) {
        return makeBoundingBox(pos.x,pos.y,pos.z,XRot,YRot);
    }
    public HitBox makeBoundingBox(double pX,double pY, double pZ, float XRot, float YRot) {
        return new HitBox(pX - width / 2, pY, pZ - length / 2, pX + width / 2, pY + height, pZ + length / 2, XRot, YRot);
    }
}
