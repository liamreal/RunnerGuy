package com.liamreal.physics.shapes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.liamreal.util.Vector2f;

class CircleTest {

    @Test
    void constructor_initialisesCircle() {
        Vector2f centre = new Vector2f(0, 0);
        Circle circle = new Circle(centre, 0);

        assertEquals(centre, circle.getCentre());
        assertEquals(0, circle.getRadius());
    }

    @Test
    void setter_updatesCentre() {
        Vector2f centre = new Vector2f(0, 0);
        Circle circle = new Circle(centre, 0);

        assertEquals(centre, circle.getCentre());

        circle.setCentre(new Vector2f(50, 25));

        assertEquals(new Vector2f(50, 25), circle.getCentre());
    }
}