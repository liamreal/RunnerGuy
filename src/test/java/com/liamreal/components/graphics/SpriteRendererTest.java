package com.liamreal.components.graphics;

import com.liamreal.util.Vector2f;
import static org.mockito.Mockito.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpriteRendererTest {

    private SpriteRenderer spriteRenderer;
    private SpriteSheet spriteSheet;
    private Graphics graphics;
    private Animation animation;

    @BeforeEach
    // Mockist approach (works better for Graphics class)
    void setUp() {
        spriteSheet = mock(SpriteSheet.class);
        graphics = mock(Graphics.class);
        animation = mock(Animation.class);

        when(spriteSheet.getSpriteWidth()).thenReturn(32);
        when(spriteSheet.getSpriteHeight()).thenReturn(32);
        when(spriteSheet.getImage())
            .thenReturn(new BufferedImage(128, 32, BufferedImage.TYPE_INT_ARGB));

        spriteRenderer = new SpriteRenderer(spriteSheet);
    }

    @Test
    void drawImage_drawsFirstAnimationFrame() {
        Vector2f position = new Vector2f(100, 50);

        when(animation.getAnimationTime()).thenReturn(0);

        spriteRenderer.drawImage(position, graphics, animation);

        verify(graphics).drawImage(
            any(),
            eq(100),
            eq(50),
            eq(132),
            eq(82),
            eq(0),
            eq(0),
            eq(31),
            eq(32),
            isNull()
        );
    }

    @Test
    void drawImage_drawsSecondAnimationFrame() {
        Vector2f position = new Vector2f(100, 50);

        // (4 / 4) * 32 = 32
        when(animation.getAnimationTime()).thenReturn(4);

        spriteRenderer.drawImage(position, graphics, animation);

        verify(graphics).drawImage(
            any(),
            eq(100),
            eq(50),
            eq(132),
            eq(82),
            eq(32),
            eq(0),
            eq(63),
            eq(32),
            isNull()
        );
    }

    @Test
    void drawImage_loopsAnimationAfterLastFrame() {
        Vector2f position = new Vector2f(0, 0);

        // 16 % 16 = 0, so it loops back to frame 0
        when(animation.getAnimationTime()).thenReturn(16);

        spriteRenderer.drawImage(position, graphics, animation);

        verify(graphics).drawImage(
            any(),
            eq(0),
            eq(0),
            eq(32),
            eq(32),
            eq(0),
            eq(0),
            eq(31),
            eq(32),
            isNull()
        );
    }
}