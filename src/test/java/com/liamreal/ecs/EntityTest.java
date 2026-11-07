package com.liamreal.ecs;

import org.junit.jupiter.api.Test;

import com.liamreal.components.physics.Transform;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    @Test
    public void testCreateEntity() {
        Entity entity = new Entity(0);
        assertEquals(0, entity.getId());
    }
    @Test
    public void test_AddReturnsTrue_WhenSuccessfullyAddingComponent() {
        Entity entity = new Entity(0);

        assertTrue(entity.add(new Transform(0, 0)));
    }
    @Test
    public void test_AddReturnsFalse_WhenAddingExistingComponent() {
        Entity entity = new Entity(0);

        entity.add(new Transform(0, 0));

        assertFalse(entity.add(new Transform(1, 1)));
    }
    @Test
    public void testAddGetComponent() {
        Entity entity = new Entity(0);
        Transform transform = new Transform(0, 0);

        entity.add(transform);
        
        assertEquals(transform, entity.get(Transform.class));
    }
    @Test
    public void test_GetReturnsNull_ForNonExistingComponent() {
        Entity entity = new Entity(0);

        assertNull(entity.get(Transform.class));
    }
    @Test
    public void testReturnsFalse_WhenAddingExistingComponent() {
        Entity entity = new Entity(0);
        Transform transform = new Transform(0, 0);
        Transform transform2 = new Transform(1, 1);

        entity.add(transform);
        boolean expected = entity.add(transform2);
        
        assertFalse(expected);
    }
    @Test
    public void testKeepsOriginal_WhenAddingExistingComponent() {
        Entity entity = new Entity(0);
        Transform transform = new Transform(0, 0);
        Transform transform2 = new Transform(1, 1);

        entity.add(transform);
        entity.add(transform2);
        
        assertEquals(transform, entity.get(Transform.class));
    }
    @Test
    public void testReturnsNull_WhenGetNonExistingComponent() {
        Entity entity = new Entity(0);

        assertNull(entity.get(Transform.class));
    }
    @Test
    public void test_HasReturnsFalse_ForNonExistingComponent() {
        Entity entity = new Entity(0);
        
        assertFalse(entity.has(Transform.class));
    }
    @Test
    public void testHasExistingComponent() {
        Entity entity = new Entity(0);

        entity.add(new Transform(0, 0));
        
        assertTrue(entity.has(Transform.class));
    }
    @Test
    public void test_HasReturnsFalse_WhenCheckingIfHasARemovedComponent() {
        Entity entity = new Entity(0);

        entity.add(new Transform(0, 0));
        entity.remove(Transform.class);

        // should not have Transform component as just removed
        assertFalse(entity.has(Transform.class));
    }
    @Test
    public void test_RemoveReturnsComponent_WhenRemovingExistingComponent() {
        Entity entity = new Entity(0);
        Transform transform = new Transform(0, 0);
        
        entity.add(transform);

        assertEquals(transform, entity.remove(Transform.class));
    }
    @Test
    public void test_RemoveReturnsNull_WhenRemovingNonExistingComponent() {
        Entity entity = new Entity(0);

        assertNull(entity.remove(Transform.class));
    }
}