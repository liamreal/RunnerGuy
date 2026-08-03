package com.liamreal.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.util.Map;
import com.liamreal.controllers.Controller;
import org.junit.jupiter.api.Test;

public class ControllerFactoryTest {
    // here test both as need to ensure each has their unique controls
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

    // then test controls being added to and returned through the created Controller
    @Test
    public void testCreatesPlayerControllers() {
        Controller playerOneController = ControllerFactory.createPlayerOneController();
        Controller playerTwoController = ControllerFactory.createPlayerTwoController();

        assertNotNull(playerOneController);
        assertNotNull(playerTwoController);
    }
}