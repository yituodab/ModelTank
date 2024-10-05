package com.model.tank.utils;

import net.minecraft.world.phys.Vec3;

public class Model {
    public static enum Type{
        UNKNOWN,
        ENGINE,
        OIL_TANK,
        AMMO_RACKS,
        CANNON,
        LATCH
    }
    public final Type type;
    public final Vec3 position;
    public final HitBox hitbox;
    public Model(Vec3 position, HitBox hitbox,Type type){
        this.position = position;
        this.hitbox = hitbox;
        this.type = type;
    }
    public static Type StringToType(String type){
        for(Type t : Type.values()){
            if(t.toString().equals(type))return t;
        }
        return Type.UNKNOWN;
    }
    public static class Armor{
        public double angle;
        public int thickness;
        public double length;
        public double weight;
        @Deprecated
        public Armor(double angle,int thickness,double length,double weight){

        }
        public Armor(int thickness,double length,double weight){

        }
    }
}
