package com.liamreal.components.physics;

import org.junit.jupiter.api.Test;
import com.liamreal.util.Vector2f;
import static org.junit.jupiter.api.Assertions.*;

public class VelocityTest {
    private static final double DELTA = 0.000001;

    @Test
    public void testCreateGetSpeed() {
        Velocity t = new Velocity(5);
        
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

        assertEquals(new Vector2f(1, 0), obtainedDirection);
    }

    @Test
    public void testCalculateDisplacementNormalisesDirection() {
        Velocity v = new Velocity(5);
        // length of vector is 5
        Vector2f direction = new Vector2f(3, 4);

        v.setDirection(direction);
        Vector2f displacement = v.calculateDisplacement();

        // normalised (3, 4) = (0.6, 0.8)
        // multiply by speed 5 = (3, 4)
        assertEquals(3, displacement.getX(), DELTA); // need 2 asserts here to take advantage of DELTA for margin of error
        assertEquals(4, displacement.getY(), DELTA);
    }

    @Test
    public void testCalculateDisplacementZeroDirection() {
        Velocity v = new Velocity(5);

        // default direction (0, 0) in constructor
        Vector2f displacement = v.calculateDisplacement();
        
        assertEquals(new Vector2f(0, 0), displacement);
    }

    @Test
    public void testNegativeDirection() {
        Velocity v = new Velocity(5);
        Vector2f direction = new Vector2f(-1, 0);
        
        v.setDirection(direction);
        // should apply (-1*5, 0*5) normalised, which in this case results in (-5, 0) since only movement on one axis 
        Vector2f displacement = v.calculateDisplacement();

        assertEquals(new Vector2f(-5, 0), displacement);
    }
}