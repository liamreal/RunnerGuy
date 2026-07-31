package com.liamreal.components.user;

import java.util.Collection;
import com.liamreal.controllers.Controller;
import com.liamreal.ecs.Component;

public class Controllable implements Component {
    private Controller controller;

    public Controllable(Controller controller) {
        this.controller = controller;
    }
    
    // get currently active controls from controller
    public Collection<String> getActiveControls() { return this.controller.getActiveControls(); }
}
