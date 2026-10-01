package com.liamreal.assets;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.util.List;
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
        texturesDirectory = AssetTestUtils.createDirectory(tempDirectory, "textures");
        playerDirectory = AssetTestUtils.createDirectory(texturesDirectory, "player");
        enemyDirectory = AssetTestUtils.createDirectory(texturesDirectory, "enemy");
    }

    @Test
    void buildPathMap_emptyDirectory_returnsEmptyMap() {
        Map<String, Path> result = PathBuilder.buildPathMap(tempDirectory);

        assertTrue(result.isEmpty());
    }

    @Test
    void buildPathMap_singleRootFile_returnsCorrectEntry() throws Exception {
        Path file = AssetTestUtils.createFile(texturesDirectory, "bullet.png");

        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(file, result.get("bullet.png"));
    }

    @Test
    void buildPathMap_nestedFile_returnsCorrectEntry() throws Exception {
        Path file = AssetTestUtils.createFile(playerDirectory, "player.png");

        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(file, result.get("player.png"));
    }
    
    @Test
    void buildPathMap_rootAndNestedFiles_returnsRootFile() throws Exception {
        Path rootFile = AssetTestUtils.createFile(texturesDirectory, "background.png");

        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(rootFile, result.get("background.png"));
    }
    
    @Test
    void buildPathMap_rootAndNestedFiles_returnsNestedFile() throws Exception {
        AssetTestUtils.createFile(texturesDirectory, "background.png");
        Path nestedFile = AssetTestUtils.createFile(playerDirectory, "player.png");

        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(nestedFile, result.get("player.png"));
    }

    @Test
    void buildPathMap_allFiles_mapHasAllFiles() throws Exception {
        AssetTestUtils.createFile(texturesDirectory, "background.png");
        AssetTestUtils.createFile(playerDirectory, "player.png");
        AssetTestUtils.createFile(enemyDirectory, "enemy.png");

        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(3, result.size());
    }

    @Test
    void buildPathMap_multipleRecursionLevels_returnsDeepestFile() throws Exception {
        Path animations = AssetTestUtils.createDirectory(playerDirectory, List.of("animations", "run"));
        Path file = AssetTestUtils.createFile(animations, "frame1.png");

        Map<String, Path> result = PathBuilder.buildPathMap(texturesDirectory);

        assertEquals(file, result.get("frame1.png"));
    }
}