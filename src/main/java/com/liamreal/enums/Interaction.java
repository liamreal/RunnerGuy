package com.liamreal.enums;

import java.util.Arrays;
import java.util.List;

public enum Interaction {
    SHOOT;
    
    // string of all directions
    public static List<Interaction> getAllInteractions() {
        return Arrays.asList(Interaction.values());
    }

    @Override
    public String toString() {
        return name();
    }
}
