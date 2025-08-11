package com.model.tank.api.entity;

import com.model.tank.resource.data.Module;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ModularEntity extends Entity implements GeoEntity {
    protected final List<Module> modules = new ArrayList<>();
    protected final Map<Integer, Integer> moduleHealth = new HashMap<>();
    public ModularEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public boolean hurtModule(Module module, int damage){
        return this.hurtModule(module.getID(), damage);
    }
    public boolean hurtModule(int id, int damage){
        if(this.moduleHealth.get(id) == null){
            return false;
        }
        this.moduleHealth.replace(id, Math.max(damage, 0));
        return true;
    }
    public int getModuleHealth(Module module) {
        return getModuleHealth(module.getID());
    }
    public int getModuleHealth(int id) {
        return moduleHealth.get(id);
    }
    public List<Module> getAllModules() {
        return modules;
    }
}
