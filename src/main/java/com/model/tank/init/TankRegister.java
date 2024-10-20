package com.model.tank.init;

import com.google.gson.JsonObject;
import com.model.tank.ModelTank;
import com.model.tank.entities.tanks.TankEntity;
import com.model.tank.utils.*;
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
    public static final HashMap<String, Plane> PLANES = new HashMap<>();
    public static final HashMap<Tank, RegistryObject<EntityType<TankEntity>>> TANKENTITYS = new HashMap<>();
    public static final RegistryObject<Item> TANKITEM = ITEMS.register("tank_item", ()->
            new TankItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus, JsonObject json){
        for (var entry : json.entrySet()){
            String name = entry.getKey();
            Tank tank = new Tank(name);
            JsonObject tanks = json.get(name).getAsJsonObject();
            tank.modelLocation = new ResourceLocation(ModelTank.MODID,tanks.get("modelLocation").getAsString());
            tank.textureLocation = new ResourceLocation(ModelTank.MODID,tanks.get("textureLocation").getAsString());
            float hitBoxLength = tanks.has("width") ? tanks.get("width").getAsFloat() : 1;
            float hitBoxHeight = tanks.has("height") ? tanks.get("height").getAsFloat() : 1;
            if(tanks.has("models"))
                for(var models : tanks.get("models").getAsJsonArray()){
                JsonObject model = models.getAsJsonObject();
                Vec3 position = new Vec3(
                        model.has("x") ? model.get("x").getAsDouble() : 1,
                	    model.has("y") ? model.get("y").getAsDouble() : 1,
                	    model.has("z") ? model.get("z").getAsDouble() : 1);
                HitBox hitbox = new HitBox(position,
                	model.has("height") ? model.get("height").getAsDouble() : 1,
                	model.has("width") ? model.get("width").getAsDouble() : 1,
                    model.has("length") ? model.get("length").getAsDouble() : 1,
                        0);
                tank.models.add(new Model(position, hitbox, Model.StringToType(model.get("type").getAsString())));
            }
            if(tanks.has("armors"))
                for(var armors : tanks.get("armors").getAsJsonArray()){
                JsonObject armor = armors.getAsJsonObject();
                Vec3 position = new Vec3(
                        armor.has("x") ? armor.get("x").getAsDouble() : 1,
                        armor.has("y") ? armor.get("y").getAsDouble() : 1,
                        armor.has("z") ? armor.get("z").getAsDouble() : 1);
                tank.armors.add(new Model.Armor(Model.Armor.StringToDirection(armor.get("direction").getAsString()),
                        armor.has("width") ? armor.get("thickness").getAsInt() : 500,
                        armor.has("width") ? armor.get("length").getAsDouble() : 1,
                        armor.has("width") ? armor.get("width").getAsDouble() : 1,
                        position));
            }
            RegistryObject<EntityType<TankEntity>> tankEntity;
            tankEntity = ENTITY_TYPES.register(name.toLowerCase(), () -> EntityType.Builder.
                            <TankEntity>of(TankEntity::new, MobCategory.MISC).sized(hitBoxLength, hitBoxHeight).
                    build(new ResourceLocation(ModelTank.MODID, name.toLowerCase()).toString()));
            TANKENTITYS.put(tank, tankEntity);
            TANKS.put(name, tank);
            //TANKITEMS.put(tank, ITEMS.register(name.toLowerCase(), ()->new TankItem(new Item.Properties().stacksTo(1),tank)));

        }
        ENTITY_TYPES.register(eventBus);
        ITEMS.register(eventBus);
    }
}
