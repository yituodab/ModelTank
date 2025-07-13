package com.model.tank.init;

import com.model.tank.ModularTank;
import com.model.tank.entities.CannonballEntity;
import com.model.tank.entities.TankEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ModularTank.MODID);

    public static final RegistryObject<EntityType<TankEntity>> TANKENTITY = ENTITY_TYPES.register("tank", () -> EntityType.Builder.
                    <TankEntity>of(TankEntity::new, MobCategory.MISC).noSummon().
            build(new ResourceLocation(ModularTank.MODID, "tank").toString()));
    public static final RegistryObject<EntityType<CannonballEntity>> CANNONBALLENTITY = ENTITY_TYPES.register("cannonball", () -> EntityType.Builder.
                    <CannonballEntity>of(CannonballEntity::new, MobCategory.MISC).noSummon().noSave().sized(0.1f,0.1f).
            build(new ResourceLocation(ModularTank.MODID, "cannonball").toString()));


}
