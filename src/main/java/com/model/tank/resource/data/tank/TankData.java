package com.model.tank.resource.data.tank;

import com.model.tank.ModularTank;
import com.google.gson.annotations.SerializedName;
import com.model.tank.resource.data.Module;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TankData {
    @SerializedName("name")
    public String name = "mrt.tanks.default";
    @SerializedName("boundingBox")
    public float[] boundingBox = {1,1,1};
    @SerializedName("modules")
    public Module[] modules = {};
    @SerializedName("armors")
    public Module.Armor[] armors = {};
    @SerializedName("maxCannonballNumber")
    public int maxCannonballNumber = 50;
    @SerializedName("maxSpeed")
    public double maxSpeed = 11;
    @SerializedName("backSpeed")
    public double backSpeed = 5;
    @SerializedName("steeringSpeed")
    public float steeringSpeed = 20;
    @SerializedName("acceleration")
    public float acceleration = 1;
    @SerializedName("cannonballs")
    public ResourceLocation[] cannonballs = {};
}
