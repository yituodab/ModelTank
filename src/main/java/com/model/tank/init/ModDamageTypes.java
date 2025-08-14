package com.model.tank.init;

import com.model.tank.ModularTank;
import com.model.tank.entities.CannonballEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ModDamageTypes {
    public static enum Type{
        CREW_KILLED,
        CANNONBALL,
        MARTYRDOM,
        IGNITE
    }

    public static final ResourceKey<DamageType> CANNONBALL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ModularTank.MODID, "cannonball"));
    public static final ResourceKey<DamageType> ON_HIT_AMMO = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ModularTank.MODID, "on_hit_ammo"));
    public static final ResourceKey<DamageType> IGNITE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ModularTank.MODID, "ignite"));

    public static DamageSource create(Level level, Type type, CannonballEntity cannonball, Entity shooter){
        var registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        switch (type){
            case CANNONBALL,CREW_KILLED -> {
                return new DamageSource(registry.getHolderOrThrow(CANNONBALL), cannonball, shooter);
            }
            case MARTYRDOM -> {
                return new DamageSource(registry.getHolderOrThrow(ON_HIT_AMMO), cannonball, shooter);
            }
            case IGNITE -> {
                return new DamageSource(registry.getHolderOrThrow(IGNITE), cannonball, shooter);
            }
        }
        return new DamageSource(registry.getHolderOrThrow(CANNONBALL), cannonball, shooter);
    }
}
