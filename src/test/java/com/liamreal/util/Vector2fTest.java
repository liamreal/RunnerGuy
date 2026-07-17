package com.liamreal.util;

import org.junit.jupiter.api.Test;

import com.liamreal.components.physics.Velocity;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2fTest {

    @Test
    public void testCreateEmpty() {
        Vector2f v = new Vector2f();
        assertNotNull(v);
        assertEquals(new Vector2f(0, 0), v);   
    }
    @Test
    public void testCreateGetXAndY() {
        Vector2f v = new Vector2f(1, 3);
        assertNotNull(v);
        assertEquals(1, v.getX());
        assertEquals(3, v.getY());
    }
    @Test
    public void testCreateSet() {
        Vector2f v = new Vector2f(1, 3);
        assertNotNull(v);
        assertEquals(1, v.getX());
        assertEquals(3, v.getY());
        v.setX(2);
        assertEquals(2, v.getX());
        v.setX(3);
        assertEquals(3, v.getY());
    }
    @Test
    public void testEqualNull() {
        Vector2f v = new Vector2f(1, 3);
        assertNotEquals(v, null);
    }
    @Test
    public void testEqualDifferentObjects() {
        Vector2f v = new Vector2f(1, 3);
        int o = 1;
        assertNotEquals(v, o);
    }
    @Test
    public void testXNotEqual() {
        Vector2f v = new Vector2f(1, 3);
        Vector2f o = new Vector2f(2, 3);
        assertNotEquals(v, o);
    }
    @Test
    public void testYNotEqual() {
        Vector2f v = new Vector2f(1, 3);
        Vector2f o = new Vector2f(1, 4);
        assertNotEquals(v, o);
    }
    @Test
    public void testEqual() {
        Vector2f v = new Vector2f(1, 3);
        Vector2f o = new Vector2f(1, 3);
        assertEquals(v, o);
    }
    @Test
    public void testPlusVector() {
        Vector2f v = new Vector2f(1, 3);
        v = v.PlusVector(new Vector2f(1, 1));
        assertEquals(new Vector2f(2, 4), v);
        v = v.PlusVector(new Vector2f(-10, 0));
        assertEquals(new Vector2f(-8, 4), v);
    }
    @Test
    public void testMinusVector() {
        Vector2f v = new Vector2f(1, 3);
        v = v.MinusVector(new Vector2f(2, 1));
        assertEquals(new Vector2f(-1, 2), v);
        v = v.MinusVector(new Vector2f(-10, 0));
        assertEquals(new Vector2f(9, 2), v);
    }
    @Test
    public void testPositiveIntegerScaling() {
        Vector2f v = new Vector2f(1, 3);
        v = v.byScalar(2);
        assertEquals(new Vector2f(2, 6), v);
        v = v.byScalar(1);
        assertEquals(new Vector2f(2, 6), v);
    }
    @Test
    public void testPositiveFloatScaling() {
        Vector2f v = new Vector2f(1, 3);
        v = v.byScalar(1.5);
        assertEquals(new Vector2f(1.5, 4.5), v);
    }
    @Test
    public void testNegativeIntegerScaling() {
        Vector2f v = new Vector2f(1, 3);
        v = v.byScalar(-2);
        assertEquals(new Vector2f(-2, -6), v);
        v = v.byScalar(-1);
        assertEquals(new Vector2f(2, 6), v);
    }
    @Test
    public void testNegativeFloatScaling() {
        Vector2f v = new Vector2f(1, 3);
        v = v.byScalar(-1.5);
        assertEquals(new Vector2f(-1.5, -4.5), v);
    }
    @Test
    public void testZeroScaling() {
        Vector2f v = new Vector2f(1, 3);
        v = v.byScalar(0);
        assertEquals(new Vector2f(0, 0), v);
    }
    @Test
    public void testNegateVector() {
        Vector2f v = new Vector2f(1, 3);
        v = v.NegateVector();
        assertEquals(new Vector2f(-1, -3), v);
    }
    @Test
    public void testVectorLength() {
        Vector2f v = new Vector2f(1, 3);
        double expectedLength = Math.sqrt(1*1 + 3*3);
        assertEquals(expectedLength, v.length());
    }
    @Test
    public void testVectorNormal() {
        Vector2f v = new Vector2f(1, 3);
        double LengthOfTheVector=  v.length();
        double scaleOfTheVector = (double) (1.0f/ LengthOfTheVector);
        Vector2f expectedVectorNormal = new Vector2f(1*scaleOfTheVector, 3*scaleOfTheVector);
        assertEquals(expectedVectorNormal, v.Normal());
    }
    @Test
    public void testDotProduct() {
        Vector2f v = new Vector2f(1, 3);
        Vector2f o = new Vector2f(2, 5);
        double expectedDotProduct = 1*2 + 3*5;
        assertEquals(expectedDotProduct, v.dot(o));
    }
    @Test
    public void testCrossProduct() {
        Vector2f v = new Vector2f(1, 3);
        Vector2f o = new Vector2f(2, 5);
        double expectedCrossProduct = 1*5 - 3*2;
        assertEquals(expectedCrossProduct, v.cross(o));
    }
}