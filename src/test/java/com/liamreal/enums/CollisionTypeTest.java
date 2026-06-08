package com.liamreal.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CollisionTypeTest {
    @Test
    public void testGetAllCollisionTypes() {
        List<CollisionType> types = CollisionType.getAllCollisionTypes();
        assertEquals(CollisionType.KILL, types.get(0));
        assertEquals(CollisionType.EXPLODE, types.get(1));
    }
}
