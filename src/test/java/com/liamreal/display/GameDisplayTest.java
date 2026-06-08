package com.liamreal.display;

import org.junit.jupiter.api.Test;
import com.liamreal.util.Point3f;
import static org.junit.jupiter.api.Assertions.*;

public class GameDisplayTest {
    @Test
    public void testGetDisplayX() {
        assertEquals(854, GameDisplay.getDisplayX());
    }
    @Test
    public void testGetDisplayY() {
        assertEquals(480, GameDisplay.getDisplayY());
    }
    @Test
    public void testGetDisplayCentre() {
        assertEquals(new Point3f(GameDisplay.getDisplayX()/2,GameDisplay.getDisplayY()/2,0), GameDisplay.getDisplayCentre());
    }
}
