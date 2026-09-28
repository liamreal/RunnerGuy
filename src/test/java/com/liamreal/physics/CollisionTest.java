package com.liamreal.physics;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.ecs.Entity;
import com.liamreal.physics.shapes.Circle;
import com.liamreal.util.Vector2f;

public class CollisionTest {
    

    @Test
    public void comparingTwoEqualCollisionsSuccessful() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(0, 0));
        entityOne.add(new Velocity(0));
        entityOne.add(new Collider(new Circle(new Vector2f(0, 0), 0)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(0, 0));
        entityTwo.add(new Velocity(0));
        entityTwo.add(new Collider(new Circle(new Vector2f(0, 0), 0)));
        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityOne, entityTwo);
        assertEquals(collisionOne, collisionTwo);
        assertEquals(collisionTwo, collisionOne);
    }

    @Test
    public void comparingTwoEqualCollisionsDifferentOrderSuccessful() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(0, 0));
        entityOne.add(new Velocity(0));
        entityOne.add(new Collider(new Circle(new Vector2f(0, 0), 0)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(0, 0));
        entityTwo.add(new Velocity(0));
        entityTwo.add(new Collider(new Circle(new Vector2f(0, 0), 0)));
        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityTwo, entityOne);
        assertEquals(collisionOne, collisionTwo);
        assertEquals(collisionTwo, collisionOne);
    }
    
}