package com.model.tank.resource.data;

import com.model.tank.ModularTank;
import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Tank{
    @SerializedName("id")
    public final String id;
    @SerializedName("name")
    public String name = "mrt.tanks.test";
    @SerializedName("boundingBox")
    public float[] boundingBox = {1,1};
    @SerializedName("modules")
    public Module[] modules;
    //@SerializedName("armors")
    //public Module.Armor[] armors;
    @OnlyIn(Dist.CLIENT)
    @SerializedName("modelLocation")
    public ResourceLocation modelLocation = new ResourceLocation(ModularTank.MODID, "geo/model.geo.json");
    @OnlyIn(Dist.CLIENT)
    @SerializedName("textureLocation")
    public ResourceLocation textureLocation = new ResourceLocation(ModularTank.MODID, "textures/texture.png");
    @SerializedName("maxPassenger")
    public int maxPassenger = 1;
    @SerializedName("maxCannonballNumber")
    public int maxCannonballNumber = 50;
    @SerializedName("cannonballs")
    public ResourceLocation[] cannonballs;
    public Tank(String id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return "id="+id+
        ", name="+name+
        ", modelLocation="+modelLocation.toString()+
        ", textureLocation="+textureLocation.toString();
    }
}
