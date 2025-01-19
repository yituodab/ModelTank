package com.model.tank.resource.data;

import com.google.gson.annotations.SerializedName;
import com.model.tank.ModelTank;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Tank{
    @SerializedName("name")
    public final String name;
    @SerializedName("modules")
    public Module[] modules;
    @SerializedName("armors")
    public Module.Armor[] armors;
    @SerializedName("modelLocation")
    public String model;
    @SerializedName("textureLocation")
    public String texture;
    public ResourceLocation modelLocation = new ResourceLocation(ModelTank.MODID,model);
    public ResourceLocation textureLocation = new ResourceLocation(ModelTank.MODID,texture);
    public Tank(String name) {
        this.name = name;
    }
}
