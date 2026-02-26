package com.liamreal.enums;

import java.util.Arrays;
import java.util.List;

public enum ItemType {
    // different types of items that can spawn
    HEALTH("health"),
    BULLET("bullet");

    // string representation
    private final String type;

    // constructor for each EnemyType (used when Java constructs above enemy types)
    ItemType(String type) {
        this.type = type;
    }
    
    // string of all player types
    public static List<ItemType> getAllItemTypes() {
        return Arrays.asList(ItemType.values());
    }

    // hashmap for player controls

    // get string representation (for texture path)
    public String toString() {
        return type;
    }
}
