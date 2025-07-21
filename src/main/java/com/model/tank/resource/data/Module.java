package com.model.tank.resource.data;

import com.google.gson.annotations.SerializedName;
import com.model.tank.utils.HitBox;

import java.util.UUID;

public class Module {
    public static enum Type{
        UNKNOWN,
        ENGINE,
        OIL_TANK,
        AMMO_RACKS,
        CANNON,
        LATCH
    }
    @SerializedName("id")
    private int id = 0;
    @SerializedName("type")
    private Type type = Type.UNKNOWN;
    @SerializedName("position")
    private double[] position = {1,1,1};
    @SerializedName("size")
    private double[] size = {1,1,1};
    @SerializedName("maxHealth")
    private int maxHealth = 100;
    private HitBox hitBox = new HitBox(getX()-getWidth()/2,getY()-getHeight()/2,getZ()-getLength()/2,
            getX()+getWidth()/2,getY()+getHeight()/2,getZ()+getLength()/2,0,0);
    public double getX(){
        return position[0];
    }
    public double getY(){
        return position[1];
    }
    public double getZ(){
        return position[2];
    }
    public double getLength(){
        return size[2];
    }
    public double getWidth(){
        return size[0];
    }
    public double getHeight(){return size[1];}
    public HitBox getHitBox() {
        return hitBox;
    }
    public Type getType() {return type;}
    public int getID() {return id;}
    public int getMaxHealth() {return maxHealth;}
    // 炮
    @SerializedName("reloadTime")
    private float reloadTime = 0;
    public float getReloadTime() {return reloadTime;}

    public static class Armor{
        @SerializedName("XRot")
        public float XRot = 0;
        @SerializedName("YRot")
        public float YRot = 0;
        public Armor(double[] position, double[] size) {
            this.position = position;
            this.size = size;
        }
        public double getX(){
            return position[0];
        }
        public double getY(){
            return position[1];
        }
        public double getZ(){
            return position[2];
        }
        @SerializedName("position")
        public double[] position = {1,1,1};
        @SerializedName("size")
        public double[] size = {1,1};
        @SerializedName("thickness")
        public double thickness = 1;
        public double getWidth() {
            return size[0];
        }
        public double getHeight() {
            return size[1];
        }
        public double getLength() {
            return thickness;
        }
        public HitBox getHitBox() {
            return new HitBox(getX()-getWidth()/2,getY()-getHeight()/2,getZ()-getLength()/2,
                    getX()+getWidth()/2,getY()+getHeight()/2,getZ()+getLength()/2,XRot,YRot);
        }
    }
}
