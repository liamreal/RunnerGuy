package com.liamreal.systems;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.liamreal.ecs.Entity;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.util.Vector2f;

public class MovementSystemTest {
    private static final double DELTA = 0.000001;
    
    @Test
    public void testMoveEntityWithVelocityAndTransform() {
        MovementSystem movementSystem = new MovementSystem();

        Entity entity = new Entity(1);

        Transform transform = new Transform(0, 0);
        Velocity velocity = new Velocity(5);
        velocity.setDirection(new Vector2f(1, 0));

        entity.add(transform);
        entity.add(velocity);

        movementSystem.move(List.of(entity));

        assertEquals(5, transform.getPosition().getX(), DELTA);
        assertEquals(0, transform.getPosition().getY(), DELTA);
    }

    @Test
    public void testMoveDiagonalNormalisesDirection() {
        MovementSystem movementSystem = new MovementSystem();

        Entity entity = new Entity(1);

        Transform transform = new Transform(0, 0);
        Velocity velocity = new Velocity(5);
        velocity.setDirection(new Vector2f(1, 1));

        entity.add(transform);
        entity.add(velocity);

        movementSystem.move(List.of(entity));

        // Normalised (1,1) * 5 = (3.535..., 3.535...)
        assertEquals(3.535533, transform.getPosition().getX(), DELTA);
        assertEquals(3.535533, transform.getPosition().getY(), DELTA);
    }
    
    @Test
    public void testEntityWithZeroVelocityDoesNotMove() {
        MovementSystem movementSystem = new MovementSystem();

        Entity entity = new Entity(1);

        Transform transform = new Transform(10, 10);
        Velocity velocity = new Velocity(0);
        velocity.setDirection(new Vector2f(1, 1));
        entity.add(transform);
        entity.add(velocity);

        movementSystem.move(List.of(entity));

        // does have Velocity but speed is 0 so should not move
        assertEquals(10, transform.getPosition().getX(), DELTA);
        assertEquals(10, transform.getPosition().getY(), DELTA);
    }

    @Test
    public void testEntityWithoutVelocityDoesNotMove() {
        MovementSystem movementSystem = new MovementSystem();

        Entity entity = new Entity(1);

        Transform transform = new Transform(10, 10);
        entity.add(transform);

        movementSystem.move(List.of(entity));

        // no Velocity component so movement ignored
        assertEquals(10, transform.getPosition().getX(), DELTA);
        assertEquals(10, transform.getPosition().getY(), DELTA);
    }

    @Test
    public void testEntityWithoutTransformDoesNotMove() {
        MovementSystem movementSystem = new MovementSystem();

        Entity entity = new Entity(1);

        Velocity velocity = new Velocity(5);
        velocity.setDirection(new Vector2f(1, 0));

        entity.add(velocity);

        movementSystem.move(List.of(entity));

        // no Transform component so movement ignored
        assertTrue(true);
    }
}