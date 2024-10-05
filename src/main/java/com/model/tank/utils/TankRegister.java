package com.model.tank.utils;

import com.google.gson.JsonObject;
import com.model.tank.ModelTank;
import com.model.tank.entities.tanks.TankEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class TankRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ModelTank.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ModelTank.MODID);
    public static final HashMap<String, Tank> TANKS = new HashMap<>();
    public static final HashMap<Tank, RegistryObject<EntityType<TankEntity>>> TANKENTITYS = new HashMap<>();
    public static final HashMap<Tank, RegistryObject<Item>> TANKITEMS = new HashMap<>();

    public static void register(IEventBus eventBus, JsonObject json){
        ENTITY_TYPES.register(eventBus);
        ITEMS.register(eventBus);
        for (var entry : json.entrySet()){
            String name = entry.getKey();
            Tank tank = new Tank(name);
            JsonObject tanks = json.get(name).getAsJsonObject();
            tank.modelLocation = new ResourceLocation(ModelTank.MODID,json.get("modelLocation").getAsString());
            tank.textureLocation = new ResourceLocation(ModelTank.MODID,tanks.get("textureLocation").getAsString());
            float hitBoxLength = tanks.get("length").getAsFloat();
            float hitBoxHeight = tanks.get("height").getAsFloat();
            for(var models : tanks.get("models").getAsJsonArray()){
                JsonObject model = models.getAsJsonObject();
                Vec3 position = new Vec3(model.get("x").getAsDouble(),model.get("y").getAsDouble(),model.get("z").getAsDouble());
                HitBox hitbox = new HitBox(position, model.get("height").getAsDouble(),model.get("weight").getAsDouble(),model.get("length").getAsDouble());
                tank.models.add(new Model(position, hitbox, Model.StringToType(model.get("type").getAsString())));
            }
            for(var armors : tanks.get("armors").getAsJsonArray()){
                JsonObject armor = armors.getAsJsonObject();
                Vec3 position = new Vec3(armor.get("x").getAsDouble(),armor.get("y").getAsDouble(),armor.get("z").getAsDouble());
                tank.armors.add(new Model.Armor(Model.Armor.StringToDirection(armor.get("direction").getAsString()),
                        armor.get("thickness").getAsInt(),
                        armor.get("length").getAsDouble(),
                        armor.get("weight").getAsDouble(),
                        position));
            }
            RegistryObject<EntityType<TankEntity>> tankEntity;
            tankEntity = ENTITY_TYPES.register(name, () -> EntityType.Builder.
                    <TankEntity>of(TankEntity::new, MobCategory.MISC).sized(hitBoxLength, hitBoxHeight).
                    build(new ResourceLocation(ModelTank.MODID, name).toString()));
            TANKENTITYS.put(tank, tankEntity);
            TANKITEMS.put(tank, ITEMS.register(name, ()->new TankItem(new Item.Properties().stacksTo(1),tank)));
        }
    }
}
