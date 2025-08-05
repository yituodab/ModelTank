package com.model.tank.resource.data.tank;

import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;

public class TankIndexData {
    @SerializedName("name")
    private String name = "mrt.tanks.default";
    @SerializedName("tank_data")
    private ResourceLocation tank_data;
    @SerializedName("display")
    private ResourceLocation display;
    public String getName() {
        return name;
    }

    public ResourceLocation getTankData() {
        return tank_data;
    }

    public ResourceLocation getDisplay() {
        return display;
    }
}
