package com.liamreal.components.physics;

import org.junit.jupiter.api.Test;

import com.liamreal.physics.shapes.Circle;
import com.liamreal.util.Vector2f;
import static org.junit.jupiter.api.Assertions.*;

public class ColliderTest {
    @Test
    public void testCreateGet() {
        Collider c = new Collider(new Circle(new Vector2f(0, 0), 0));
        assertNotNull(c);
        Vector2f position = c.getPosition();
        double length = c.getLength();
        assertEquals(new Vector2f(0, 0), position);
        assertEquals(0, length);
    }
    @Test
    public void testSet() {
        Collider c = new Collider(new Circle(new Vector2f(0, 0), 0));
        c.setPosition(new Vector2f(50, 25));
        Vector2f newPosition = c.getPosition();
        assertEquals(new Vector2f(50, 25), newPosition);
    }
}