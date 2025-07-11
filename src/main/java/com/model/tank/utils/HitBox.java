package com.model.tank.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HitBox extends AABB {
    private float YRot = 0;
    private float XRot = 0;

    public HitBox(BlockPos pPos,float XRot,float YRot) {
        this((double)pPos.getX(), (double)pPos.getY(), (double)pPos.getZ(), (double)(pPos.getX() + 1), (double)(pPos.getY() + 1), (double)(pPos.getZ() + 1),XRot,YRot);
    }

    public HitBox(BlockPos pStart, BlockPos pEnd,float XRot, float YRot) {
        this((double)pStart.getX(), (double)pStart.getY(), (double)pStart.getZ(), (double)pEnd.getX(), (double)pEnd.getY(), (double)pEnd.getZ(),XRot,YRot);
    }
    public HitBox(Vec3 startPos, Vec3 endPos, float XRot, float YRot){
        super(startPos,endPos);
        this.XRot = XRot;
        this.YRot = YRot;
    }
    public Vec3 getPos(){
        return new Vec3((maxX-minX)/2+minX,(maxY-minY)/2+minY,(maxY-minY)/2+minY);
    }
    public HitBox(double pX1, double pY1, double pZ1, double pX2, double pY2, double pZ2,float XRot,float YRot) {
        super(pX1, pY1, pZ1, pX2, pY2, pZ2);
        this.XRot = XRot;
        this.YRot = YRot;
    }

    @Override
    public Optional<Vec3> clip(Vec3 pFrom, Vec3 pTo) {
        Vec3 Pos = getPos();
        Vec3 From = pFrom.subtract(Pos);
        Vec3 To = pTo.subtract(pTo);
        Vec3 from = From.xRot(XRot).yRot(YRot).add(pFrom);
        Vec3 to = To.xRot(XRot).yRot(YRot).add(pTo);
        return super.clip(from, to);
    }
}
