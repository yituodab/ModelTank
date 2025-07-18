package com.model.tank.entities;


import com.model.tank.api.client.entity.ModEntity;
import com.model.tank.api.client.interfaces.IEntity;
import com.model.tank.init.EntityRegister;
import com.model.tank.network.NetWorkManager;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.CannonballData;
import com.model.tank.resource.data.Module;
import com.model.tank.resource.data.Tank;


import com.model.tank.utils.ModDimensions;
import com.model.tank.utils.VehicleMoveType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TankEntity extends ModEntity implements IEntityAdditionalSpawnData {
    private Map<Cannonball, Integer> cannonballs = new HashMap<>();
    private Cannonball currentCannonball;
    private ResourceLocation modelLocation;
    private ResourceLocation textureLocation;
    private ResourceLocation tankID;
    private List<Module> modules;
    private List<Module.Armor> armors;
    private float tickSteeringSpeed;
    private double tickSpeed;
    private double tickBackSpeed;
    private float tickAcceleration;
    private float addSpeed;
    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;
    public TankEntity(EntityType<?> p_19870_, Level p_19871_, Tank tank, ResourceLocation id) {
        super(p_19870_, p_19871_);
        this.tankID = id;
        fromTankData(tank);
    }
    public void fromTankData(Tank tank){
        this.modelLocation = tank.modelLocation;
        this.textureLocation = tank.textureLocation;
        this.modules = List.of(tank.modules.clone());
        this.armors = List.of(tank.armors);
        this.tickSteeringSpeed = tank.steeringSpeed / 20;
        this.tickAcceleration = tank.acceleration /20;
        this.tickBackSpeed = tank.backSpeed / 20;
        this.tickSpeed = tank.maxSpeed / 20;
        for (ResourceLocation id : tank.cannonballs) {
            CannonballData cannonballData = DataManager.CANNONBALLS.get(id);
            if(cannonballData == null)continue;
            Cannonball cannonball = new Cannonball(id, DataManager.CANNONBALLS.get(id));
            cannonballs.put(cannonball, 0);
            this.currentCannonball = cannonball;
        }
        ((IEntity)this).setDimensions(new ModDimensions(tank.boundingBox[0],tank.boundingBox[1],tank.boundingBox[2],true));
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module.Armor> getArmors() {
        return armors;
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if(pPlayer.isSecondaryUseActive())return InteractionResult.PASS;
        if(!this.level().isClientSide && !this.isVehicle())
            return pPlayer.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        else return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        Entity controllingPassenger = null;
        if(!this.getPassengers().isEmpty())controllingPassenger = this.getPassengers().get(0);
        return controllingPassenger instanceof Player player ? player : super.getControllingPassenger();
    }

    @Override
    protected boolean canAddPassenger(Entity pPassenger) {
        return !this.isVehicle() && pPassenger instanceof Player;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return ((IEntity)this).getDimensions();
    }

    public double getCurrentSpeed() {
        return Vec3.ZERO.distanceTo(this.getDeltaMovement());
    }

    @Override
    public void refreshDimensions() {
    }

    @Override
    protected AABB makeBoundingBox() {
        if(((IEntity)this).getDimensions() instanceof ModDimensions modDimensions)
            return modDimensions.makeBoundingBox(this.position(),this.getXRot(),this.getYRot());
        return super.makeBoundingBox();
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()){
            controlTank();
        }
        this.move(MoverType.SELF,getDeltaMovement());
    }

    @Deprecated
    public TankEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }
    public void shoot() {
        Level level = this.level();
        Cannonball currentCannonball = getCurrentCannonball();
        CannonballData cannonballData = currentCannonball.data;
        if(cannonballData != null){
            if(cannonballs.get(currentCannonball) > 0){
                CannonballEntity cannonball = new CannonballEntity(EntityRegister.CANNONBALLENTITY.get(), level, this, cannonballData, currentCannonball.id);
                cannonball.setPos(this.position());
                cannonball.shoot(this,this.getXRot(),this.getYRot());
                level.addFreshEntity(cannonball);
            }
        }
    }
    public void setInput(boolean up,boolean down,boolean left,boolean right){
        inputUp = up;
        inputDown = down;
        inputLeft = left;
        inputRight = right;
    }

    public void controlTank() {
        if(this.isVehicle()){
            Vec3 deltaMovement = getDeltaMovement();
            double currentSpeed = getCurrentSpeed();
            float yRot = getYRot();
            if(inputLeft){
                yRot -= tickSteeringSpeed;
            }
            if(inputRight) {
                yRot += tickSteeringSpeed;
            }
            this.setRot(yRot,getXRot());
            if(!inputUp && !inputDown){
                addSpeed = addSpeed < 0 ? -0.005F : 0.005F;
            }
            if(inputUp && currentSpeed < tickSpeed){
                if(tickSpeed-currentSpeed<tickAcceleration)
                    addSpeed += (tickSpeed-currentSpeed)/tickSpeed;
                else
                    addSpeed += 1F;
            }
            if(inputDown && !(addSpeed <= 0 && currentSpeed > tickBackSpeed)){
                addSpeed -= 0.5F;
            }
            deltaMovement = new Vec3(Mth.sin(-this.getYRot() * 0.017453292F)*tickAcceleration*addSpeed,deltaMovement.y,
                    Mth.cos(this.getYRot() * 0.017453292F)*tickAcceleration*addSpeed);
            this.setDeltaMovement(deltaMovement);
        }
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
        this.cannonballs.forEach((data, number)-> friendlyByteBuf.writeInt(number));
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
