package com.liamreal.factory;

import java.awt.event.KeyEvent;
import java.util.Map;

import com.liamreal.controllers.Controller;

public class ControllerFactory {
    public static Controller createPlayerOneController() {
        return new Controller(ControllerFactory.createPlayerOneControls());
    }
    public static Controller createPlayerTwoController() {
        return new Controller(ControllerFactory.createPlayerTwoControls());
    }
    // helper methods for player controls, for now hard-coded
    private static Map<Integer, String> createPlayerOneControls() {
        Map<Integer, String> keyControls = Map.of(
            KeyEvent.VK_W, "up",
            KeyEvent.VK_S, "down",
            KeyEvent.VK_A, "left",
            KeyEvent.VK_D, "right",
            KeyEvent.VK_SPACE, "use"
        );
        return keyControls;
    }
    private static Map<Integer, String> createPlayerTwoControls() {
        Map<Integer, String> keyControls = Map.of(
            KeyEvent.VK_I, "up",
            KeyEvent.VK_K, "down",
            KeyEvent.VK_J, "left",
            KeyEvent.VK_L, "right",
            KeyEvent.VK_PERIOD, "use"
        );
        return keyControls;
    }
}
