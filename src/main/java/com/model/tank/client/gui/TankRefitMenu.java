package com.model.tank.client.gui;

import com.model.tank.entities.TankEntity;
import com.model.tank.init.ModScreens;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class TankRefitMenu extends AbstractContainerMenu {
    public TankRefitMenu(int pContainerId, Inventory inventory) {
        super(ModScreens.TANK_REFIT_MENU.get(), pContainerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }
    public void addCannonball(TankEntity tank, Player player){
        player.getInventory().items.forEach(item->{

        });
    }
}
