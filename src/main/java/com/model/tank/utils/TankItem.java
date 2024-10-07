package com.model.tank.utils;

import com.model.tank.entities.tanks.TankEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TankItem extends Item {
    public TankItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        if(!p_41432_.isClientSide){
            Tank tank = TankRegister.TANKS.get(p_41433.getItemInHand(p_41434_).getOrCreateTag().getString("tank"));
            TankEntity tankEntity = new TankEntity(TankRegister.TANKENTITYS.get(tank).get(), p_41432_, tank);
            tankEntity.setPos(p_41433_.pick(5,0,false).getLocation());
            p_41432_.addFreshEntity(tankEntity);
        }
        return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));
    }
}
