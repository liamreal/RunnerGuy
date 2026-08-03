package com.liamreal.ecs;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    private int nextId = 0;
    // allows easy access and removal when entities found
    private Map<Integer, Entity> entityMap = new HashMap<>();

    public Entity createEntity() {
        Entity entity = new Entity(nextId++);
        entityMap.put(entity.getId(), entity);
        return entity;
    }

    public Map<Integer,Entity> getEntityMap() {
        return this.entityMap;
    }

    // get number of entities
    public int getEntityCount() {
        return entityMap.size();
    }
}