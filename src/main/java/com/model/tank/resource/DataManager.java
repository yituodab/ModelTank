package com.model.tank.resource;

import com.model.tank.ModelTank;
import com.model.tank.resource.data.Plane;
import com.model.tank.resource.data.Tank;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.stream.Stream;

public class DataManager {
    private static boolean firstLoad = true;
    public static final Path ModularTankDataDirPath = FMLPaths.GAMEDIR.get().resolve("mrt");
    public static final Path TankDataDir = ModularTankDataDirPath.resolve("default_pack/data/mrt/tanks");
    public static final Path PlaneDataDir = ModularTankDataDirPath.resolve("default_pack/data/mrt/planes");
    public static final HashMap<String, Tank> TANKS = new HashMap<>();
    public static final HashMap<String, Plane> PLANES = new HashMap<>();
    public static void load(){
        if(firstLoad){
            copyModDirectory(ModelTank.class,"/assets/modeltank/default_pack", ModularTankDataDirPath, "default_pack");
            firstLoad = false;
        }
        TankDataLoader.loadTankDataFromDir(TankDataDir);
        PlaneDataLoader.loadTankDataFromDir(PlaneDataDir);
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
