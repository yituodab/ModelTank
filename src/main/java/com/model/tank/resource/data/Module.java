package com.model.tank.resource.data;

import com.google.gson.annotations.SerializedName;
import com.model.tank.utils.HitBox;
import net.minecraft.world.phys.Vec3;

public class Module {
    public Module(double[] xyz,double[] size, Type type){
        this.xyz = xyz;
        this.size = size;
        this.type = type;
    }
    public static enum Type{
        UNKNOWN,
        ENGINE,
        OIL_TANK,
        AMMO_RACKS,
        CANNON,
        LATCH
    }
    @SerializedName("type")
    public Type type;
    @SerializedName("position")
    public double[] xyz;
    @SerializedName("size")
    public double[] size;
    public static Type StringToType(String type){
        for(Type t : Type.values()){
            if(t.toString().equals(type))return t;
        }
        return Type.UNKNOWN;
    }
    public Module copy(){
        Type p = Type.UNKNOWN;
        for(Type t : Type.values()){if(t == this.type)p = t;}
        return new Module(xyz, size, type);
    }
    //public static class Armor extends Module{
    //    public HitBox hitbox;
    //    public Vec3 position;
    //    public double angle;
    //    public int thickness;
    //    public double length;
    //    public double width;
    //    public Armor(Vec3 position,double angle,int thickness,double length,double width){
    //        super(position, )
    //    }
    //}
}
