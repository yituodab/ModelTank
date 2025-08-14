package com.model.tank.resource.data.tank;

import com.google.gson.annotations.SerializedName;
import com.model.tank.api.resource.data.ModularEntityData;
import com.model.tank.resource.data.Module;
import net.minecraft.resources.ResourceLocation;

public class TankData extends ModularEntityData {
    @SerializedName("boundingBox")
    private float[] boundingBox = {1,1,1};
    @SerializedName("armors")
    private Module.Armor[] armors = {};
    @SerializedName("maxCannonballNumber")
    private int maxCannonballNumber = 50;
    @SerializedName("cannonballs")
    private ResourceLocation[] cannonballs = {};

    public float[] getBoundingBox() {
        return boundingBox;
    }

    public Module.Armor[] getArmors() {
        return armors;
    }

    public int getMaxCannonballNumber() {
        return maxCannonballNumber;
    }
    public ResourceLocation[] getCannonballs() {
        return cannonballs;
    }
}
