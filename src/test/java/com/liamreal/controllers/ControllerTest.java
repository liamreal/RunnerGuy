package com.liamreal.controllers;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;

public class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setUp() {
        Map<Integer, String> bindings = Map.of(
                KeyEvent.VK_W, "up",
                KeyEvent.VK_S, "down",
                KeyEvent.VK_A, "left",
                KeyEvent.VK_D, "right",
                KeyEvent.VK_SPACE, "use"
        );

        controller = new Controller(bindings);
    }

    // when constructed, controller has no active controls
    @Test
    void constructorInitialisesAllControlsAsInactive() {
        assertTrue(controller.getActiveControls().isEmpty());
    }

    // if a key is being pressed should update active controls
    @Test
    void pressingBoundKeyActivatesControl() {
        KeyEvent pressW = new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_PRESSED,
                0,
                0,
                KeyEvent.VK_W,
                'W'
        );

        controller.handleInput(pressW);

        assertEquals(Set.of("up"), controller.getActiveControls());
    }

    // now for testing release of a binded key, should not be active after release
    @Test
    void releasingBoundKeyDeactivatesControl() {
        KeyEvent pressW = new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_PRESSED,
                0,
                0,
                KeyEvent.VK_W,
                'W'
        );
        KeyEvent releaseW = new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_RELEASED,
                0,
                0,
                KeyEvent.VK_W,
                'W'
        );

        controller.handleInput(pressW);
        controller.handleInput(releaseW);

        assertTrue(controller.getActiveControls().isEmpty());
    }

    // in this example we use a very obscure key (F24), exteremely unlikely to ever be used
    @Test
    void pressingUnboundKeyDoesNothing() {
        KeyEvent pressUnboundKey = new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_PRESSED,
                0,
                0,
                KeyEvent.VK_F24,
                KeyEvent.CHAR_UNDEFINED
        );

        controller.handleInput(pressUnboundKey);

        assertTrue(controller.getActiveControls().isEmpty());
    }

    // need key bindings to create controller, 2 asserts because we are getting the exception to see its message
    @Test
    void whenBindingsAreNull_ConstructorThrowsRuntimeException() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> new Controller(null));

        assertTrue(ex.getMessage().contains("Key bindings for a controller cannot be null!"));
    }

    // missing keybinds result in runtime error with given error message
    @Test
    void whenAControlIsMissing_ConstructorThrowsRuntimeException() {
        Map<Integer, String> bindings = Map.of(
                KeyEvent.VK_W, "up",
                KeyEvent.VK_S, "down",
                KeyEvent.VK_A, "left",
                KeyEvent.VK_D, "right"
        );

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> new Controller(bindings)
        );
        assertTrue(ex.getMessage().contains("One or more controls for a controller have not been assigned! Ensure all controls are assigned exactly once!"));
    }

    // handling of multiple keys being active at once
    @Test
    void multipleKeysCanBeActiveAtOnce() {
        controller.handleInput(new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_W,
                'W'));

        controller.handleInput(new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_D,
                'D'));

        assertEquals(Set.of("up", "right"), controller.getActiveControls());
    }
}