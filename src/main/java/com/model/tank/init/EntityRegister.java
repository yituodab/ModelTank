package com.model.tank.init;

import com.model.tank.ModelTank;
import com.model.tank.entities.tanks.TankEntity;
import com.model.tank.utils.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ModelTank.MODID);

    public static final RegistryObject<EntityType<TankEntity>> TANKENTITY = ENTITY_TYPES.register("tank", () -> EntityType.Builder.
                    <TankEntity>of(TankEntity::new, MobCategory.MISC).
            build(new ResourceLocation(ModelTank.MODID, "tank").toString()));


}
