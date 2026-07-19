package com.liamreal.ecs;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private final int id;
    private Map<Class<? extends Component>, Component> components = new HashMap<>();

    // entity assigned id on creation
    public Entity(int id) {
        this.id = id;
    }

    // can get entity id (for entity deletion)
    public int getId() { return this.id; }

    // can add, check if has, and get components based on classes (e.g. if has key Transform.class has Transform object)
    public void add(Component component) {
        components.put(component.getClass(), component);
    }
    public boolean has(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }
    // return the type of class inferred that extends Component interface
    public <T extends Component> T get(Class<T> componentClass) {
        // casting allows to infer based on type, e.g. get(Transform.class) --> no need to cast it using (Transform) on the hashmap getter 
        return componentClass.cast(components.get(componentClass));
    }
}
