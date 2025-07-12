package com.model.tank.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.tank.ModularTank;
import com.model.tank.resource.client.AssetsLoader;
import com.model.tank.resource.data.CannonballData;
import com.model.tank.resource.data.Tank;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import software.bernie.geckolib.loading.json.raw.Model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.stream.Stream;

@Mod.EventBusSubscriber
public class DataManager {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .create();
    private static boolean firstLoad = true;
    public static final Path MRTDataDirPath = FMLPaths.GAMEDIR.get().resolve("mrt");
    public static final HashMap<ResourceLocation, Tank> TANKS = new HashMap<>();
    public static final HashMap<ResourceLocation, CannonballData> CANNONBALLS = new HashMap<>();
    public static final HashMap<ResourceLocation, Model> MODELS = new HashMap<>();
    //public static final HashMap<String, Plane> PLANES = new HashMap<>();

    public static void loadData(){
        if(firstLoad){
            copyModDirectory(ModularTank.class, "/assets/mrt/default_pack", MRTDataDirPath, "default_pack");
            firstLoad = false;
        }
        try {
            Files.newDirectoryStream(MRTDataDirPath).forEach(path -> {
                if(Files.isDirectory(path)){
                    CannonballDataLoader.loadCannonballDataFromDir(path.resolve("cannonballs"));
                    TankDataLoader.loadTankDataFromDir(path.resolve("tanks"));
                }
            });
        } catch (IOException e) {
            ModularTank.LOGGER.error("load json failed",e);
        }
    }
    @OnlyIn(value = Dist.CLIENT)
    public static void loadAssets(){
        try {
            Files.newDirectoryStream(MRTDataDirPath).forEach(path -> {
                if(Files.isDirectory(path)){
                    AssetsLoader.loadAssetsFromDir(path.resolve("assets"));
                }
            });
        } catch (IOException e) {
            ModularTank.LOGGER.error("load json failed",e);
        }

    }
    @SubscribeEvent
    public static void atReload(AddReloadListenerEvent event){
        loadData();
        loadAssets();
    }
    /**
     * 复制本模组的文件夹到指定文件夹。将强行覆盖原文件夹。
     *
     * @param srcPath jar 中的源文件地址
     * @param root    想要复制到的根目录
     * @param path    复制后的路径
     */
    public static void copyModDirectory(Class<?> resourceClass, String srcPath, Path root, String path) {
        URL url = resourceClass.getResource(srcPath);
        try {
            if (url != null) {
                copyFolder(url.toURI(), root.resolve(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private static void copyFolder(URI sourceURI, Path targetPath) throws IOException {
        if (Files.isDirectory(targetPath)) {
            // 删掉原文件夹，达到强行覆盖的效果
            deleteFiles(targetPath);
        }
        // 使用 Files.walk() 遍历文件夹中的所有内容
        try (Stream<Path> stream = Files.walk(Paths.get(sourceURI), Integer.MAX_VALUE)) {
            stream.forEach(source -> {
                // 生成目标路径
                Path target = targetPath.resolve(sourceURI.relativize(source.toUri()).toString());
                try {
                    // 复制文件或文件夹
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(target);
                    } else {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    // 处理异常，例如权限问题等
                    e.printStackTrace();
                }
            });
        }
    }
    private static void deleteFiles(Path targetPath) throws IOException {
        Files.walkFileTree(targetPath, new SimpleFileVisitor<>() {
            // 先去遍历删除文件
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            // 再去遍历删除目录
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
