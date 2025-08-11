package com.model.tank.item;

import com.model.tank.api.nbt.CannonballItemDataManager;
import com.model.tank.utils.CannonballType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CannonballItem extends Item implements CannonballItemDataManager {
    public CannonballItem() {
        super(new Properties().stacksTo(50));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getName(ItemStack pStack) {
        return Component.translatable("mrt.cannonball.type." + this.getType(pStack).toString().toLowerCase());
    }
}
