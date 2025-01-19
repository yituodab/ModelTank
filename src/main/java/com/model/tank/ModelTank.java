package com.model.tank;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.model.tank.client.key.AimKey;
import com.model.tank.entities.tanks.TankRender;
import com.model.tank.init.EntityRegister;
import com.model.tank.init.ItemRegister;
import com.model.tank.init.ModCreativeTab;
import com.model.tank.resource.DataManager;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.ModSorter;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Mod(ModelTank.MODID)
public class ModelTank
{
    public static boolean IsInGame = Minecraft.getInstance().isWindowActive();
    public static final boolean TACZ_LOADED = ModList.get().isLoaded("tacz");
    public static final String MODID = "modeltank";
    public static final Logger LOGGER = LogUtils.getLogger();
    

    public ModelTank()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addToTab);
        modEventBus.addListener(this::addCreative);
        ItemRegister.ITEMS.register(modEventBus);
        EntityRegister.ENTITY_TYPES.register(modEventBus);
        ModCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        DataManager.load();
    }
    public void addToTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTab() == ModCreativeTab.TANK_TAB.get()){
            DataManager.TANKS.forEach((name, tank) -> {
                ItemStack item = ItemRegister.TANKITEM.get().getDefaultInstance();
                item.setHoverName(Component.translatable(name));
                item.getOrCreateTag().putString("tank", name);
                event.accept(item);
            });
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void keyRegister(RegisterKeyMappingsEvent event){
            event.register(AimKey.AIM_KEY);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(()->{
                    EntityRenderers.register(EntityRegister.TANKENTITY.get(), TankRender::new);
            });
        }
    }
}
