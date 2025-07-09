package com.model.tank.utils;

import com.model.tank.ModularTank;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTexture extends AbstractTexture {
    private final ResourceLocation id;
    private final Path path;
    public FileTexture(ResourceLocation id,Path path){
        this.id = id;
        this.path = path;
    }
    @Override
    public void load(ResourceManager resourceManager) throws IOException {
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(this::load);
        } else {
            this.load();
        }
    }
    private void load(){
        if(path.toFile().isFile()){
            try (InputStream stream = Files.newInputStream(path)) {
                NativeImage imageIn = NativeImage.read(stream);
                int width = imageIn.getWidth();
                int height = imageIn.getHeight();
                TextureUtil.prepareImage(this.getId(), 0, width, height);
                imageIn.upload(0, 0, 0, 0, 0, width, height, false, false, false, true);
            } catch (IOException e) {
                ModularTank.LOGGER.error("Error:",e);
            }
        }
    }

    public Path getPath() {
        return path;
    }

    public ResourceLocation getID() {
        return id;
    }
}
