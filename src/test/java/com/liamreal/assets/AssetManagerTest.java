package com.liamreal.assets;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AssetManagerTest {
    @TempDir
    Path tempDirectory;

    @BeforeEach
    void setUp() {
        // IMPORTANT ASSUMPTION AT START OF GAME so set up for each test -- DO NOT CHANGE OR BREAKS TESTS
        AssetConfig.setAssetType("default");
    }

    // sample function to load asset we want
    static String loadAssetHelper(Path path) { return "loaded:" + path.getFileName(); }
    // function that intentionally fails to mock load asset failing and returning null (since asset not loaded)
    static String loadAssetNullHelper(Path path) { return null; }
    // function to return same path
    static Path loadAssetPathHelper(Path path) { return path; }
    // returns null if end of path does not match default end of path
    static String loadAssetDefaultPathEndHelper(Path path) {
		return path.getParent().getFileName().endsWith(AssetConfig.getAssetType())
				? String.format("%s-player", AssetConfig.getAssetType()) 
				: null;
	}

    private <T> AssetManager<T> createAssetManager(Path tempDirectory, Function<Path, T> loader) {
        return new AssetManager<>(
                tempDirectory,
                loader // use test loader func to return test-friendly result
        );
    } 


    @Test
    void constructor_buildsAssetMap() throws Exception {
        // create file in root
        AssetsTestUtils.createFile(tempDirectory, "player.png");
        // use test helper to return simple string
        AssetManager<String> manager = createAssetManager(tempDirectory, AssetManagerTest::loadAssetHelper);
        // should give result of "loaded:player.png" which was applied on "player.png"
        assertEquals(
                "loaded:player.png",
                manager.getAsset("player.png")
        );
    }

    @Test
    void getAsset_missingKeyReturnsNull() {
        AssetManager<String> manager = createAssetManager(tempDirectory, AssetManagerTest::loadAssetHelper);
        assertNull(manager.getAsset("missing.png"));
    }


    @Test
    void loaderReturningNull_doesNotAddAsset_emptyMap() throws Exception {
        AssetsTestUtils.createFile(tempDirectory, "fails_to_be_read.png");
        AssetManager<String> manager = createAssetManager(tempDirectory, AssetManagerTest::loadAssetNullHelper);
        // map should be empty since null means asset failed to load, therefore not added
        assertTrue(manager.getAssetMap().isEmpty());
    }

    // same as above but instead getting asset should fail
    // PROBABLY NOT NEEDED BECAUSE, SINCE MAP IS EMPTY, DOES NOT EXIST, AND GETTING NULL FILE TESTED BY getAsset_missingKeyReturnsNull
    @Test
    void loaderReturningNull_doesNotAddAsset_assetNull() throws Exception {
        AssetsTestUtils.createFile(tempDirectory, "fails_to_be_read.png");
        AssetManager<String> manager = createAssetManager(tempDirectory, AssetManagerTest::loadAssetNullHelper);
        assertNull(manager.getAsset("fails_to_be_read.png"));
    }

    @Test
    void update_addsAssetFromNewPath() throws Exception {
        Path addedDirectory = AssetsTestUtils.createDirectory(tempDirectory, "second");
        AssetsTestUtils.createFile(addedDirectory, "enemy.png");
        AssetManager<String> manager = createAssetManager(tempDirectory, AssetManagerTest::loadAssetHelper);
        manager.update(addedDirectory);
        assertEquals(
                "loaded:enemy.png",
                manager.getAsset("enemy.png")
        );
    }

    @Test
    void update_missingAsset_keepsExistingAsset() throws Exception {
        Path firstDirectory = AssetsTestUtils.createDirectory(tempDirectory, "first");
        Path secondDirectory = AssetsTestUtils.createDirectory(tempDirectory, "second");
        Path originalFile = AssetsTestUtils.createFile(firstDirectory, "player.png");
        AssetManager<Path> manager = createAssetManager(firstDirectory, AssetManagerTest::loadAssetPathHelper);
        // update with new directory
        manager.update(secondDirectory);
        // file not in second directory so should have kept one from previous directory
        assertEquals(
                originalFile,
                manager.getAsset("player.png")
        );
    }

	@Test
    void update_loaderReturnsSuccessfulDefaultAsset() throws Exception {
        Path defaultDirectory = AssetsTestUtils.createDirectory(tempDirectory, "default");
        AssetsTestUtils.createFile(defaultDirectory, "player.png");
        AssetManager<String> manager = createAssetManager(defaultDirectory, AssetManagerTest::loadAssetDefaultPathEndHelper);
        // should be default-player to start with since directory is "default"
		assertEquals(
                "default-player",
                manager.getAsset("player.png")
        );
    }

	@Test
    void update_loaderReturnsNullForNewAsset_keepsDefaultAsset() throws Exception {
        Path defaultDirectory = AssetsTestUtils.createDirectory(tempDirectory, "default");
        Path overrideDirectory = AssetsTestUtils.createDirectory(tempDirectory, "override");
        AssetsTestUtils.createFile(defaultDirectory, "player.png");
        AssetsTestUtils.createFile(overrideDirectory, "player.png");
        AssetManager<String> manager = createAssetManager(defaultDirectory, AssetManagerTest::loadAssetDefaultPathEndHelper);
        // update with new directory
        manager.update(overrideDirectory);
        // loader fails on second file (corrupt or incorrect format) so does not update; defaults to existing file
		// so should NOT be "override-player" and instead should remain "default-player"
        assertEquals(
                "default-player",
                manager.getAsset("player.png")
        );
    }

    @Test
    void update_overwritesExistingAssetNewPath() throws Exception {
        Path firstDirectory = AssetsTestUtils.createDirectory(tempDirectory, "first");
        Path secondDirectory = AssetsTestUtils.createDirectory(tempDirectory, "second");
        AssetsTestUtils.createFile(firstDirectory, "player.png");
        Path updatedFile = AssetsTestUtils.createFile(secondDirectory, "player.png");
        // create AssetManager of paths, this will create the first/player.png
        AssetManager<Path> manager = createAssetManager(firstDirectory, AssetManagerTest::loadAssetPathHelper);
        // now update it with second directory
        manager.update(secondDirectory);
        // check if path for player.png is now in second/player.png
        assertEquals(
                updatedFile,
                manager.getAsset("player.png")
        );
    }



}