package com.model.tank.mixins;

import com.model.tank.api.client.interfaces.ILocalPlayer;
import com.model.tank.client.key.ShootKey;
import com.model.tank.entities.tank.TankEntity;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin implements ILocalPlayer {
    private @Unique boolean modularTank$aim = false;
    @Override
    public void shoot() {
        if(((LocalPlayer)(Object)this).getVehicle() instanceof TankEntity tank)
            ShootKey.shoot(tank, tank.getCurrentCannonball());
    }

    @Override
    public void aim(boolean aim) {
        this.modularTank$aim = aim;
    }

    @Override
    public boolean isAim() {
        return modularTank$aim;
    }
}
