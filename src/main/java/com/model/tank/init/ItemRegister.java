package com.model.tank.init;

import com.model.tank.ModularTank;
import com.model.tank.item.TankItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ModularTank.MODID);
    public static final RegistryObject<Item> TANKITEM = ITEMS.register("tank_item", ()->
            new TankItem(new Item.Properties().stacksTo(1)));
}
