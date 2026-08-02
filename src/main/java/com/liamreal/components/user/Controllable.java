package com.liamreal.components.user;

import com.liamreal.controllers.Controller;
import com.liamreal.ecs.Component;
import java.util.Set;

public class Controllable implements Component {
    private Controller controller;

    public Controllable(Controller controller) {
        this.controller = controller;
    }
    
    // get currently active controls from controller
    public Set<String> getActiveControls() { return this.controller.getActiveControls(); }
}
