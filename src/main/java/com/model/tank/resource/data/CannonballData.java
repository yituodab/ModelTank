package com.model.tank.resource.data;

import com.google.gson.annotations.SerializedName;
import com.model.tank.utils.CannonballType;
import net.minecraft.resources.ResourceLocation;

public class CannonballData {
    @SerializedName("name")
    public String name;
    @SerializedName("id")
    public final String id;
    @SerializedName("speed")
    public double speed;
    @SerializedName("type")
    public CannonballType type;
    @SerializedName("entity_damage")
    public float entityDamage;
    @SerializedName("life")
    public int life;
    public CannonballData(String id) {
        this.id = id;
    }
}
