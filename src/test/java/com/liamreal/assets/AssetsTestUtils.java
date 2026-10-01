package com.liamreal.assets;

import java.nio.file.Files;
import java.nio.file.Path;

public class AssetsTestUtils {
    // helps with creating files in a dir
    public static Path createFile(Path directory, String fileName) throws Exception {
        return Files.createFile(directory.resolve(fileName));
    }
    // helps with resolving dirs
    public static Path createDirectory(Path directory, String resolution) throws Exception {
        return Files.createDirectory(directory.resolve(resolution));
    }
}
