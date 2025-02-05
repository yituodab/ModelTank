package com.model.tank.resource.data;

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
    public ResourceLocation modelLocation = null;
    @SerializedName("textureLocation")
    public ResourceLocation textureLocation = null;
    @SerializedName("maxPassenger")
    public int maxPassenger = 1;
    @SerializedName("cannonballs")
    public Cannonball[] cannonballs;
    public Tank(String id) {
        this.id = id;
    }
    public class Cannonball{
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public String id;
    }
}
