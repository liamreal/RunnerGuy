package com.liamreal.assets;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AssetManagerTest {
    @TempDir
    Path tempDirectory;

    @BeforeEach
    void setUp() {
        // IMPORTANT ASSUMPTION AT START OF GAME
        AssetConfig.setAssetType("default");
    }

    @Test
    void constructor_buildsAssetMap() throws Exception {
        // create file in root
        Files.createFile(tempDirectory.resolve("player.png"));
        AssetManager<String> manager =
                new AssetManager<>(
                    tempDirectory,
                    path -> "loaded:" + path.getFileName() // using lambda functions on path to apply function "loaded:"+path.getFileName()
                );
        // should give result of "loaded:player.png" which was applied on "player.png"
        assertEquals(
                "loaded:player.png",
                manager.getAsset("player.png")
        );
    }

    @Test
    void getAsset_missingKeyReturnsNull() {
        AssetManager<String> manager =
                new AssetManager<>(
                        tempDirectory,
                        path -> "loaded:" + path.getFileName()
                );
        assertNull(manager.getAsset("missing.png"));
    }

    @Test
    void loaderReturningNull_doesNotAddAsset() throws Exception {
        Files.createFile(tempDirectory.resolve("broken.png"));
        AssetManager<String> manager =
                new AssetManager<>(
                        tempDirectory,
                        path -> null // undefined function on object should result in null, meaning not added to map
                );
        // map should be empty and so null value received when querying broken file
        assertTrue(manager.getAssetMap().isEmpty());
        assertNull(manager.getAsset("broken.png"));
    }

    @Test
    void update_loadsAssetsFromNewPath() throws Exception {
        Path firstDirectory =
                Files.createDirectory(
                        tempDirectory.resolve("first")
                );
        Path secondDirectory =
                Files.createDirectory(
                        tempDirectory.resolve("second")
                );
        Files.createFile(
                firstDirectory.resolve("player.png")
        );
        Files.createFile(
                secondDirectory.resolve("enemy.png")
        );
        AssetManager<String> manager =
                new AssetManager<>(
                        firstDirectory,
                        path -> "loaded:" + path.getFileName()
                );
        assertEquals(
                "loaded:player.png",
                manager.getAsset("player.png")
        );
        manager.update(secondDirectory);
        assertEquals(
                "loaded:enemy.png",
                manager.getAsset("enemy.png")
        );
    }


    @Test
    void update_overwritesExistingAssetNewPath() throws Exception {
        Path firstDirectory =
                Files.createDirectory(
                        tempDirectory.resolve("first")
                );
        Path secondDirectory =
                Files.createDirectory(
                        tempDirectory.resolve("second")
                );
        Files.createFile(
                firstDirectory.resolve("player.png")
        );
        Files.createFile(
                secondDirectory.resolve("player.png")
        );
        // create AssetManager of paths to check that paths are updated (file same name)
        AssetManager<Path> manager =
                new AssetManager<>(
                        firstDirectory,
                        path -> path
                );
        assertEquals(
                firstDirectory.resolve("player.png"),
                manager.getAsset("player.png")
        );
        manager.update(secondDirectory);
        assertEquals(
                secondDirectory.resolve("player.png"),
                manager.getAsset("player.png")
        );
    }

    @Test
    void update_missingAsset_keepsExistingAsset() throws Exception {
        Path firstDirectory =
                Files.createDirectory(
                        tempDirectory.resolve("first")
                );
        Path secondDirectory =
                Files.createDirectory(
                        tempDirectory.resolve("second")
                );
        Files.createFile(
                firstDirectory.resolve("player.png")
        );
        AssetManager<Path> manager =
                new AssetManager<>(
                        firstDirectory,
                        path -> path
                );
        manager.update(secondDirectory);
        // file should only be found in first directory so keeps that on
        assertEquals(
                firstDirectory.resolve("player.png"),
                manager.getAsset("player.png")
        );
    }

    @Test
    void update_loaderReturnsNull_keepsExistingAsset() throws Exception {
        Path defaultDirectory =
                Files.createDirectory(tempDirectory.resolve("default"));
        Path overrideDirectory =
                Files.createDirectory(tempDirectory.resolve("override"));
        Files.createFile(defaultDirectory.resolve("player.png"));
        Files.createFile(overrideDirectory.resolve("player.png"));
        AssetManager<String> manager =
                new AssetManager<>(
                        defaultDirectory,
                        path -> path.startsWith(defaultDirectory)
                                ? "default-player"
                                : null
                );
        // should be default-player to start with
        assertEquals(
                "default-player",
                manager.getAsset("player.png")
        );
        manager.update(overrideDirectory);
        // loader fails on second file (corrupt or incorrect format) so does not update; defaults to existing file
        assertEquals(
                "default-player",
                manager.getAsset("player.png")
        );
    }
}