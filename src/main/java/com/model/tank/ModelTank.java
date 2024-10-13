package com.model.tank;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.model.tank.entities.tanks.TankEntity;
import com.model.tank.entities.tanks.TankRender;
import com.model.tank.utils.ModCreativeTab;
import com.model.tank.init.TankRegister;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModelTank.MODID)
public class ModelTank
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "modeltank";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    

    public ModelTank()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addToTab);


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        try{
            File file = FMLPaths.CONFIGDIR.get().resolve("modeltank/tank.json").toFile();
            //if(!file.exists())
                copyResourceToFile("/assets/modeltank/tank.json","config/modeltank/tank.json");
            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            JsonObject jsonObject;
            jsonObject = gson.fromJson(reader, JsonObject.class);
            TankRegister.register(modEventBus, jsonObject);
            LOGGER.info("Load Successful");
        } catch (Exception e) {
            LOGGER.error("Couldn’t register tanks,because ",e);
        }
        ModCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    public  void copyResourceToFile(String resourcePath, String targetPath) {
        // 获取Minecraft实例目录
        File mcDir = FMLPaths.GAMEDIR.get().toFile();
        // 构建目标目录
        File targetDir = new File(mcDir, targetPath).getParentFile();
        // 创建目录
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            throw new IllegalStateException("Could not create directory: " + targetDir.getAbsolutePath());
        }
        // 构建目标文件
        File targetFile = new File(targetDir, new File(targetPath).getName());
        // 使用Java NIO来复制文件，简化代码
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy file from " + resourcePath + " to " + targetFile, e);
        }
    }
    public void addToTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTab() == ModCreativeTab.TANK_TAB.get()){
            TankRegister.TANKS.forEach((name, tank) -> {
                ItemStack item = TankRegister.TANKITEM.get().getDefaultInstance();
                item.setHoverName(Component.translatable(name));
                item.getOrCreateTag().putString("tank", name);
                event.accept(item);
            });
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

        // Do something when the server starts
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(()->{
                TankRegister.TANKENTITYS.forEach((e,a)-> {
                    EntityRenderers.register(a.get(), TankRender::new);
                });
            });
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
