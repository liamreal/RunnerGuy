package com.liamreal.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CollisionTypeTest {
    @Test
    public void testGetAllCollisionTypes() {
        List<CollisionType> collistionTypes = CollisionType.getAllCollisionTypes();
        assertEquals(CollisionType.KILL, collistionTypes.get(0));
        assertEquals(CollisionType.EXPLODE, collistionTypes.get(1));
    }
}
