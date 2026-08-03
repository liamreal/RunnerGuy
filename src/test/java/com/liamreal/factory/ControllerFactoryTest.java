package com.liamreal.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.util.Map;
import com.liamreal.controllers.Controller;
import org.junit.jupiter.api.Test;

public class ControllerFactoryTest {
    // test for player one because has unique controls to player two
    @Test
    public void testCreatesPlayerOneControlMap() {
        Map<Integer, String> controls = ControllerFactory.createPlayerOneControls();
        assertEquals(
            Map.of(
                KeyEvent.VK_W, "up",
                KeyEvent.VK_S, "down",
                KeyEvent.VK_A, "left",
                KeyEvent.VK_D, "right",
                KeyEvent.VK_SPACE, "use"
            ),
            controls
        );
    }

    // test for player two because has unique controls to player one
    @Test
    public void testCreatesPlayerTwoControlMap() {
        Map<Integer, String> controls = ControllerFactory.createPlayerTwoControls();
        assertEquals(
            Map.of(
                KeyEvent.VK_I, "up",
                KeyEvent.VK_K, "down",
                KeyEvent.VK_J, "left",
                KeyEvent.VK_L, "right",
                KeyEvent.VK_PERIOD, "use"
            ),
            controls
        );
    }

    // then Controller being created successfully for both
    @Test
    public void testCreatesPlayerControllers() {
        Controller playerOneController = ControllerFactory.createPlayerOneController();
        Controller playerTwoController = ControllerFactory.createPlayerTwoController();

        assertNotNull(playerOneController);
        assertNotNull(playerTwoController);
    }
}