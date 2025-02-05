package com.model.tank.commands;

import com.model.tank.entities.tank.TankEntity;
import com.model.tank.init.EntityRegister;
import com.model.tank.resource.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;

public class RootCommandAndSubCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("mrt")
                .requires((source -> source.hasPermission(2)));
        root.then(Commands.literal("tank").executes(RootCommandAndSubCommands::summonTank));
        root.then(Commands.literal("reload").executes(RootCommandAndSubCommands::reloadPack));
        dispatcher.register(root);
    }
    public static int summonTank(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel level = context.getSource().getLevel();
        TankEntity tank = new TankEntity(EntityRegister.TANKENTITY.get(), level, DataManager.TANKS.get("mrt:test")) ;
        tank.setPos(context.getSource().getPosition());
        context.getSource().getLevel().addFreshEntity(tank);
        return 0;
    }
    public static int reloadPack(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        DataManager.load();
        return 0;
    }
}
