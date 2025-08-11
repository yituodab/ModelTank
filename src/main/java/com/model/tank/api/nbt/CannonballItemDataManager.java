package com.model.tank.api.nbt;

import com.model.tank.utils.CannonballType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface CannonballItemDataManager {
    String CANNONBALL_TYPE = "cannonball_type";
    default CannonballType getType(ItemStack item){
        CompoundTag nbt = item.getOrCreateTag();
        if(nbt.contains(CANNONBALL_TYPE)){
            return CannonballType.valueOf(nbt.getString(CANNONBALL_TYPE));
        }
        return CannonballType.HE;
    }
}
