package com.model.tank.resource.loader;

import com.model.tank.ModularTank;
import com.model.tank.resource.DataManager;
import com.model.tank.resource.data.index.TankIndex;
import com.model.tank.resource.data.tank.TankData;
import com.model.tank.resource.data.tank.TankIndexData;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TankIndexLoader {
    public static void loadTankIndexFromDir(Path namespaceDir){
        Path tankDir = namespaceDir.resolve("index/tanks");
        if(!tankDir.toFile().isDirectory())return;
        try{
            Files.newDirectoryStream(tankDir).forEach(path -> {
                try(InputStream inputStream = Files.newInputStream(path)) {
                    String namespace = namespaceDir.getFileName().toString();
                    String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                    TankIndexData tank = DataManager.GSON.fromJson(json, TankIndexData.class);
                    DataManager.putTankIndex(new ResourceLocation(namespace, path.getFileName().toString().replace(".json", "")), tank);
                } catch (Exception e) {
                    ModularTank.LOGGER.error("Load {} failed,because", path, e);
                }
            });
        } catch (Exception e) {
            ModularTank.LOGGER.error("Load {} failed,because", tankDir,e);
        }
    }
}
