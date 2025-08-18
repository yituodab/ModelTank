package com.model.tank.resource.data.tank;

import com.google.gson.annotations.SerializedName;
import com.model.tank.utils.CannonballType;

public class CannonballData {
    @SerializedName("name")
    private String name = "mrt.cannonballs.default";
    @SerializedName("speed")
    private double speed = 300;
    @SerializedName("type")
    private CannonballType type = CannonballType.HE;
    @SerializedName("entity_damage")
    private float entityDamage = 20;
    @SerializedName("tnt_mass")
    private float TNTmass;
    @SerializedName("life")
    private int life = 10;

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public CannonballType getType() {
        return type;
    }

    public float getEntityDamage() {
        return entityDamage;
    }

    public float getTNTmass() {
        return TNTmass;
    }

    public int getLife() {
        return life;
    }
}
