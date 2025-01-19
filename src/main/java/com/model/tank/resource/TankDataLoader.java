package com.model.tank.resource;

import com.google.gson.Gson;
import com.model.tank.ModelTank;
import com.model.tank.resource.data.Tank;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TankDataLoader {
    public static void loadTankDataFromDir(Path path){
        if(Files.isDirectory(path)){
            for (File file : path.toFile().listFiles()) {
                loadTankDataFromFile(file);
            }
        }
    }
    public static void loadTankDataFromFile(File file){
        Gson gson = new Gson();
        try(InputStream inputStream = Files.newInputStream(file.toPath())){
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Tank tank = gson.fromJson(json, Tank.class);
            DataManager.TANKS.put(file.getName(), tank);
        } catch (Exception e) {
            ModelTank.LOGGER.error("load tank data fail,because",e);
        }
    }
}
