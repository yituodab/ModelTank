package com.model.tank.utils;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Plane{
    public final String name;
    private int mass;//kg,1kg=9.8N
    private int WingS;
    private float MaxCl;
    public float maxSpeed;
    public static final float AIR_DENSITY = 1.22f;
    public List<Model> models = new ArrayList<>();
    //public List<Model.Armor> armors = new ArrayList<>();
    public ResourceLocation modelLocation;
    public ResourceLocation textureLocation;
    public Plane(String name) {
        this.name = name;
    }

    public double getLift(double speed,double angle){
        double Cl = MaxCl / 85 * angle;
        return (AIR_DENSITY * speed * speed * Cl * WingS) / 2;
    }
    public double getFlySpeed(){
        double Cl = MaxCl / 85 * 5;
        return (AIR_DENSITY*WingS*Cl*2*mass*9.8);
    }
}
