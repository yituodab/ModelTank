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
        public static enum Direction{
            RIGHT,
            LEFT,
            FRONT,
            BACK,
            TOP,
            BOTTOM
        }
        // e 给你看看
        public static Direction StringToDirection(String direction){
            for(Direction d : Direction.values()){
                if(d.toString().equals(direction))return d;
            }
            return Direction.TOP;
        }
        public HitBox hitbox;
        public Vec3 position;
        public double angle;
        public Direction direction;
        public int thickness;
        public double length;
        public double width;
        @Deprecated
        public Armor(double angle,int thickness,double length,double width){

        }
        public Armor(Direction direction,int thickness,double length,double width,Vec3 position){
            this.direction = direction;
            this.thickness = thickness;
            this.length = length;
            this.width = width;
            this.position = position;
            HitBox hitbox;
                switch (direction) {
                    case RIGHT, LEFT -> {
                        hitbox = new HitBox(position,width, (double) thickness /1000,length);
                    }
                    case FRONT, BACK -> {
                        hitbox = new HitBox(position,width,length,(double) thickness /1000);
                    }
                    case TOP, BOTTOM ->{
                        hitbox = new HitBox(position, (double) thickness /1000,width,length);
                    }
                    default -> hitbox = new HitBox(position,0,0,0);
                }
            this.hitbox = hitbox;
        }
    }
}
