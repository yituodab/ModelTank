package com.model.tank.init;

import com.model.tank.ModelTank;
import com.model.tank.init.TankRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModelTank.MODID);
    public static final RegistryObject<CreativeModeTab> TANK_TAB = CREATIVE_MODE_TABS.register("tank_tab",
            () -> CreativeModeTab.builder().icon(ModCreativeTab::getIcon).
                    title(Component.translatable("itemGroup.modeltank")).
                    displayItems((parameters, output)->{
                    })
                    .build());
    private static ItemStack getIcon(){
        ItemStack itemStack = TankRegister.TANKITEM.get().getDefaultInstance();
        itemStack.setHoverName(Component.translatable("item.modeltank.tank"));
        itemStack.getOrCreateTag().putString("tank","default");
        return itemStack;
    }
}
