package com.liamreal.assets;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class TextureManagerTest {
    private Path assetsDirectory;
    private Path defaultDirectory;
    private Path newDirectory;
    private Path defaultTexturesDirectory;
    private Path newTexturesDirectory;
    private MockedStatic<AssetConfig> mockedConfig;
    private int imageWidth = 1; // NON-NEGATIVE --- images made for tests all have unique width so at least differ by one property

    @BeforeEach
    void setUp() throws Exception {
        // create dir structure:
        //              assets
        //             /     \
        //        default     new
        //        /             \
        //    textures         textures
        assetsDirectory = Files.createTempDirectory("assets");
        defaultDirectory = AssetTestUtils.createDirectory(assetsDirectory, "default");
        defaultTexturesDirectory = AssetTestUtils.createDirectory(defaultDirectory, "textures");
        createImage(AssetTestUtils.createDirectory(defaultTexturesDirectory, "player.png"));
        newDirectory = AssetTestUtils.createDirectory(assetsDirectory, "new");
        newTexturesDirectory = AssetTestUtils.createDirectory(newDirectory, "textures");

        // mock AssetConfig for desired start value of "default", expected start asset pack out of game
        mockedConfig = Mockito.mockStatic(AssetConfig.class);

        mockedConfig
                .when(AssetConfig::getAssetsPath)
                .thenReturn(defaultDirectory);
    }

	// close static class after each test
    @AfterEach
    void tearDown() {
        mockedConfig.close();
    }

    // create sample image for new files
    private void createImage(Path path) throws Exception {
        // image of random size 1-100 (inclusive)
        BufferedImage image =
                new BufferedImage(
                        this.imageWidth, // image unique test width
                        1,
                        BufferedImage.TYPE_INT_RGB
                );

        ImageIO.write(
                image,
                "png",
                path.toFile()
        );

        // change for next image width
        this.imageWidth++;
    }

    @Test
    void getTexture_missingTextureReturnsNull() {
        TextureManager manager = new TextureManager();
        // texture does not exist so is null
        assertNull(
                manager.getAsset("missing.png")
        );
    }

    @Test
    void constructor_loadsExistingTexture() throws Exception {
        TextureManager manager = new TextureManager();
        // texture exists ("default" config) so manager loads it
        assertNotNull(
                manager.getAsset("player.png")
        );
    }


    // encapsulates both missing and failed textures because of how ImageLoader is written (where exceptions result in returning null and logging)
    @Test
    void update_missingTextureKeepsExistingTexture() throws Exception {
        TextureManager manager = new TextureManager();
        BufferedImage defaultTexture = manager.getAsset("player.png"); // verified exists in previous test

        // mock new directory path
        mockedConfig
                .when(AssetConfig::getAssetsPath)
                .thenReturn(newDirectory);

        // internally calls config for new directory
        manager.update();

        // since no matching file in new directory should return older existing file
        assertSame(
                defaultTexture,
                manager.getAsset("player.png")
        );
    }

    @Test
    void update_newTextureUpdatesExistingTexture() throws Exception {
        TextureManager manager = new TextureManager();
        BufferedImage defaultTexture = manager.getAsset("player.png");
        // create new texture in new directory
        createImage(AssetTestUtils.createDirectory(newTexturesDirectory, "player.png"));

        // again new directory mocked
        mockedConfig
                .when(AssetConfig::getAssetsPath)
                .thenReturn(newDirectory);

        // updated inside manager
        manager.update();

        // this time texture should be new one
        assertNotSame(
                defaultTexture,
                manager.getAsset("player.png")
        );
    }

}