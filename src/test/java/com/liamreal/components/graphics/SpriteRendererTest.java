package com.liamreal.components.graphics;

import com.liamreal.graphics.Animation;
import com.liamreal.graphics.SpriteSheet;
import com.liamreal.util.Vector2f;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
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

    // Mockist approach (works better for Graphics class)
    @BeforeEach
    void setUp() {
        spriteSheet = mock(SpriteSheet.class);
        graphics = mock(Graphics.class);
        animation = mock(Animation.class);

        when(spriteSheet.getSpriteWidth()).thenReturn(32);
        when(spriteSheet.getSpriteHeight()).thenReturn(20);
        when(spriteSheet.getImage())
            .thenReturn(new BufferedImage(128, 20, BufferedImage.TYPE_INT_ARGB));

        
        spriteRenderer = new SpriteRenderer(spriteSheet, true);
    }

    @Test
    void draw_drawsFirstAnimationFrame() {
        Vector2f position = new Vector2f(100, 50);

        // (0 / 4) * 32 = 0
        when(animation.getAnimationTime()).thenReturn(0);

        spriteRenderer.draw(position, graphics, animation);

        verify(graphics).drawImage(
            any(),
            eq(100),
            eq(50),
            eq(132),
            eq(70),
            eq(0),
            eq(0),
            eq(31),
            eq(20),
            isNull()
        );
    }

    @Test
    void draw_drawsSecondAnimationFrame() {
        Vector2f position = new Vector2f(100, 50);

        // (4 / 4) * 32 = 32
        when(animation.getAnimationTime()).thenReturn(4);

        spriteRenderer.draw(position, graphics, animation);

        verify(graphics).drawImage(
            any(),
            eq(100),
            eq(50),
            eq(132),
            eq(70),
            eq(32),
            eq(0),
            eq(63),
            eq(20),
            isNull()
        );
    }

    @Test
    void draw_loopsAnimationAfterLastFrame() {
        Vector2f position = new Vector2f(0, 0);

        // 16 % 16 = 0, so it loops back to frame 0
        when(animation.getAnimationTime()).thenReturn(16);

        spriteRenderer.draw(position, graphics, animation);

        verify(graphics).drawImage(
            any(),
            eq(0),
            eq(0),
            eq(32),
            eq(20),
            eq(0),
            eq(0),
            eq(31),
            eq(20),
            isNull()
        );
    }

    @Test
    void draw_staticAlwaysDrawsFirstFrame() {
        spriteRenderer = new SpriteRenderer(spriteSheet, false);

        Vector2f position = new Vector2f(100, 50);

        // even though animation time is advanced, static sprites ignore it
        when(animation.getAnimationTime()).thenReturn(12);

        spriteRenderer.draw(position, graphics, animation);

        verify(graphics).drawImage(
            any(),
            eq(100),
            eq(50),
            eq(132),
            eq(70),
            eq(0),
            eq(0),
            eq(31),
            eq(20),
            isNull()
        );
    }
    
    @Test
    void drawStatic_doesNotUseAnimationTime() {
        spriteRenderer = new SpriteRenderer(spriteSheet, false);

        Vector2f position = new Vector2f(0, 0);

        spriteRenderer.draw(position, graphics, animation);

        // getAnimationTime should never be called for static sprites
        verify(animation, never()).getAnimationTime();
    }

    @Test
    void updateImage_updatesSpriteSheetImage() {
        BufferedImage newImage =
                new BufferedImage(64, 20, BufferedImage.TYPE_INT_ARGB);

        spriteRenderer.updateImage(newImage);

        // simply verifies method in SpriteRenderer was called with the provided image, the internal method relies on implementation in SpriteSheet
        verify(spriteSheet).updateImage(newImage);
    }
}