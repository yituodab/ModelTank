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
    public double weight;
    public HitBox(Vec3 position, double height, double weight, double length){
        this.height = height;
        this.weight = weight;
        this.length = length;
        minx = position.x - weight/2;
        miny = position.y - height/2;
        minz = position.z - length/2;
        maxx = position.x + weight/2;
        maxy = position.y + height/2;
        maxz = position.z + length/2;
    }
    public boolean onHit(HitBox h){
        return this.minx <= h.minx && this.maxx >= h.maxx &&
                this.miny <= h.miny && this.maxy >= h.maxy &&
                this.minz <= h.minz && this.maxz >= h.maxz;
    }
}
