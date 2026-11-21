package com.liamreal.ecs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EntityManagerTest {
    @Test
    void createEntity_addsEntityToManager() {
        EntityManager manager = new EntityManager();

        Entity entity = manager.createEntity();

        assertSame(entity, manager.getEntity(entity.getId()));
    }

    @Test
    void createEntity_assignsUniqueIds() {
        EntityManager manager = new EntityManager();

        Entity first = manager.createEntity();
        Entity second = manager.createEntity();

        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    void getEntityMap_initiallyEmpty() {
        EntityManager manager = new EntityManager();

        assertTrue(manager.getEntityMap().isEmpty());
    }

    @Test
    void getEntityCount_countsAllCreatedEntities() {
        EntityManager manager = new EntityManager();

        manager.createEntity();
        manager.createEntity();

        assertEquals(2, manager.getEntityCount());
    }
}