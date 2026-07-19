package com.liamreal.components.graphics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImageLoaderTest {
    @Test
    public void testLoadNullTexturesException() {
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> ImageLoader.load("")
        );
        assertEquals("Could not load image: ", exception.getMessage());
    }
    @Test
    public void testLoadExistingTexture() {
        assertNotNull(ImageLoader.load("src/test/resources/test.png"));
    }
}