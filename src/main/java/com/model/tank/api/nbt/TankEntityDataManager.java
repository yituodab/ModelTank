package com.model.tank.api.nbt;

import com.model.tank.resource.data.index.TankIndex;

public interface TankEntityDataManager{
    String TANK_ID = "tank_id";
    void fromTankIndex(TankIndex tankIndex);
}
