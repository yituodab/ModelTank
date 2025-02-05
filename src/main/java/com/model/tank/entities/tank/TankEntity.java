package com.model.tank.entities.tank;

import com.model.tank.api.entity.AbstractTank;
import com.model.tank.entities.cannonball.CannonballEntity;
import com.model.tank.init.EntityRegister;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.CannonballData;
import com.model.tank.resource.data.Tank;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class TankEntity extends AbstractTank {
    private Tank.Cannonball currentCannonball;
    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;
    private double tickSpeed = maxSpeed / 20;
    public TankEntity(EntityType<?> p_19870_, Level p_19871_, Tank tank) {
        super(p_19870_, p_19871_, tank);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public TankEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public void load(CompoundTag p_20259_) {
        super.load(p_20259_);
        //this.tank = EntityRegister.TANKS.get(p_20259_.getString("tank"));
    }

    @Override
    public void shoot() {
        Level level = this.level();
        CannonballData cannonballData = DataManager.CANNONBALLS.get(currentCannonball.id);
        if(cannonballData != null && cannonballData.id != null){
            CannonballEntity cannonball = new CannonballEntity(EntityRegister.CANNONBALLENTITY.get(), level, this, cannonballData);
            cannonball.setPos(this.position());
            cannonball.shoot(this,this.getXRot(),this.getYRot());
            level.addFreshEntity(cannonball);
        }
    }

    @Override
    public void controlTank() {
    }

}
