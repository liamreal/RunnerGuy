package com.liamreal.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PlayerTypeTest {
    @Test
    public void testGetAllPlayerTypes() {
        List<PlayerType> types = PlayerType.getAllPlayerTypes();
        assertEquals(PlayerType.ONE, types.get(0));
        assertEquals(PlayerType.TWO, types.get(1));
    }
}
