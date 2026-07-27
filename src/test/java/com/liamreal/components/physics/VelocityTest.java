package com.liamreal.components.physics;

import org.junit.jupiter.api.Test;
import com.liamreal.util.Vector2f;
import static org.junit.jupiter.api.Assertions.*;

public class VelocityTest {
    @Test
    public void testCreateGet() {
        Velocity t = new Velocity(5, 5);
        assertNotNull(t);
        Vector2f p = t.getVector();
        assertEquals(new Vector2f(5, 5), p);
    }
    @Test
    public void testSet() {
        Velocity t = new Velocity(5, 5);
        t.setVector(new Vector2f(10, 3));
        Vector2f p = t.getVector();
        assertEquals(new Vector2f(10, 3), p);
    }
}