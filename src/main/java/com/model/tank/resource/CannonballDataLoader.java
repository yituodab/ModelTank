package com.model.tank.resource;

import com.model.tank.ModularTank;
import com.model.tank.resource.data.CannonballData;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CannonballDataLoader {
    public static void loadTankDataFromDir(Path root){
        if(Files.isDirectory(root)){
            try{
                Files.newDirectoryStream(root).forEach(path -> {
                    try {
                        Files.newDirectoryStream(path).forEach(cannonball -> loadTankDataFromFile(path.toFile().getName(), cannonball));
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
            CannonballData cannonball = DataManager.GSON.fromJson(json, CannonballData.class);
            String namespace = modid + ":" + cannonball.id;
            DataManager.CANNONBALLS.put(namespace, cannonball);
        } catch (Exception e) {
            ModularTank.LOGGER.error("load tank data fail,because",e);
        }
    }
}
