package com.model.tank;

import com.model.tank.client.key.AimKey;
import com.model.tank.entities.tank.TankRender;
import com.model.tank.hud.TankHUD;
import com.model.tank.init.EntityRegister;
import com.model.tank.init.ItemRegister;
import com.model.tank.init.ModCreativeTab;
import com.model.tank.network.NetWorkManager;
import com.model.tank.resource.DataManager;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
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
import org.slf4j.Logger;

@Mod(ModularTank.MODID)
public class ModularTank
{
    public static boolean IsInGame = Minecraft.getInstance().isWindowActive();
    public static final boolean TACZ_LOADED = ModList.get().isLoaded("tacz");
    public static final String MODID = "mrt";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogUtils.getLogger();
    

    public ModularTank()
    {
        DataManager.load();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addToTab);
        modEventBus.addListener(this::addCreative);
        ItemRegister.ITEMS.register(modEventBus);
        EntityRegister.ENTITY_TYPES.register(modEventBus);
        ModCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }
    public void addToTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTab() == ModCreativeTab.TANK_TAB.get()){
            DataManager.TANKS.forEach((id, tank) -> {
                ItemStack item = ItemRegister.TANKITEM.get().getDefaultInstance();
                item.setHoverName(Component.translatable(tank.name));
                item.getOrCreateTag().putString("TankID", id.toString());
                event.accept(item);
            });
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(NetWorkManager::init);
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
        public static void hudRegister(RegisterGuiOverlaysEvent event){event.registerAboveAll("tank_hud", new TankHUD());}
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(()->{
                    EntityRenderers.register(EntityRegister.TANKENTITY.get(), TankRender::new);
            });
        }
    }
}
