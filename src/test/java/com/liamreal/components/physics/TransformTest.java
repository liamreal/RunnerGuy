package com.liamreal.components.physics;

import org.junit.jupiter.api.Test;
import com.liamreal.util.Vector2f;
import static org.junit.jupiter.api.Assertions.*;

public class TransformTest {
    @Test
    public void testCreateGet() {
        Transform t = new Transform(5, 5);
        assertNotNull(t);
        Vector2f p = t.getPosition();
        assertEquals(new Vector2f(5, 5), p);
    }
    @Test
    public void testSet() {
        Transform t = new Transform(5, 5);
        t.setPosition(new Vector2f(10, 3));
        Vector2f p = t.getPosition();
        assertEquals(new Vector2f(10, 3), p);
    }
}