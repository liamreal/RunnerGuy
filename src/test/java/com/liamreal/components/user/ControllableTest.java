package com.liamreal.components.user;

import java.awt.Canvas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.liamreal.controllers.Controller;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Set;

public class ControllableTest {
    private Controller controller;
    private Controllable controllable;

    @BeforeEach
    void setUp() {
        Map<Integer, String> bindings = Map.of(
                KeyEvent.VK_W, "up",
                KeyEvent.VK_S, "down",
                KeyEvent.VK_A, "left",
                KeyEvent.VK_D, "right",
                KeyEvent.VK_SPACE, "use"
        );
        // create controller given bindings and create controllable component given controller
        controller = new Controller(bindings);
        controllable = new Controllable(controller);
    }
    
    @Test
    void getActiveControlsReturnsControllersActiveControls() {
        controller.handleInput(new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_W,
                'W'
            )
        );

        assertEquals(Set.of("up"), controllable.getActiveControls());
    }

    @Test
    void getActiveControlsReturnsEmptyForNoActiveControls() {
        assertTrue(controllable.getActiveControls().isEmpty());
    }
}