package com.model.tank;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ModularTank.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Integer> builder = BUILDER.define("sb", 0);


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
    }
    public static ForgeConfigSpec init(){
        return BUILDER.build();
    }
}
