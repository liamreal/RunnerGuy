package com.liamreal.physics.shapes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.liamreal.util.Vector2f;

class CircleTest {

    @Test
    void constructor_initialisesCircleDataSuccessfuly() {
        Vector2f centre = new Vector2f(0, 0);
        Circle circle = new Circle(centre, 0);

        assertEquals(new Circle(new Vector2f(0, 0), 0), circle);
    }

    @Test
    void setter_updatesCentre() {
        Vector2f centre = new Vector2f(0, 0);
        Circle circle = new Circle(centre, 0);

        circle.setCentre(new Vector2f(50, 25));

        assertEquals(new Vector2f(50, 25), circle.getCentre());
    }
}