package com.liamreal.components.physics;

import com.liamreal.ecs.Component;
import com.liamreal.util.Vector2f;

public class Velocity implements Component {
    private Vector2f velocity;

    public Velocity(double x, double y) {
        this.velocity = new Vector2f(x, y);
    }

    public Vector2f getVelocity() { return this.velocity; }
    public void setVelocity(Vector2f newVelocity) { this.velocity = newVelocity; }


}
