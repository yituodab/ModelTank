package com.model.tank.network.S2C;

import com.model.tank.entities.TankEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ServerTankShoot(ResourceLocation id,int currentCannonballNumber, boolean shootSuccess){
    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeResourceLocation(id);
        friendlyByteBuf.writeInt(currentCannonballNumber);
        friendlyByteBuf.writeBoolean(shootSuccess);
    }
    public static ServerTankShoot decode(FriendlyByteBuf friendlyByteBuf){
        ResourceLocation id = friendlyByteBuf.readResourceLocation();
        int number = friendlyByteBuf.readInt();
        boolean shootSuccess = friendlyByteBuf.readBoolean();
        return new ServerTankShoot(id,number,shootSuccess);
    }
    public void run(Supplier<NetworkEvent.Context> supplier){
        supplier.get().enqueueWork(()->{
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.getVehicle() instanceof TankEntity tank) {
                tank.getCannonballs().get(id).setNumber(currentCannonballNumber);
                if(shootSuccess)tank.resetReloadTime();
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
