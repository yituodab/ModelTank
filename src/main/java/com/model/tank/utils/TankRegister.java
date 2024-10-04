package com.model.tank.utils;

import com.google.gson.JsonObject;
import com.model.tank.ModelTank;
import com.model.tank.entities.tanks.TankEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.Supplier;

public class TankRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ModelTank.MODID);
    public static final HashMap<String, Tank> TANKS = new HashMap<>();
    public static final HashMap<String, Supplier<EntityType<TankEntity>>> TANKENTITYS = new HashMap<>();
    public TankRegister(JsonObject json){
        for (var entry : json.entrySet()){
            String name = entry.getKey();
            JsonObject tanks = json.get("name").getAsJsonObject();
            for(var models : tanks.get("models").getAsJsonArray()){
                JsonObject model = models.getAsJsonObject();
                Tank tank = new Tank();
            }
        }
    }
}
