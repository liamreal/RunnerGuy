package com.liamreal.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DirectionTest {
    @Test
    public void testGetAllDirections() {
        List<Direction> types = Direction.getAllDirections();
        assertEquals(Direction.DOWN, types.get(0));
        assertEquals(Direction.UP, types.get(1));
        assertEquals(Direction.LEFT, types.get(2));
        assertEquals(Direction.RIGHT, types.get(3));
    }
}
