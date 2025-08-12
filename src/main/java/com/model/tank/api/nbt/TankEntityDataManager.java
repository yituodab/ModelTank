package com.model.tank.api.nbt;

import com.model.tank.entities.TankEntity;
import com.model.tank.resource.DataLoader;
import com.model.tank.resource.data.index.TankIndex;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public interface TankEntityDataManager{
    String TANK_ID = "tank_id";
    default ResourceLocation getTankID(TankEntity entity){
        CompoundTag nbt = entity.serializeNBT();
        if(!nbt.contains(TANK_ID)){
            return DataLoader.DEFAULT_TANK_ID;
        }
        return new ResourceLocation(nbt.getString("tank_id"));
    }
    void fromTankIndex(TankIndex tankIndex);
}
