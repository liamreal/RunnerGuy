package com.liamreal.components.physics;

import com.liamreal.ecs.Component;
import com.liamreal.util.Vector2f;

public class Velocity implements Component {
    private double speed;
    private Vector2f direction;

    public Velocity(double speed) {
        this.direction = new Vector2f(0, 0);
        this.speed = speed;
    }
    
    // normalise direction then multiply speed by direction to get displacement vector
    public Vector2f calculateDisplacement() { return this.getDirection().Normal().byScalar(this.getSpeed()); }
    public double getSpeed() { return this.speed; }
    public Vector2f getDirection() { return this.direction; }
    public void setSpeed(double newSpeed) { this.speed = newSpeed; }
    public void setDirection(Vector2f newDirection) { this.direction = newDirection; }
}
