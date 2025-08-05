package com.model.tank.resource.data.tank;

import com.google.gson.annotations.SerializedName;
import com.model.tank.utils.CannonballType;

public class CannonballData {
    @SerializedName("name")
    public String name = "mrt.cannonballs.default";
    @SerializedName("speed")
    public double speed = 300;
    @SerializedName("type")
    public CannonballType type = CannonballType.HE;
    @SerializedName("entity_damage")
    public float entityDamage = 20;
    @SerializedName("life")
    public int life = 10;
}
