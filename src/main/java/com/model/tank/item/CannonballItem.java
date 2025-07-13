package com.model.tank.item;

import com.model.tank.utils.CannonballType;
import net.minecraft.world.item.Item;

public class CannonballItem extends Item {
    private CannonballType type = CannonballType.HE;
    public CannonballItem(Properties pProperties) {
        super(pProperties);
    }
}
