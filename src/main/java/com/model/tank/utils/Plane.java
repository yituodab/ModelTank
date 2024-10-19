package com.model.tank.utils;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Plane{
    public final String name;
    public List<Model> models = new ArrayList<>();
    //public List<Model.Armor> armors = new ArrayList<>();
    public ResourceLocation modelLocation;
    public ResourceLocation textureLocation;
    public Plane(String name) {
        this.name = name;
    }
}
