package com.model.tank.network.C2S;

import com.model.tank.entities.TankEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

// 该类用于客户端换弹时切换炮弹向服务端发包刷新换弹时间
public record ClientTankReload(){
    public void encode(FriendlyByteBuf friendlyByteBuf){
    }
    public static ClientTankReload decode(FriendlyByteBuf friendlyByteBuf){
        return new ClientTankReload();
    }
    public void run(Supplier<NetworkEvent.Context> supplier){
        supplier.get().enqueueWork(()->{
            if(Objects.requireNonNull(supplier.get().getSender()).getVehicle() instanceof TankEntity tank){
                tank.resetReloadTime();
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
