package com.model.tank.resource.data;

import com.model.tank.ModularTank;
import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;

public class Tank{
    @SerializedName("id")
    public final String id;
    @SerializedName("name")
    public String name = "mrt.tanks.test";
    @SerializedName("modules")
    public Module[] modules;
    //@SerializedName("armors")
    //public Module.Armor[] armors;
    @SerializedName("modelLocation")
    public ResourceLocation modelLocation = new ResourceLocation(ModularTank.MODID, "test.json");
    @SerializedName("textureLocation")
    public ResourceLocation textureLocation = new ResourceLocation(ModularTank.MODID, "test.png");
    @SerializedName("maxPassenger")
    public int maxPassenger = 1;
    @SerializedName("cannonballs")
    public Cannonball[] cannonballs;
    public Tank(String id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return "id="+id+
        "name="+name+
        "modelLocation="+modelLocation.toString()+
        "textureLocation="+textureLocation.toString();
    }
    public class Cannonball{
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public String id;
    }
}
