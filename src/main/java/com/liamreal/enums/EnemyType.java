package com.liamreal.enums;

import java.util.Arrays;
import java.util.List;

public enum EnemyType {
    // different types of enemies
    BASIC("basic"),
    ADVANCED("advanced");

    // string representation
    private final String type;

    // string of all enemy types
    public static List<EnemyType> getAllEnemyTypes() {
        return Arrays.asList(EnemyType.values());
    }

    // constructor for each EnemyType (used when Java constructs above enemy types)
    EnemyType(String type) {
        this.type = type;
    }

    // get string representation (for texture path)
    public String toString() {
        return type;
    }
}
