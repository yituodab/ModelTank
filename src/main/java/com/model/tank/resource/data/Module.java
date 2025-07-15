package com.model.tank.resource.data;

import com.google.gson.annotations.SerializedName;
import com.model.tank.utils.HitBox;

public class Module {
    public Module(double[] position, double[] size, Type type){
        this.position = position;
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
    public double[] position = {1,1,1};
    @SerializedName("size")
    public double[] size = {1,1,1};
    @SerializedName("maxHealth")
    public int maxHealth = 100;
    private HitBox hitBox = new HitBox(getX()-getWidth()/2,getY()-getHeight()/2,getZ()-getLength()/2,
            getX()+getWidth()/2,getY()+getHeight()/2,getZ()+getLength()/2,0,0);
    private int health = maxHealth;
    public Module copy(){
        Type p = Type.UNKNOWN;
        for(Type t : Type.values()){if(t == this.type)p = t;}
        return new Module(position, size, type);
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
    public double getLength(){
        return size[2];
    }
    public double getWidth(){
        return size[0];
    }
    public double getHeight(){
        return size[1];
    }
    public HitBox getHitBox() {
        return hitBox;
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int health){
        this.health = health;
    }
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
