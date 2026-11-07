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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        Entity other = (Entity) obj;
        return this.getId() == other.getId();
    }

    // can add, check if has, and get components based on classes (e.g. if has key Transform.class has Transform object)
    public boolean add(Component component) {
        // if component already exists not added (prevent accidental overwrite, instead remove existing and add new)
        if (this.has(component.getClass())) { return false; }
        components.put(component.getClass(), component);
        return true;
    }
    public boolean has(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }
    public <T extends Component> Component remove(Class<T> componentClass) {
        if (!this.has(componentClass)) { return null; }
        return this.components.remove(componentClass);
    }
    // return the type of class inferred that extends Component interface
    public <T extends Component> T get(Class<T> componentClass) {
        // casting allows to infer based on type, e.g. get(Transform.class) --> no need to cast it using (Transform) on the hashmap getter 
        return componentClass.cast(components.get(componentClass));
    }
}
