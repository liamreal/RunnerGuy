package com.liamreal.components.physics;

import org.junit.jupiter.api.Test;

import com.liamreal.util.Vector2f;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class VelocityTest {
    private static final double DELTA = 0.000001;

    @Test
    public void testCreateGetSpeed() {
        Velocity t = new Velocity(5);
        assertNotNull(t);
        double p = t.getSpeed();
        assertEquals(5, p);
    }

    @Test
    public void testSetSpeed() {
        Velocity t = new Velocity(5);
        t.setSpeed(5);
        double p = t.getSpeed();
        assertEquals(5, p);
    }
    
    @Test
    public void testSetDirection() {
        Velocity v = new Velocity(5);

        Vector2f direction = new Vector2f(1, 0);
        v.setDirection(direction);

        Vector2f obtainedDirection = v.getDirection();

        assertEquals(1, obtainedDirection.getX());
        assertEquals(0, obtainedDirection.getY());
    }

    @Test
    public void testCalculateDisplacementNormalisesDirection() {
        Velocity v = new Velocity(5);

        // Length of this vector is 5
        Vector2f direction = new Vector2f(3, 4);
        v.setDirection(direction);

        Vector2f displacement = v.calculateDisplacement();

        // Normalised (3,4) = (0.6,0.8)
        // Multiply by speed 5 = (3,4)
        assertEquals(3, displacement.getX(), DELTA);
        assertEquals(4, displacement.getY(), DELTA);
    }

    @Test
    public void testCalculateDisplacementZeroDirection() {
        Velocity v = new Velocity(5);

        // Default direction is (0,0) as defined in constructor
        Vector2f displacement = v.calculateDisplacement();

        assertEquals(0, displacement.getX(), DELTA);
        assertEquals(0, displacement.getY(), DELTA);
    }

    @Test
    public void testNegativeDirection() {
        Velocity v = new Velocity(5);

        Vector2f direction = new Vector2f(-1, 0);
        v.setDirection(direction);

        Vector2f displacement = v.calculateDisplacement();

        assertEquals(-5, displacement.getX(), DELTA);
        assertEquals(0, displacement.getY(), DELTA);
    }
}