package com.model.tank.utils;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Plane{
    public final String name;
    public final int weight;//1kg=9.8N
    public final int WingS;
    public float CI;
    public static final float AIR_DENSITY = 1.22f;
    public List<Model> models = new ArrayList<>();
    //public List<Model.Armor> armors = new ArrayList<>();
    public ResourceLocation modelLocation;
    public ResourceLocation textureLocation;
    public Plane(String name) {
        this.name = name;
    }
    public double getLift(double speed){
        return (AIR_DENSITY * speed * speed * CI * WingS) / 2;
    }
}
