package com.liamreal.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ItemTypeTest {
    @Test
    public void testGetAllItemTypes() {
        List<ItemType> types = ItemType.getAllItemTypes();
        assertEquals(ItemType.HEALTH, types.get(0));
        assertEquals(ItemType.BULLET, types.get(1));
        assertEquals(ItemType.PORTAL, types.get(2));
    }
}
