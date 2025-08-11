package com.model.tank.item;

import com.model.tank.api.nbt.TankBoxDataManager;
import com.model.tank.entities.TankEntity;
import com.model.tank.init.ModEntities;
import com.model.tank.resource.DataLoader;
import com.model.tank.resource.data.index.TankIndex;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TankBoxItem extends Item implements TankBoxDataManager {
    public TankBoxItem() {
        super(new Properties().stacksTo(1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Component getName(ItemStack pStack) {
        TankIndex tank = DataLoader.getTankIndex(this.getTankID(pStack));
        if(tank != null)return Component.translatable(tank.getDisplay().getName());
        return super.getName(pStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide()){
            BlockHitResult hitResult = getPlayerPOVHitResult(pLevel,pPlayer, ClipContext.Fluid.ANY);
            ItemStack item = pPlayer.getItemInHand(pUsedHand);
            if(hitResult.getType() == HitResult.Type.MISS) {
                return InteractionResultHolder.pass(item);
            }
            ResourceLocation tankID = this.getTankID(item);
            TankIndex tank = DataLoader.getTankIndex(tankID);
            if(tank != null){
                TankEntity tankEntity = new TankEntity(ModEntities.TANKENTITY.get(), pLevel, tank, tankID);
                tankEntity.setPos(hitResult.getLocation());
                pLevel.addFreshEntity(tankEntity);
                if(!pPlayer.isCreative())item.setCount(item.getCount() - 1);
                return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
            }
            return InteractionResultHolder.pass(item);
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
