package com.model.tank.network.C2S;


import com.model.tank.entities.TankEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public record ClientTankShoot(ResourceLocation id) {
    public void encode(FriendlyByteBuf friendlyByteBuf){
        friendlyByteBuf.writeResourceLocation(id);
    }
    public static ClientTankShoot decode(FriendlyByteBuf friendlyByteBuf){
        ResourceLocation id = friendlyByteBuf.readResourceLocation();
        return new ClientTankShoot(id);
    }
    public void run(Supplier<NetworkEvent.Context> supplier){
        supplier.get().enqueueWork(()->{
            if(Objects.requireNonNull(supplier.get().getSender()).getVehicle() instanceof TankEntity tank){
                tank.shoot(id);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
