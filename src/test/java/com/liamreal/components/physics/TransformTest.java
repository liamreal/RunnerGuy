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
    @Test
    public void testCompareToSame() {
        Transform t1 = new Transform(1, 5);
        Transform t2 = new Transform(1, 5);
        assertEquals(0, t1.compareTo(t2));
    }
    @Test
    public void testCompareToDifferent() {
        Transform t1 = new Transform(1, 5);
        Transform t2 = new Transform(1, 4);
        Transform t3 = new Transform(2, 5);
        assertTrue(t1.compareTo(t2) > 0);
        assertTrue(t2.compareTo(t1) < 0);
        assertTrue(t1.compareTo(t3) < 0);
        assertTrue(t3.compareTo(t1) > 0);
    }
    @Test
    public void testCompareYSame() {
        Transform t1 = new Transform(1, 5);
        Transform t2 = new Transform(2, 5);
        assertEquals(0, t1.compareY(t2));
    }
    @Test
    public void testCompareYDifferent() {
        Transform t1 = new Transform(1, 5);
        Transform t2 = new Transform(3, 4);
        Transform t3 = new Transform(2, 6);
        assertTrue(t1.compareY(t2) > 0);
        assertTrue(t2.compareY(t1) < 0);
        assertTrue(t1.compareY(t3) < 0);
        assertTrue(t3.compareY(t1) > 0);
    }
    @Test
    public void testAddDisplacement() {
        Vector2f initialPosition = new Vector2f(1.0, 2.0);
        Transform transform = new Transform(initialPosition);
        
        Vector2f displacement = new Vector2f(3.0, 4.0);
        transform.addDisplacement(displacement);
        
        Vector2f expectedPosition = initialPosition.PlusVector(displacement);
        assertEquals(expectedPosition, transform.getPosition());
        
        
    }
    @Test
    public void testAddZeroDisplacement() {
        Vector2f initialPosition = new Vector2f(1.0, 2.0);
        Transform transform = new Transform(initialPosition);

        // test adding zero displacement
        Vector2f expectedPosition = new Vector2f(1.0, 2.0);
        transform.addDisplacement(new Vector2f(0.0f, 0.0f));
        assertEquals(expectedPosition, transform.getPosition());
    }
    @Test
    public void testAddNegativeDisplacement() {
        Vector2f initialPosition = new Vector2f(1.0, 2.0);
        Transform transform = new Transform(initialPosition);

        // test adding negative displacement
        Vector2f negativeDisplacement = new Vector2f(-1.0f, -1.0f);
        transform.addDisplacement(negativeDisplacement);
        Vector2f expectedFinalPosition = initialPosition.PlusVector(negativeDisplacement);
        assertEquals(expectedFinalPosition, transform.getPosition());
    }
}