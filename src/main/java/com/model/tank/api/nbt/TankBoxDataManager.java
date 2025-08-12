package com.model.tank.api.nbt;

import com.model.tank.resource.DataLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface TankBoxDataManager {
    String TANK_ID = "tank_id";
    default ResourceLocation getTankID(ItemStack item){
        CompoundTag nbt = item.getOrCreateTag();
        if(!nbt.contains(TANK_ID)){
            return DataLoader.DEFAULT_TANK_ID;
        }
        return new ResourceLocation(nbt.getString(TANK_ID));
    }
}
