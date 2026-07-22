package com.liamreal.graphics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

public class SpriteSheetTest {
    private BufferedImage testImage;
    private String testPath;

    @BeforeEach
    void setUp() {
        testPath = "src/test/resources/test.png";
        testImage = ImageLoader.load(testPath);
    }

    @Test
    public void testCreateSpriteSheetSuccessfully() {
        SpriteSheet spriteSheet = new SpriteSheet(testImage, 64, 32);
        assertEquals(64, spriteSheet.getSpriteWidth());
        assertEquals(32, spriteSheet.getSpriteHeight());
    }
    @Test
    public void testCreateSpriteSheetNullImageException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new SpriteSheet(null, 64, 32)
        );
        assertEquals("SpriteSheet image cannot be null", exception.getMessage());
    }
    @Test
    public void testCreateSpriteSheetInvalidWidthException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new SpriteSheet(testImage, 0, 32)
        );
        assertEquals("SpriteSheet must have positive spriteWidth and spriteHeight", exception.getMessage());
    }
    @Test
    public void testCreateSpriteSheetInvalidHeightException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new SpriteSheet(testImage, 32, 0)
        );
        assertEquals("SpriteSheet must have positive spriteWidth and spriteHeight", exception.getMessage());
    }
    @Test
    public void testUpdateImageSuccessfully() {
        SpriteSheet spriteSheet = new SpriteSheet(testImage, 64, 32);
        // ensure original image correct
        assertSame(
            testImage,
            spriteSheet.getImage()
        );
        BufferedImage newImage =
            new BufferedImage(
                128,
                64,
                BufferedImage.TYPE_INT_ARGB
            );
        spriteSheet.updateImage(newImage);
        // ensure new image applied
        assertSame(
            newImage,
            spriteSheet.getImage()
        );
    }
}