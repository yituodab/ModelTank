package com.model.tank.resource.data.index;

import com.model.tank.resource.DataLoader;
import com.model.tank.resource.client.data.tank.TankDisplay;
import com.model.tank.resource.data.tank.TankData;
import com.model.tank.resource.data.tank.TankIndexData;

public class TankIndex {
    private TankData tankData;
    private TankDisplay display;
    public TankIndex(TankIndexData index){
        this.tankData = DataLoader.getTankData(index.getTankData());
        this.display = DataLoader.getTankDisplay(index.getDisplay());
    }

    public TankData getTankData() {
        return tankData;
    }

    public TankDisplay getDisplay() {
        return display;
    }
}
