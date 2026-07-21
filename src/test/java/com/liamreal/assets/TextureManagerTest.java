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

    @BeforeEach
    void setUp() throws Exception {
        assetsDirectory = Files.createTempDirectory("assets");
        defaultDirectory =
                Files.createDirectories(
                        assetsDirectory.resolve("default")
                );
        defaultTexturesDirectory =
                Files.createDirectories(
                        defaultDirectory.resolve("textures")
                );
        newDirectory =
                Files.createDirectories(
                        assetsDirectory.resolve("new")
                );
        newTexturesDirectory =
                Files.createDirectories(
                        newDirectory.resolve("textures")
                );
        mockedConfig = Mockito.mockStatic(AssetConfig.class);
        mockedConfig
                .when(AssetConfig::getAssetsPath)
                .thenReturn(defaultDirectory);
    }

    @AfterEach
    void tearDown() {
        mockedConfig.close();
    }

    // create image for new files
    private void createImage(Path path) throws Exception {
        BufferedImage image =
                new BufferedImage(
                        1,
                        1,
                        BufferedImage.TYPE_INT_RGB
                );

        ImageIO.write(
                image,
                "png",
                path.toFile()
        );
    }

    @Test
    void constructor_loadsTextures() throws Exception {
        createImage(
                defaultTexturesDirectory.resolve("player.png")
        );
        TextureManager manager = new TextureManager();
        // texture exists so manager loads it
        assertNotNull(
                manager.getAsset("player.png")
        );
    }

    @Test
    void getTexture_missingTextureReturnsNull() {
        TextureManager manager = new TextureManager();
        // texture does not exist so is null
        assertNull(
                manager.getAsset("missing.png")
        );
    }

    // encapsulates both missing and failed textures because of how ImageLoader is written (where exceptions result in returning null and logging)
    @Test
    void update_missingTextureKeepsExistingTexture() throws Exception {
        createImage(
                defaultTexturesDirectory.resolve("player.png")
        );
        TextureManager manager = new TextureManager();
        BufferedImage texture = manager.getAsset("player.png");
        assertNotNull(texture); // ensure first obtained texture not null

        // mock new directory path
        mockedConfig
                .when(AssetConfig::getAssetsPath)
                .thenReturn(newDirectory);

        // internally calls config for new directory
        manager.update();

        // since no matching file in new directory should fail
        assertSame(
                texture,
                manager.getAsset("player.png")
        );
    }

    @Test
    void update_newTextureUpdatesExistingTexture() throws Exception {
        createImage(
                defaultTexturesDirectory.resolve("player.png")
        );
        createImage(
                newTexturesDirectory.resolve("player.png")
        );
        TextureManager manager = new TextureManager();
        BufferedImage texture = manager.getAsset("player.png");
        assertNotNull(texture);

        // again new directory mocked
        mockedConfig
                .when(AssetConfig::getAssetsPath)
                .thenReturn(newDirectory);

        // updated inside manager
        manager.update();

        // this time texture should be different because is new valid image
        assertNotSame(
                texture,
                manager.getAsset("player.png")
        );
    }

}