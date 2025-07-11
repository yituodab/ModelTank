package com.model.tank.resource.data;

import com.google.gson.annotations.SerializedName;
import com.model.tank.utils.CannonballType;
import net.minecraft.resources.ResourceLocation;

public class CannonballData {
    @SerializedName("name")
    public String name;
    @SerializedName("id")
    public final ResourceLocation id;
    @SerializedName("speed")
    public double speed;
    @SerializedName("type")
    public CannonballType type = CannonballType.HE;
    @SerializedName("entity_damage")
    public float entityDamage;
    @SerializedName("life")
    public int life = 10;
    public CannonballData(ResourceLocation id) {
        this.id = id;
    }
}
