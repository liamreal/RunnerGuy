package com.liamreal.components.physics;

import org.junit.jupiter.api.Test;
import com.liamreal.util.Vector2f;
import static org.junit.jupiter.api.Assertions.*;

public class VelocityTest {
    @Test
    public void testCreateGet() {
        Velocity t = new Velocity(5);
        assertNotNull(t);
        double p = t.getSpeed();
        assertEquals(5, p);
    }
    @Test
    public void testSet() {
        Velocity t = new Velocity(5);
        t.setSpeed(5);
        double p = t.getSpeed();
        assertEquals(5, p);
    }
}