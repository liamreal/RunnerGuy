package com.liamreal.assets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AssetTestUtils {
    // helps with creating files in a dir
    public static Path createFile(Path directory, String fileName) throws Exception {
        return Files.createFile(directory.resolve(fileName));
    }
    // helps with resolving a directory
    public static Path createDirectory(Path directory, String resolution) throws Exception {
        return Files.createDirectory(directory.resolve(resolution));
    }
    // helps with resolving subdirectories
    public static Path createDirectory(Path directory, List<String> resolutions) throws Exception {
        for (int i = 0; i < resolutions.size(); i++) {
            directory = createDirectory(directory, resolutions.get(i));
        }
        return directory;
    }
}
