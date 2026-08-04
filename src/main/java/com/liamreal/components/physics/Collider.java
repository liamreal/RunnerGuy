package com.liamreal.components.physics;

import com.liamreal.ecs.Component;
import com.liamreal.physics.shapes.Circle;

public class Collider implements Component {
    private Circle shape;

    public Collider(Circle shape) {
        this.shape = shape;
    }

    
}
