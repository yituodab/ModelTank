package com.model.tank.init;

import com.model.tank.commands.RootCommandAndSubCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommandRegister {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event){RootCommandAndSubCommands.register(event.getDispatcher());}
}
