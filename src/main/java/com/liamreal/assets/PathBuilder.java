package com.liamreal.assets;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class PathBuilder {
    // return Map of filenames as strings mapping to the full path of that file e.g.  "bullet.png" --> assets/default/textures/items/bullet.png
    public static Map<String, Path> buildPathMap(Path directory) {
        Map<String, Path> pathMap = new HashMap<>();
        // hashmap passed into recursive function so has access to it at all levels and not creating a new one each recursion
        walkDirectory(pathMap, directory);
        return pathMap;
    }
    // recursively walks directory so need hashmap reference at all points in recursion
    private static void walkDirectory(Map<String, Path> pathMap, Path directory) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {
                // recursively go into directory
                if (Files.isDirectory(path)) {
                    walkDirectory(pathMap, path);
                } 
                else {
                    pathMap.put(
                        path.getFileName().toString(),
                        path
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to walk directory %s", directory), e);
        }
    }


    public static void main(String args[]) {
        // verify works
        System.out.println(PathBuilder.buildPathMap(Path.of(String.format("%s/textures/", AssetConfig.getAssetsPath()))));
    }
}
