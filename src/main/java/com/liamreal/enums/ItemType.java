package com.liamreal.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ItemType {
    // different types of items that can spawn
    HEALTH("health"),
    BULLET("bullet"),
    PORTAL("portal");

    // string representation
    private final String type;

    // constructor for each EnemyType (used when Java constructs above enemy types)
    ItemType(String type) {
        this.type = type;
    }
    
    // string of all item types
    public static List<ItemType> getAllItemTypes() {
        return Arrays.asList(ItemType.values());
    }
    
    // string of all item types EXCEPT portal
    public static List<ItemType> getAllItemTypesExceptPortal() {
        List<ItemType> allItems = new ArrayList<>(Arrays.asList(ItemType.values()));
        allItems.remove(ItemType.PORTAL);
        return allItems;
    }

    // hashmap for player controls

    // get string representation (for texture path)
    public String toString() {
        return type;
    }
}
