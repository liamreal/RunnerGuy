package com.liamreal.controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    @Test
    public void testControllerExists() {
        assertNotNull(Controller.getInstance());
    }
    @Test
    public void testControllerPlayerOneMovementKeysNotPressed() {
        Controller controller = Controller.getInstance();
        controller.setKeyAPressed(false);
        controller.setKeySPressed(false);
        controller.setKeyDPressed(false);
        controller.setKeyWPressed(false);
        controller.setKeySpacePressed(false);
        assertFalse(controller.isKeyAPressed());
        assertFalse(controller.isKeySPressed());
        assertFalse(controller.isKeyDPressed());
        assertFalse(controller.isKeyWPressed());
        assertFalse(controller.isKeySpacePressed());
    }
    @Test
    public void testControllerPlayerOneMovementKeysPressed() {
        Controller controller = Controller.getInstance();
        controller.setKeyAPressed(true);
        controller.setKeySPressed(true);
        controller.setKeyDPressed(true);
        controller.setKeyWPressed(true);
        controller.setKeySpacePressed(true);
        assertTrue(controller.isKeyAPressed());
        assertTrue(controller.isKeySPressed());
        assertTrue(controller.isKeyDPressed());
        assertTrue(controller.isKeyWPressed());
        assertTrue(controller.isKeySpacePressed());
    }
    @Test
    public void testControllerPlayerTwoMovementKeysNotPressed() {
        Controller controller = Controller.getInstance();
        controller.setKeyIPressed(false);
        controller.setKeyJPressed(false);
        controller.setKeyKPressed(false);
        controller.setKeyLPressed(false);
        controller.setKeyPeriodPressed(false);
        assertFalse(controller.isKeyIPressed());
        assertFalse(controller.isKeyJPressed());
        assertFalse(controller.isKeyKPressed());
        assertFalse(controller.isKeyLPressed());
        assertFalse(controller.isKeyPeriodPressed());
    }
    @Test
    public void testControllerPlayerTwoMovementKeysPressed() {
        Controller controller = Controller.getInstance();
        controller.setKeyIPressed(true);
        controller.setKeyJPressed(true);
        controller.setKeyKPressed(true);
        controller.setKeyLPressed(true);
        controller.setKeyPeriodPressed(true);
        assertTrue(controller.isKeyIPressed());
        assertTrue(controller.isKeyJPressed());
        assertTrue(controller.isKeyKPressed());
        assertTrue(controller.isKeyLPressed());
        assertTrue(controller.isKeyPeriodPressed());
    }
}
