// 22371791 Liam Kerrin
package com.liamreal.enums;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    DOWN,
    UP,
    LEFT,
    RIGHT;
    
    // string of all directions
    public static List<Direction> getAllDirections() {
        return Arrays.asList(Direction.values());
    }

    @Override
    public String toString() {
        return name();
    }
}
