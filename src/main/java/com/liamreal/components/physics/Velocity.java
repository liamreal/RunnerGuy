package com.liamreal.components.physics;

import com.liamreal.ecs.Component;
import com.liamreal.util.Vector2f;

public class Velocity implements Component {
    private double speed;
    private Vector2f vector;

    public Velocity(double speed) {
        this.vector = new Vector2f(0, 0);
        this.speed = speed;
    }
    
    public double getSpeed() { return this.speed; }
    public void setSpeed(double newSpeed) { this.speed = newSpeed; }
}
