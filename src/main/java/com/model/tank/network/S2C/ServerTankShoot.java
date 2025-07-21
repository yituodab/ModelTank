package com.model.tank.network.S2C;

import com.model.tank.entities.TankEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ServerTankShoot(int currentCannonballNumber,boolean shootSuccess){
    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(currentCannonballNumber);
        friendlyByteBuf.writeBoolean(shootSuccess);
    }
    public static ServerTankShoot decode(FriendlyByteBuf friendlyByteBuf){
        int number = friendlyByteBuf.readInt();
        boolean shootSuccess = friendlyByteBuf.readBoolean();
        return new ServerTankShoot(number,shootSuccess);
    }
    public void run(Supplier<NetworkEvent.Context> supplier){
        supplier.get().enqueueWork(()->{
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.getVehicle() instanceof TankEntity tank) {
                tank.getCannonballs().get(tank.getCurrentCannonball()).setNumber(currentCannonballNumber);
                if(shootSuccess)tank.resetReloadTime();
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
