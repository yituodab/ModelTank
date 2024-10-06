package com.model.tank.utils;

import net.minecraft.world.phys.Vec3;

public class HitBox {
    public Vec3 position;
    public double minx;
    public double maxx;
    public double miny;
    public double maxy;
    public double minz;
    public double maxz;
    public double height;
    public double length;
    public double width;
    public HitBox(Vec3 position, double height, double width, double length){
        this.height = height;
        this.width = width;
        this.length = length;
        this.position = position;
        minz = position.x - width/2;
        miny = position.y - height/2;
        minx = position.z - length/2;
        maxz = position.x + width/2;
        maxy = position.y + height/2;
        maxx = position.z + length/2;
    }
    public boolean onHit(HitBox h){
        return this.minx <= h.minx && this.maxx >= h.maxx &&
                this.miny <= h.miny && this.maxy >= h.maxy &&
                this.minz <= h.minz && this.maxz >= h.maxz;
    }
}
