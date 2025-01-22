package com.model.tank.resource.data;

import com.google.gson.annotations.SerializedName;
import com.model.tank.ModularTank;
import net.minecraft.resources.ResourceLocation;

public class Tank{
    @SerializedName("id")
    public final String id;
    @SerializedName("name")
    public String name;
    @SerializedName("modules")
    public Module[] modules;
    @SerializedName("armors")
    public Module.Armor[] armors;
    @SerializedName("modelLocation")
    public String model;
    @SerializedName("textureLocation")
    public String texture;
    @SerializedName("maxPassenger")
    public int maxPassenger;
    public ResourceLocation modelLocation = new ResourceLocation(ModularTank.MODID,model);
    public ResourceLocation textureLocation = new ResourceLocation(ModularTank.MODID,texture);
    public Tank(String id) {
        this.id = id;
    }
}
