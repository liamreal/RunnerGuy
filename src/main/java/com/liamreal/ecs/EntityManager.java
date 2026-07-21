package com.liamreal.ecs;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    private int nextId = 0;
    // allows easy access and removal when entities found
    private Map<Integer, Entity> entities = new HashMap<>();

    public Entity createEntity() {
        Entity entity = new Entity(nextId++);
        entities.put(entity.getId(), entity);
        return entity;
    }

    public Map<Integer,Entity> getEntityMap() {
        return this.entities;
    }

    // get number of entities
    public int getEntityCount() {
        return entities.size();
    }
}