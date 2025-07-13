package com.model.tank.network.C2S;


import com.model.tank.entities.TankEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public record ClientTankShoot(int tankID) {
    public void encode(FriendlyByteBuf friendlyByteBuf){
        friendlyByteBuf.writeInt(tankID);
    }
    public static ClientTankShoot decode(FriendlyByteBuf friendlyByteBuf){
        int id = friendlyByteBuf.readInt();
        return new ClientTankShoot(id);
    }
    public void run(Supplier<NetworkEvent.Context> supplier){
        supplier.get().enqueueWork(()->{
            if(Objects.requireNonNull(supplier.get().getSender()).getVehicle() instanceof TankEntity tank){
                tank.shoot();
            };
        });
        supplier.get().setPacketHandled(true);
    }
}
