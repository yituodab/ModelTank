package com.model.tank.api.entity;

import java.util.*;

import com.model.tank.resource.data.Tank;
import com.model.tank.resource.data.Module;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.RideCommand;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;


public abstract class AbstractTank extends Entity implements GeoEntity {
    public ResourceLocation modelLocation;
    public ResourceLocation textureLocation;
    public double maxSpeed;// m/s
    public int MaxPassenger;
    public List<Player> passengers;
    public List<Module> modules;
    public List<Module.Armor> armors;
    public AbstractTank(EntityType<?> p_19870_, Level p_19871_, Tank tank) {
        super(p_19870_, p_19871_);
        this.modules = List.of(tank.modules);
        this.armors = List.of(tank.armors);
        this.MaxPassenger = tank.maxPassenger;
        for (Module m : tank.modules) {
            modules.add(m.copy());
        }
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if(pPlayer.isLocalPlayer())return InteractionResult.PASS;
        return pPlayer.startRiding(this) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    protected boolean canAddPassenger(Entity pPassenger) {
        return passengers.size() < MaxPassenger && pPassenger instanceof Player;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean isVehicle() {
        return true;
    }

    public AbstractTank(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public void load(CompoundTag p_20259_) {
        super.load(p_20259_);
        //this.tank = EntityRegister.TANKS.get(p_20259_.getString("tank"));
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }
}
