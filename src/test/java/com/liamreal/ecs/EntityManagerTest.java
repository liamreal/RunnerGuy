package com.liamreal.ecs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EntityManagerTest {
    @Test
    void createEntity_addsEntityToManager() {
        EntityManager manager = new EntityManager();
        Entity entity = manager.createEntity();
        assertEquals(1, manager.getEntityCount());
        assertSame(entity, manager.getEntityMap().get(entity.getId()));
    }

    @Test
    void createEntity_assignsUniqueIds() {
        EntityManager manager = new EntityManager();
        Entity first = manager.createEntity();
        Entity second = manager.createEntity();
        assertEquals(0, first.getId());
        assertEquals(1, second.getId());
    }

    @Test
    void getEntityMap_initiallyEmpty() {
        EntityManager manager = new EntityManager();
        assertEquals(0, manager.getEntityCount());
        assertTrue(manager.getEntityMap().isEmpty());
    }

    @Test
    void getEntityMap_containsAllCreatedEntities() {
        EntityManager manager = new EntityManager();
        Entity first = manager.createEntity();
        Entity second = manager.createEntity();
        assertEquals(2, manager.getEntityMap().size());
        assertTrue(manager.getEntityMap().containsKey(first.getId()));
        assertTrue(manager.getEntityMap().containsKey(second.getId()));
    }
}