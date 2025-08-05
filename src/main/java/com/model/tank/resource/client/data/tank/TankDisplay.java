package com.model.tank.resource.client.data.tank;

import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;

public class TankDisplay {
    @SerializedName("model")
    private ResourceLocation model;
    @SerializedName("texture")
    private ResourceLocation texture;
    public ResourceLocation getModel() {
        return model;
    }
    public ResourceLocation getTexture() {
        return texture;
    }
}
