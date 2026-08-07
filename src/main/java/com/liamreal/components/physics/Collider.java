package com.liamreal.components.physics;

import com.liamreal.ecs.Component;
import com.liamreal.physics.shapes.Circle;
import com.liamreal.util.Vector2f;

public class Collider implements Component {
    private Circle shape;

    public Collider(Circle shape) {
        this.shape = shape;
    }

    public void setPosition(Vector2f newPosition) {
        this.shape.setCentre(newPosition);
    }
    public double getLength() { return this.shape.getRadius(); }
    public Vector2f getPosition() { return this.shape.getCentre(); }
}
