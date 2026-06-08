// 22371791 Liam Kerrin
package com.liamreal.enums;

import java.util.Arrays;
import java.util.List;

public enum CollisionType {
    // different types of bullets
    KILL("kill"),
    EXPLODE("explode");

    // string representation
    private final String type;

    // constructor for each CollisionType (used when Java constructs above bullet types)
    CollisionType(String type) {
        this.type = type;
    }

    // string of all bullet types
    public static List<CollisionType> getAllCollisionTypes() {
        return Arrays.asList(CollisionType.values());
    }

    // get string representation (for texture path)
    public String toString() {
        return type;
    }
}
