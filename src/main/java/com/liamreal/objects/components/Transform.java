package com.liamreal.objects.components;

import com.liamreal.objects.Component;
import com.liamreal.util.Vector2f;

public class Transform implements Component {
    private Vector2f position;

    public Transform(double x, double y) {
        this.position = new Vector2f(x, y);
    }

    public Vector2f getPosition() { return this.position; }
    public void setPosition(Vector2f newPosition) { this.position = newPosition; }
}
