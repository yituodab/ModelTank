package com.model.tank.resource.client.data.tank;

import com.google.gson.annotations.SerializedName;
import com.model.tank.ModularTank;
import net.minecraft.resources.ResourceLocation;

public class TankDisplay {
    @SerializedName("name")
    private String name = "mrt.tanks.default";
    @SerializedName("model")
    private ResourceLocation model = new ResourceLocation(ModularTank.MODID, "geo/model.geo.json");
    @SerializedName("texture")
    private ResourceLocation texture = new ResourceLocation(ModularTank.MODID, "textures/texture.png");;

    public String getName() {
        return name;
    }
    public ResourceLocation getModel() {
        return model;
    }
    public ResourceLocation getTexture() {
        return texture;
    }
}
