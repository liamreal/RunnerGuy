package com.liamreal.graphics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimationTest {
    @Test
    public void AnimationCreatedSuccessfully() {
        Animation animation = new Animation();
        assertNotNull(animation);
    }
    @Test
    public void AnimationTimeIncrementing() {
        Animation animation = new Animation();
        assertEquals(0, animation.getAnimationTime());
        animation.incrementAnimationTime();
        assertEquals(1, animation.getAnimationTime());
    }
}