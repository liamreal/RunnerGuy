package com.liamreal.assets;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PathBuilderTest {

    // mocking directories
    @TempDir
    Path tempDirectory;
    // base directories needed
    private Path texturesDirectory;
    private Path playerDirectory;
    private Path enemyDirectory;

    @BeforeEach
    void setUp() throws Exception {
        texturesDirectory = Files.createDirectories(
                tempDirectory.resolve("textures"));
        playerDirectory = Files.createDirectories(
                texturesDirectory.resolve("player"));
        enemyDirectory = Files.createDirectories(
                texturesDirectory.resolve("enemy"));
    }

    @Test
    void buildPathMap_emptyDirectory_returnsEmptyMap() {
        Map<String, Path> result = PathBuilder.buildPathMap(tempDirectory);

        assertTrue(result.isEmpty());
    }

    @Test
    void buildPathMap_singleRootFile_returnsCorrectEntry() throws Exception {
        Path file = Files.createFile(
            texturesDirectory.resolve("bullet.png"));
        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(1, result.size());
        assertEquals(file, result.get("bullet.png"));
    }

    @Test
    void buildPathMap_nestedFile_returnsCorrectEntry() throws Exception {
        Path file = Files.createFile(
            playerDirectory.resolve("player.png"));
        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(1, result.size());
        assertEquals(file, result.get("player.png"));
    }
    
    @Test
    void buildPathMap_rootAndNestedFiles_returnsBothEntries() throws Exception {
        Path rootFile = Files.createFile(
            texturesDirectory.resolve("background.png"));
        Path nestedFile = Files.createFile(
            playerDirectory.resolve("player.png"));
        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(2, result.size());
        assertEquals(rootFile, result.get("background.png"));
        assertEquals(nestedFile, result.get("player.png"));
    }


    @Test
    void buildPathMap_allFiles_returnsEveryFile() throws Exception {
        Path rootFile = Files.createFile(
            texturesDirectory.resolve("background.png"));
        Path playerFile = Files.createFile(
            playerDirectory.resolve("player.png"));
        Path enemyFile = Files.createFile(
            enemyDirectory.resolve("enemy.png"));
        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(3, result.size());
        assertEquals(rootFile, result.get("background.png"));
        assertEquals(playerFile, result.get("player.png"));
        assertEquals(enemyFile, result.get("enemy.png"));
    }

    @Test
    void buildPathMap_multipleRecursionLevels_returnsDeepestFile() throws Exception {
        Path animations = Files.createDirectories(
            playerDirectory.resolve("animations")
                            .resolve("run"));
        Path file = Files.createFile(
            animations.resolve("frame1.png"));
        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(1, result.size());
        assertEquals(file, result.get("frame1.png"));
    }
}