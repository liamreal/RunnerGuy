package com.liamreal.physics.shapes;

import com.liamreal.util.Vector2f;

public class Circle {
    private Vector2f centre;
    private double radius;

    public Circle(Vector2f centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }

    public Vector2f getCentre() { return this.centre; }
    public double getRadius() { return this.radius; }
    public void setCentre(Vector2f newCentre) { this.centre = newCentre; }
}