package com.model.tank.commands;

import com.model.tank.entities.TankEntity;
import com.model.tank.init.EntityRegister;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.Tank;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class RootCommandAndSubCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("mrt")
                .requires((source -> source.hasPermission(2)));
        root.then(Commands.literal("tank").
                then(Commands.argument("id", StringArgumentType.string()).
                        executes(RootCommandAndSubCommands::summonTank)));
        root.then(Commands.literal("reload").executes(RootCommandAndSubCommands::reloadPack));
        dispatcher.register(root);
    }
    public static int summonTank(CommandContext<CommandSourceStack> context) {
        ResourceLocation tankID = new ResourceLocation(StringArgumentType.getString(context, "id"));
        ServerLevel level = context.getSource().getLevel();
        Tank tankData = DataManager.TANKS.get(tankID);
        if(tankData == null)return 0;
        TankEntity tank = new TankEntity(EntityRegister.TANKENTITY.get(), level, tankData, tankID) ;
        tank.setPos(context.getSource().getPosition());
        context.getSource().getLevel().addFreshEntity(tank);
        return 0;
    }
    public static int reloadPack(CommandContext<CommandSourceStack> context) {
        DataManager.loadData();
        return 0;
    }
}
