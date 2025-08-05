package com.model.tank.item;

import com.model.tank.entities.TankEntity;
import com.model.tank.init.ModEntities;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.index.TankIndex;
import com.model.tank.resource.data.tank.TankData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TankItem extends Item {
    public TankItem(Properties p_41383_) {
        super(p_41383_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Component getName(ItemStack pStack) {
        TankIndex tank = DataManager.getTankIndex(new ResourceLocation(pStack.getOrCreateTag().getString("TankID")));
        if(tank != null && tank.getName() != null)return Component.translatable(tank.getName());
        return super.getName(pStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide()){
            ItemStack item = pPlayer.getItemInHand(pUsedHand);
            ResourceLocation tankID = new ResourceLocation(item.getOrCreateTag().getString("TankID"));
            TankIndex tank = DataManager.getTankIndex(tankID);
            if(tank != null){
                TankEntity tankEntity = new TankEntity(ModEntities.TANKENTITY.get(), pLevel, tank, tankID);
                tankEntity.setPos(pPlayer.pick(5,0,false).getLocation());
                pLevel.addFreshEntity(tankEntity);
                if(!pPlayer.isCreative())item.setCount(item.getCount() - 1);
            }
            else return super.use(pLevel, pPlayer, pUsedHand);
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
