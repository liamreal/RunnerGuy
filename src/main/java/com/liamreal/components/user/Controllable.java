package com.liamreal.components.user;

import java.lang.ModuleLayer.Controller;
import com.liamreal.ecs.Component;

public class Controllable implements Component {
    private Controller controller;

    public Controllable(Controller controller) {
        this.controller = controller;
    }
    
    // get controller instance
    public Controller getController() { return this.controller; }
}
