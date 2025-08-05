package com.model.tank.init;

import com.model.tank.ModularTank;
import com.model.tank.client.gui.TankRefitMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModScreens {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModularTank.MODID);
    public static final Supplier<MenuType<TankRefitMenu>> TANK_REFIT_MENU =
            MENU_TYPES.register("tank_refit_menu", () -> IForgeMenuType.create((id, inventory, data)->new TankRefitMenu(id,inventory)));
}
