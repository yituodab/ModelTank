package com.model.tank.resource.data.index;

import com.model.tank.resource.DataManager;
import com.model.tank.resource.client.data.tank.TankDisplay;
import com.model.tank.resource.data.tank.TankData;
import com.model.tank.resource.data.tank.TankIndexData;

public class TankIndex {
    private String name;
    private TankData tankData;
    private TankDisplay display;
    public TankIndex(TankIndexData index){
        this.name = index.getName();
        this.tankData = DataManager.getTankData(index.getTankData());
        this.display = DataManager.getTankDisplay(index.getDisplay());
    }

    public String getName() {
        return name;
    }

    public TankData getTankData() {
        return tankData;
    }

    public TankDisplay getDisplay() {
        return display;
    }
}
