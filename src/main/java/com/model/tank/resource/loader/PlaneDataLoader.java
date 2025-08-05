package com.model.tank.resource.loader;

import com.google.gson.Gson;
import com.model.tank.ModularTank;
import com.model.tank.resource.data.Plane;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlaneDataLoader {
    public static void loadTankDataFromDir(Path path){
        if(Files.isDirectory(path)){
            for (File file : path.toFile().listFiles()) {
                loadPlaneDataFromFile(file);
            }
        }
    }
    public static void loadPlaneDataFromFile(File file){
        Gson gson = new Gson();
        try(InputStream inputStream = Files.newInputStream(file.toPath())){
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Plane plane = gson.fromJson(json, Plane.class);
            //DataManager.PLANES.put(file.getName(), plane);
        } catch (Exception e) {
            ModularTank.LOGGER.error("load plane data fail,because",e);
        }
    }
}
