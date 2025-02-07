package com.model.tank.entities.tank;


import com.model.tank.entities.cannonball.CannonballEntity;
import com.model.tank.init.EntityRegister;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.CannonballData;
import com.model.tank.resource.data.Module;
import com.model.tank.resource.data.Tank;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TankEntity extends Entity implements GeoEntity, IEntityAdditionalSpawnData {
    private Map<Tank.Cannonball, Integer> cannonballs = new HashMap<>();
    private Tank.Cannonball currentCannonball;
    private ResourceLocation modelLocation;
    private ResourceLocation textureLocation;
    public double maxSpeed;// m/s
    public int MaxPassenger;
    private ResourceLocation tankID;
    public List<Module> modules;
    //public List<Module.Armor> armors;
    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;
    private double tickSpeed = maxSpeed / 20;
    public TankEntity(EntityType<?> p_19870_, Level p_19871_, Tank tank, ResourceLocation id) {
        super(p_19870_, p_19871_);
        this.tankID = id;
        fromTankData(tank);

    }
    public void fromTankData(Tank tank){
        this.modelLocation = tank.modelLocation;
        this.textureLocation = tank.textureLocation;
        this.modules = List.of(tank.modules.clone());
        for (Tank.Cannonball cannonball : tank.cannonballs) {
            if(currentCannonball == null)currentCannonball = cannonball;
            cannonballs.put(cannonball, 0);
        }
        //this.armors = List.of(tank.armors);
        this.MaxPassenger = tank.maxPassenger;
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if(pPlayer.isLocalPlayer())return InteractionResult.PASS;
        return pPlayer.startRiding(this) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    protected void addPassenger(Entity pPassenger) {
        super.addPassenger(pPassenger);
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);
    }

    @Override
    protected boolean canAddPassenger(Entity pPassenger) {
        return this.getPassengers().isEmpty() && pPassenger instanceof Player;
    }


    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()){
            controlTank();
        }
    }

    @Override
    public boolean isVehicle() {
        return true;
    }

    public TankEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public void load(CompoundTag p_20259_) {
        super.load(p_20259_);
        //this.tank = EntityRegister.TANKS.get(p_20259_.getString("tank"));
    }
    public void shoot() {
        Level level = this.level();
        CannonballData cannonballData = DataManager.CANNONBALLS.get(currentCannonball.id);
        if(cannonballData != null && cannonballData.id != null){
            CannonballEntity cannonball = new CannonballEntity(EntityRegister.CANNONBALLENTITY.get(), level, this, cannonballData);
            cannonball.setPos(this.position());
            cannonball.shoot(this,this.getXRot(),this.getYRot());
            level.addFreshEntity(cannonball);
        }
    }

    public void controlTank() {
    }
    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.tankID = new ResourceLocation(compoundTag.getString("TankID"));
        fromTankData(DataManager.TANKS.get(this.tankID));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putString("TankID", this.tankID.toString());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return GeckoLibUtil.createInstanceCache(this);
    }

    public ResourceLocation getModelLocation() {
        return modelLocation;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(cannonballs.size());
        cannonballs.forEach((cannonball,c)->{
            friendlyByteBuf.writeUtf(cannonball.name);
            friendlyByteBuf.writeResourceLocation(cannonball.id);
        });
        friendlyByteBuf.writeResourceLocation(this.modelLocation);
        friendlyByteBuf.writeResourceLocation(this.textureLocation);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf friendlyByteBuf) {
        int size = friendlyByteBuf.readInt();
        for(int i = 0;i<size;i++){
            String name = friendlyByteBuf.readUtf();
            ResourceLocation id = friendlyByteBuf.readResourceLocation();
            Tank.Cannonball cannonball = new Tank.Cannonball();
            cannonball.name = name;
            cannonball.id = id;
            cannonballs.put(cannonball,0);
        }
        this.modelLocation = friendlyByteBuf.readResourceLocation();
        this.textureLocation = friendlyByteBuf.readResourceLocation();
    }

    public Map<Tank.Cannonball, Integer> getCannonballs() {
        return cannonballs;
    }
}
