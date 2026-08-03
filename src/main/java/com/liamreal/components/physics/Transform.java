package com.liamreal.components.physics;

import com.liamreal.ecs.Component;
import com.liamreal.util.Vector2f;

public class Transform implements Component, Comparable<Transform> {
    private Vector2f position;

    public Transform(double x, double y) {
        this.position = new Vector2f(x, y);
    }
    // can just pass in new position
    public Transform(Vector2f position) {
        this.position = position;
    }

    // comparsion methods
    public int compareTo(Transform other) {
        int i = this.compareX(other);
		if (i != 0) return i;

		i = this.compareY(other);
		return i;
    }
    public int compareX(Transform other) { return this.getPosition().compareX(other.getPosition()); }
    // return based on Y-position, if lower (higher on screen), should be first
    public int compareY(Transform other) { return this.getPosition().compareY(other.getPosition()); }

    // add displacement vector (i.e. speed and direction calculated from velocity) to position
    public void addDisplacement(Vector2f displacementVector) { this.position = this.position.PlusVector(displacementVector); }
    public Vector2f getPosition() { return this.position; }
    public void setPosition(Vector2f newPosition) { this.position = newPosition; }
}
