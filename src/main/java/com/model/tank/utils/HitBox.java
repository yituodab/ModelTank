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
    public Vec3[] points = new Vec3[8];
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
        points[0] = new Vec3(minx,miny,minz);
        points[1] = new Vec3(minx,miny,maxz);
        points[2] = new Vec3(maxx,miny,minz);
        points[3] = new Vec3(maxx,miny,maxz);
        points[4] = new Vec3(minx,maxy,minz);
        points[5] = new Vec3(minx,maxy,maxz);
        points[6] = new Vec3(maxx,maxy,minz);
        points[7] = new Vec3(maxx,maxy,maxz);
    }
    public void tick(double rotation){

    }
    //public boolean onHit(HitBox h){
        //return this.minx <= h.minx && this.maxx >= h.maxx &&
                //this.miny <= h.miny && this.maxy >= h.maxy &&
                //this.minz <= h.minz && this.maxz >= h.maxz;
    //}
    /*float dotProduct(const Vec2& a, const Vec2& b) {
    return a.x * b.x + a.y * b.y;
}


void getOBBVertices(const OBB& obb, Vec2 vertices[4]) {
    float cosTheta = Math.cos(obb.rotation);
    float sinTheta = Math.sin(obb.rotation);

    Vec2 extentsX = {obb.halfExtents.x * cosTheta, obb.halfExtents.x * sinTheta};
    Vec2 extentsY = {-obb.halfExtents.y * sinTheta, obb.halfExtents.y * cosTheta};

    vertices[0] = {obb.center.x - extentsX.x - extentsY.x, obb.center.y - extentsX.y - extentsY.y};
    vertices[1] = {obb.center.x + extentsX.x - extentsY.x, obb.center.y + extentsX.y - extentsY.y};
    vertices[2] = {obb.center.x + extentsX.x + extentsY.x, obb.center.y + extentsX.y + extentsY.y};
    vertices[3] = {obb.center.x - extentsX.x + extentsY.x, obb.center.y - extentsX.y + extentsY.y};
}


bool isSeparatedOnAxis(const Vec2& axis, const Vec2 verticesA[4], const Vec2 verticesB[4]) {
    float minA = dotProduct(axis, verticesA[0]);
    float maxA = minA;

    for (int i = 1; i < 4; i++) {
        float projection = dotProduct(axis, verticesA[i]);
        minA = std::min(minA, projection);
        maxA = std::max(maxA, projection);
    }

    float minB = dotProduct(axis, verticesB[0]);
    float maxB = minB;

    for (int i = 1; i < 4; i++) {
        float projection = dotProduct(axis, verticesB[i]);
        minB = std::min(minB, projection);
        maxB = std::max(maxB, projection);
    }

    return !(minA <= maxB && maxA >= minB);
}


bool isOBBColliding(const OBB& a, const OBB& b) {
    Vec2 verticesA[4], verticesB[4];
    getOBBVertices(a, verticesA);
    getOBBVertices(b, verticesB);

    Vec2 axes[4] = {
        {verticesA[1].x - verticesA[0].x, verticesA[1].y - verticesA[0].y},
        {verticesA[3].x - verticesA[0].x, verticesA[3].y - verticesA[0].y},
        {verticesB[1].x - verticesB[0].x, verticesB[1].y - verticesB[0].y},
        {verticesB[3].x - verticesB[0].x, verticesB[3].y - verticesB[0].y}
    };

    for (const Vec2& axis : axes) {
        if (isSeparatedOnAxis(axis, verticesA, verticesB)) {
            return false; 
        }
    }
    return true;
}*/
}
