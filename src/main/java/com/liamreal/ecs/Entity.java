package com.liamreal.ecs;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private int id;
    private Map<Class<? extends Component>, Component> components = new HashMap<>();

    // entity assigned id on creation
    public Entity(int id) {
        this.id = id;
    }

    // can add, check if has, and get components based on classes
    public void add(Component component) {
        components.put(component.getClass(), component);
    }
    public boolean has(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }
    public Component get(Class<? extends Component> componentClass) {
        // casting allows to infer based on type, e.g. get(Transform.class) --> no need to cast it using (Transform) on the hashmap getter 
        return componentClass.cast(components.get(componentClass));
    }
}
