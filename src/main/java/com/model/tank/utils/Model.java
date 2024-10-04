package com.model.tank.utils;

import net.minecraft.world.phys.Vec3;

public class Model {
    public static enum TYPE{
        ENGINE,
        OIL_TANK,
        AMMO_RACKS,
        CANNON
    }
    public final Vec3 position;
    public final HitBox hitbox;
    public Model(Vec3 position, HitBox hitbox){
        this.position = position;
        this.hitbox = hitbox;
    }
}
