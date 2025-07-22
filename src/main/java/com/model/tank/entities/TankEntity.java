package com.model.tank.entities;


import com.model.tank.api.client.entity.ModEntity;
import com.model.tank.api.client.interfaces.IEntity;
import com.model.tank.init.ModEntities;
import com.model.tank.network.NetWorkManager;
import com.model.tank.network.S2C.ServerTankShoot;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.CannonballData;
import com.model.tank.resource.data.Module;
import com.model.tank.resource.data.Tank;


import com.model.tank.utils.ModDimensions;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;


public class TankEntity extends ModEntity implements IEntityAdditionalSpawnData {
    private final Map<ResourceLocation, Cannonball> cannonballs = new HashMap<>();
    private final List<Module> modules = new ArrayList<>();
    private final Map<Integer, Integer> moduleHealth = new HashMap<>();
    private ResourceLocation currentCannonball;
    private ResourceLocation modelLocation;
    private ResourceLocation textureLocation;
    private ResourceLocation tankID;
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
    private int reloadTime = 0;
    private int maxReloadTime = 0;
    public TankEntity(EntityType<?> p_19870_, Level p_19871_, Tank tank, ResourceLocation id) {
        super(p_19870_, p_19871_);
        this.tankID = id;
        fromTankData(tank);
    }
    public void fromTankData(Tank tank){
        this.modelLocation = tank.modelLocation;
        this.textureLocation = tank.textureLocation;
        this.modules.clear();
        this.modules.addAll(List.of(tank.modules));
        this.armors = List.of(tank.armors);
        this.tickSteeringSpeed = tank.steeringSpeed / 20;
        this.tickAcceleration = tank.acceleration /20;
        this.tickBackSpeed = tank.backSpeed / 20;
        this.tickSpeed = tank.maxSpeed / 20;
        for (ResourceLocation id : tank.cannonballs) {
            CannonballData cannonballData = DataManager.CANNONBALLS.get(id);
            if(cannonballData == null)continue;
            Cannonball cannonball = new Cannonball(DataManager.CANNONBALLS.get(id), 0);
            cannonballs.put(id, cannonball);
            if(this.getCurrentCannonball() == null)this.setCurrentCannonball(id);
        }
        for (Module module : this.modules) {
            this.moduleHealth.put(module.getID(), module.getMaxHealth());
            switch (module.getType()){
                case CANNON -> {
                    this.maxReloadTime = (int)(module.getReloadTime() * 1000);
                }
            }
        }
        ((IEntity)this).setDimensions(new ModDimensions(tank.boundingBox[0],tank.boundingBox[1],tank.boundingBox[2],true));
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
        refreshReloadTime();
        this.move(MoverType.SELF,getDeltaMovement());
    }
    public boolean isReload(){
        return reloadTime > 0;
    }
    public void refreshReloadTime(){
        reloadTime = isReload() ? reloadTime - 50 : 0;
    }
    public void resetReloadTime(){
        this.reloadTime = maxReloadTime;
    }
    public boolean shoot(ResourceLocation id) {
        Level level = this.level();
        ServerPlayer player = (ServerPlayer) this.getControllingPassenger();
        Cannonball currentCannonball = cannonballs.get(id);
        CannonballData cannonballData = currentCannonball.data;
        boolean shootSuccess = false;
        if(cannonballData != null && player != null){
            // 确认玩家是否为创造模式
            boolean isCreative = player.isCreative();
            // 获取当前炮弹数
            int number = currentCannonball.getNumber();
            if((number > 0 || isCreative) && !isReload()){
                CannonballEntity cannonball = new CannonballEntity(ModEntities.CANNONBALLENTITY.get(), level, this, cannonballData, id);
                cannonball.setPos(player.getEyePosition());
                cannonball.shoot(player,player.getXRot(),player.getYRot());
                level.addFreshEntity(cannonball);
                // 减少炮弹数
                if(!isCreative)currentCannonball.setNumber(number - 1);
                cannonballs.put(id,currentCannonball);
                resetReloadTime();
                shootSuccess = true;
            }
            // 向客户端发包同步炮弹数及发射情况
            NetWorkManager.sendToPlayer(new ServerTankShoot(number,shootSuccess),player);
        }
        return shootSuccess;
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
        CompoundTag cannonballs = compoundTag.getCompound("cannonballs");
        CompoundTag modules = compoundTag.getCompound("modules");
        this.cannonballs.forEach((id, cannonball)->{
            int i = cannonballs.getInt(id.toString());
            cannonball.setNumber(i);
        });
        this.modules.forEach((module -> {
            int health = modules.getInt(String.valueOf(module.getID()));
            this.moduleHealth.replace(module.getID(),health);
        }));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putString("TankID", this.tankID.toString());
        CompoundTag cannonballs = new CompoundTag();
        CompoundTag modules = new CompoundTag();
        this.cannonballs.forEach((id, cannonball)-> cannonballs.putInt(id.toString(), cannonball.getNumber()));
        this.moduleHealth.forEach((id, health)-> modules.putInt(id.toString(),health));
        compoundTag.put("cannonballs", cannonballs);
        compoundTag.put("modules", modules);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeResourceLocation(this.tankID);
        this.cannonballs.forEach((id,cannonball)-> {
            friendlyByteBuf.writeResourceLocation(id);
            friendlyByteBuf.writeInt(cannonball.getNumber());
        });
        this.moduleHealth.forEach((id,health)->{
            friendlyByteBuf.writeInt(id);
            friendlyByteBuf.writeInt(health);
        });
    }

    @Override
    public void readSpawnData(FriendlyByteBuf friendlyByteBuf) {
        this.tankID = friendlyByteBuf.readResourceLocation();
        fromTankData(DataManager.TANKS.get(this.tankID));
        for(int i = 0;i<cannonballs.size();i++){
            ResourceLocation id = friendlyByteBuf.readResourceLocation();
            int number = friendlyByteBuf.readInt();
            cannonballs.get(id).setNumber(number);
        }
        for(int i = 0;i<moduleHealth.size();i++){
            int id = friendlyByteBuf.readInt();
            int health = friendlyByteBuf.readInt();
            moduleHealth.replace(id,health);
        }
    }

    public Map<ResourceLocation, Cannonball> getCannonballs() {return cannonballs;}
    public ResourceLocation getCurrentCannonball() {return this.currentCannonball;}
    public void setCurrentCannonball(ResourceLocation currentCannonball) {this.currentCannonball = currentCannonball;}
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {return GeckoLibUtil.createInstanceCache(this);}
    public ResourceLocation getModelLocation() {return modelLocation;}
    public ResourceLocation getTextureLocation() {return textureLocation;}
    public List<Module> getModules() {return modules;}
    public List<Module.Armor> getArmors() {return armors;}
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {return NetworkHooks.getEntitySpawningPacket(this);}
    @Deprecated
    public TankEntity(EntityType<?> p_19870_, Level p_19871_) {super(p_19870_, p_19871_);}
    public static class Cannonball{
        private final CannonballData data;
        private int number;
        public Cannonball(CannonballData data, int number){
            this.data = data;
            this.number = number;
        }
        public CannonballData getData() {
            return data;
        }
        public int getNumber() {
            return number;
        }
        public void setNumber(int number) {
            this.number = number;
        }
    }
}
