package com.liamreal.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class InteractionTest {
    @Test
    public void testGetAllInteractions() {
        List<Interaction> types = Interaction.getAllInteractions();
        assertEquals(Interaction.SHOOT, types.get(0));
    }
}
