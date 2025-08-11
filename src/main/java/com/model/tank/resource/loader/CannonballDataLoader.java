package com.model.tank.resource.loader;

import com.model.tank.ModularTank;
import com.model.tank.resource.DataLoader;
import com.model.tank.resource.data.tank.CannonballData;
import com.model.tank.resource.data.tank.TankData;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CannonballDataLoader {
    public static void loadCannonballDataFromDir(Path namespaceDir){
        Path cannonballDir = namespaceDir.resolve("cannonballs");
        if(!cannonballDir.toFile().isDirectory())return;
        try{
            Files.newDirectoryStream(cannonballDir).forEach(path -> {
                try(InputStream inputStream = Files.newInputStream(path)) {
                    String namespace = namespaceDir.getFileName().toString();
                    String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                    CannonballData tank = DataLoader.GSON.fromJson(json, CannonballData.class);
                    DataLoader.putCannonballData(new ResourceLocation(namespace, path.getFileName().toString().replace(".json", "")), tank);
                } catch (Exception e) {
                    ModularTank.LOGGER.error("Load {} failed,because", path, e);
                }
            });
        } catch (Exception e) {
            ModularTank.LOGGER.error("Load {} failed,because", cannonballDir,e);
        }
    }
}
