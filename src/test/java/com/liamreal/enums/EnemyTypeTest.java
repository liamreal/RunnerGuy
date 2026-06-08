package com.liamreal.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class EnemyTypeTest {
    @Test
    public void testGetAllEnemyTypes() {
        List<EnemyType> types = EnemyType.getAllEnemyTypes();
        assertEquals(EnemyType.BASIC, types.get(0));
        assertEquals(EnemyType.ADVANCED, types.get(1));
    }
}
