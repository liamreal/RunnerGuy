package com.liamreal.systems;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.liamreal.ecs.Entity;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.components.user.Controllable;
import com.liamreal.util.Vector2f;

public class InputSystemTest {
    private InputSystem inputSystem;
    private Entity entity;
    private Transform transform;
    private Velocity velocity;
    private Controllable controllable;

    @BeforeEach
    void setUp() {
        inputSystem = new InputSystem();
        entity = new Entity(1);
        transform = new Transform(50, 50);
        velocity = new Velocity(5);
        controllable = mock(Controllable.class);
    }
    
    @Test
    public void testUpdateMovementSetsRightUpDirection() {
        entity.add(transform);
        entity.add(velocity);
        entity.add(controllable);

        when(controllable.getActiveControls())
            .thenReturn(Set.of("right", "up"));

        inputSystem.update(List.of(entity));

        Vector2f direction = velocity.getDirection();

        assertEquals(1, direction.getX());
        assertEquals(-1, direction.getY());
    }

    @Test
    public void testUpdateMovementSetsDownLeftDirection() {
        entity.add(transform);
        entity.add(velocity);
        entity.add(controllable);

        when(controllable.getActiveControls())
            .thenReturn(Set.of("down", "left"));

        inputSystem.update(List.of(entity));

        Vector2f direction = velocity.getDirection();

        assertEquals(-1, direction.getX());
        assertEquals(1, direction.getY());
    }

    @Test
    public void testUpdateMovementSetsAllDirection() {
        entity.add(transform);
        entity.add(velocity);
        entity.add(controllable);

        when(controllable.getActiveControls())
            .thenReturn(Set.of("up", "down", "left", "right"));

        inputSystem.update(List.of(entity));

        Vector2f direction = velocity.getDirection();

        assertEquals(0, direction.getX());
        assertEquals(0, direction.getY());
    }

    @Test
    public void testEntityWithoutTransformIgnored() {
        entity.add(velocity);
        entity.add(controllable);

        when(controllable.getActiveControls())
            .thenReturn(Set.of("up"));

        inputSystem.update(List.of(entity));

        Vector2f direction = velocity.getDirection();

        assertEquals(0, direction.getX());
        assertEquals(0, direction.getY());
    }

    @Test
    public void testNoControlsSetsZeroDirection() {
        entity.add(transform);
        entity.add(velocity);
        entity.add(controllable);

        when(controllable.getActiveControls())
            .thenReturn(Set.of());

        inputSystem.update(List.of(entity));

        Vector2f direction = velocity.getDirection();

        assertEquals(0, direction.getX());
        assertEquals(0, direction.getY());
    }
}