package com.model.tank.resource;

import com.google.gson.Gson;
import com.model.tank.ModularTank;
import com.model.tank.resource.data.Tank;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TankDataLoader {
    public static void loadTankDataFromDir(Path root){
        if(Files.isDirectory(root)){
            try{
                Files.newDirectoryStream(root).forEach(path -> {
                    try {
                        Files.newDirectoryStream(path).forEach(tank -> loadTankDataFromFile(path.getFileName().toString(), tank));
                    } catch (IOException e) {
                        ModularTank.LOGGER.error("error",e);
                    }
                });
            } catch (IOException e) {
                ModularTank.LOGGER.error("error",e);
            }
        }
    }
    public static void loadTankDataFromFile(String modid, Path path){
        try(InputStream inputStream = Files.newInputStream(path)){
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Tank tank = DataManager.GSON.fromJson(json, Tank.class);
            DataManager.TANKS.put(new ResourceLocation(modid, tank.id), tank);
        } catch (Exception e) {
            ModularTank.LOGGER.error("load tank data fail,because",e);
        }
    }
}
