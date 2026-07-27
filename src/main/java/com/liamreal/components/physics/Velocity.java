package com.liamreal.components.physics;

import com.liamreal.ecs.Component;
import com.liamreal.util.Vector2f;

public class Velocity implements Component {
    private Vector2f vector;

    public Velocity(double x, double y) {
        this.vector = new Vector2f(x, y);
    }

    public Vector2f getVector() { return this.vector; }
    public void setVector(Vector2f newVector) { this.vector = newVector; }


}
