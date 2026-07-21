package com.liamreal.graphics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageLoaderTest {
    @Test
    public void testLoadMissingTextureReturnsNull() {
        BufferedImage result = ImageLoader.load("");
        assertNull(result);
    }
    @Test
    public void testLoadMissingFileReturnsNull() {
        BufferedImage result = ImageLoader.load("does_not_exist.png");
        assertNull(result);
    }
    @Test
    public void testLoadInvalidImageReturnsNull() throws Exception {
        Path invalidFile = Files.createTempFile("invalid", ".png");
        Files.writeString(invalidFile, "not an image");
        BufferedImage result = ImageLoader.load(invalidFile);
        assertNull(result);
    }
    @Test
    public void testLoadExistingTexture() {
        assertNotNull(ImageLoader.load("src/test/resources/test.png"));
    }
}