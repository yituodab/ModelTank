package com.model.tank.mixins;

import com.model.tank.api.client.interfaces.ILocalPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin implements ILocalPlayer {
    private @Unique boolean aim = false;
    @Override
    public void aim(boolean aim) {
        this.aim = aim;
    }

    @Override
    public boolean isAim() {
        return aim;
    }
}
