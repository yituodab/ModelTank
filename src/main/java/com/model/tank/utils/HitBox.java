package com.model.tank.utils;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HitBox extends AABB {
    private List<HitBox> childHitboxs = null;
    private boolean empty;
    private float YRot = 0;
    private float XRot = 0;
    public HitBox(Vec3 startPos, Vec3 endPos, float XRot, float YRot){
        super(startPos,endPos);
        this.XRot = XRot;
        this.YRot = YRot;
    }
    public HitBox(){
        super(0,0,0,0,0,0);
        this.empty = true;
    }
    public HitBox(double pX1, double pY1, double pZ1, double pX2, double pY2, double pZ2,float XRot,float YRot) {
        super(pX1, pY1, pZ1, pX2, pY2, pZ2);
        this.XRot = XRot;
        this.YRot = YRot;
    }
    public void putChildHitbox(HitBox hitbox){
        if(childHitboxs == null)childHitboxs = new ArrayList<>();
        childHitboxs.add(hitbox);
    }
    public boolean isEmpty(){
        return empty;
    }

    @Override
    public Optional<Vec3> clip(Vec3 pFrom, Vec3 pTo) {
        Optional<Vec3> last = null;
        if(this.childHitboxs != null && !this.childHitboxs.isEmpty()) {
            for (HitBox hitbox : childHitboxs) {
                Optional<Vec3> vec3 = hitbox.clip(pFrom, pTo);
                if (vec3.isPresent() && (last != null || vec3.get().distanceTo(pFrom) < last.get().distanceTo(pFrom))) {
                    last = vec3;
                }
            }
        }
        if(isEmpty())return null;
        if(last == null) {
            Vec3 from = pFrom.xRot(XRot).yRot(YRot);
            Vec3 to = pTo.xRot(XRot).yRot(YRot);
            return super.clip(from, to);
        }
        return last;
    }
}
