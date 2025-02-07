package com.model.tank.commands;

import com.model.tank.entities.tank.TankEntity;
import com.model.tank.init.EntityRegister;
import com.model.tank.resource.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sun.jdi.connect.Connector;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.registries.Registries;
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
        String tankID = StringArgumentType.getString(context, "id");
        ServerLevel level = context.getSource().getLevel();
        ResourceLocation testID = new ResourceLocation("mrt:test");
        TankEntity tank = new TankEntity(EntityRegister.TANKENTITY.get(), level, DataManager.TANKS.get(testID), testID) ;
        tank.setPos(context.getSource().getPosition());
        context.getSource().getLevel().addFreshEntity(tank);
        return 0;
    }
    public static int reloadPack(CommandContext<CommandSourceStack> context) {
        DataManager.load();
        return 0;
    }
}
