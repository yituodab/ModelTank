package com.model.tank.resource.data;

import com.model.tank.ModularTank;
import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Tank{
    @SerializedName("name")
    public String name = "mrt.tanks.default";
    @SerializedName("boundingBox")
    public float[] boundingBox = {1,1,1};
    @SerializedName("modules")
    public Module[] modules = {};
    @OnlyIn(Dist.CLIENT)
    @SerializedName("modelLocation")
    public ResourceLocation modelLocation = new ResourceLocation(ModularTank.MODID, "geo/model.geo.json");
    @OnlyIn(Dist.CLIENT)
    @SerializedName("textureLocation")
    public ResourceLocation textureLocation = new ResourceLocation(ModularTank.MODID, "textures/texture.png");
    @SerializedName("maxCannonballNumber")
    public int maxCannonballNumber = 50;
    @SerializedName("maxSpeed")
    public double maxSpeed = 11;
    @SerializedName("steeringSpeed")
    public float steeringSpeed = 20;
    @SerializedName("acceleration")
    public float acceleration = 1;
    @SerializedName("cannonballs")
    public ResourceLocation[] cannonballs = {};
}
