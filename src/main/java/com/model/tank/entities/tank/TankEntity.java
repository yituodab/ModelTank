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
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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
    private Map<Cannonball, Integer> cannonballs = new HashMap<>();
    private Cannonball currentCannonball;
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
        for (ResourceLocation id : tank.cannonballs) {
            CannonballData cannonball = DataManager.CANNONBALLS.get(id);
            cannonballs.put(new Cannonball(id, cannonball), 0);
        }
        this.setBoundingBox(EntityDimensions.scalable(tank.boundingBox[0],tank.boundingBox[1]).makeBoundingBox(position()));
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
    }
    public void shoot(Cannonball currentCannonball) {
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
        this.cannonballs.forEach((data, number)->{
            int i = compoundTag.getCompound("cannonballs").getInt(data.id.toString());
            cannonballs.put(data,i);
        });

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putString("TankID", this.tankID.toString());
        CompoundTag tag = new CompoundTag();
        this.cannonballs.forEach((data, number)-> tag.putInt(data.id.toString(), number));
        compoundTag.put("cannonballs", tag);
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
        friendlyByteBuf.writeResourceLocation(this.tankID);
        this.cannonballs.forEach((data, number)->{
            friendlyByteBuf.writeInt(number);
        });
    }

    @Override
    public void readSpawnData(FriendlyByteBuf friendlyByteBuf) {
        this.tankID = friendlyByteBuf.readResourceLocation();
        fromTankData(DataManager.TANKS.get(this.tankID));
        this.cannonballs.forEach((data, number)->{
            int i = friendlyByteBuf.readInt();
            cannonballs.put(data, i);
        });
    }

    public Map<Cannonball, Integer> getCannonballs() {
        return cannonballs;
    }

    public Cannonball getCurrentCannonball() {
        return this.currentCannonball;
    }

    public void setCurrentCannonball(Cannonball currentCannonball) {
        this.currentCannonball = currentCannonball;
    }

    public record Cannonball(ResourceLocation id, CannonballData data){}
}
