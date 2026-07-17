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
}