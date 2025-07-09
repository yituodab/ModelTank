package com.model.tank.resource.client;

import com.model.tank.ModularTank;
import com.model.tank.resource.DataManager;
import com.model.tank.utils.FileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class AssetsLoader {
    public static void loadAssetsFromDir(Path root){
        if(Files.isDirectory(root)){
            try{
                Files.newDirectoryStream(root).forEach(path -> {
                    try {
                        Files.newDirectoryStream(path.resolve("lang")).forEach(
                                LanguageLoader::load
                        );
                        Files.newDirectoryStream(path.resolve("models")).forEach(model ->
                                loadModelFromFile(path.getFileName().toString(), model)
                        );
                        Files.newDirectoryStream(path.resolve("textures")).forEach(texture ->
                                loadTextureFromFile(path.getFileName().toString(), texture)
                        );
                    } catch (IOException e) {
                        ModularTank.LOGGER.error("error",e);
                    }
                });
            } catch (IOException e) {
                ModularTank.LOGGER.error("error",e);
            }
        }
    }
    public static void loadModelFromFile(String modid, Path path){
        try(InputStream inputStream = Files.newInputStream(path)){
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Model model = JsonUtil.GEO_GSON.fromJson(json, Model.class);
            DataManager.MODELS.put(new ResourceLocation(modid, path.getFileName().toString()), model);
        } catch (Exception e) {
            ModularTank.LOGGER.error("load tank data fail,because",e);
        }
    }
    public static void loadTextureFromFile(String modid, Path path){
        try{
            FileTexture texture = new FileTexture(new ResourceLocation(modid, path.getFileName().toString()),path);
            Minecraft.getInstance().getTextureManager().register(texture.getID(),texture);
        } catch (Exception e) {
            ModularTank.LOGGER.error("load tank data fail,because",e);
        }
    }
}
