package com.model.tank.network;

import com.model.tank.ModularTank;
import com.model.tank.network.C2S.ClientTankShoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.model.tank.ModularTank.VERSION;

public class NetWorkManager {
    private static int ID = 0;
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ModularTank.MODID, "networking"),
            ()->VERSION,
            VERSION::equals, VERSION::equals
            );
    private static int getID(){
        return ID++;
    }
    public static void init(){
        CHANNEL.messageBuilder(ClientTankShoot.class,getID()).encoder(ClientTankShoot::encode).
                decoder(ClientTankShoot::decode).consumerMainThread(ClientTankShoot::run).add();
    }
}
