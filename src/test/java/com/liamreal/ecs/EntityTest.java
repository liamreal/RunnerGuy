package com.liamreal.ecs;

import org.junit.jupiter.api.Test;

import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    @Test
    public void testCreateEntity() {
        Entity entity = new Entity(0);
        assertEquals(0, entity.getId());
    }
    @Test
    public void testAddGetComponent() {
        Entity entity = new Entity(0);
        entity.add(new Transform(0, 0));
        Transform entityTransform = entity.get(Transform.class);
        assertEquals(new Transform(0, 0).getPosition(), entityTransform.getPosition());
        assertEquals(Transform.class, entityTransform.getClass());
    }
    @Test
    public void testAddMultipleComponents() {
        Entity entity = new Entity(0);
        entity.add(new Transform(0, 0));
        entity.add(new Velocity(0));
        Transform entityTransform = entity.get(Transform.class);
        Velocity entityVelocity = entity.get(Velocity.class);
        assertEquals(new Transform(0, 0).getPosition(), entityTransform.getPosition());
        assertEquals(Transform.class, entityTransform.getClass());
        assertEquals(new Velocity(0).getSpeed(), entityVelocity.getSpeed());
        assertEquals(Velocity.class, entityVelocity.getClass());
    }
    @Test
    public void testGetNonExistingComponent() {
        Entity entity = new Entity(0);
        assertNull(entity.get(Transform.class));
    }
    @Test
    public void testHasExistingComponent() {
        Entity entity = new Entity(0);
        entity.add(new Transform(0, 0));
        assertNotNull(entity.get(Transform.class));
        assertEquals(new Transform(0, 0).getPosition(), entity.get(Transform.class).getPosition());
    }
}