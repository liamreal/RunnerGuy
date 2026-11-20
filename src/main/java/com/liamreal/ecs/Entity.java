package com.liamreal.ecs;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private final int id;
    // can add, check if has, and get components based on classes (e.g. if has key Transform.class has Transform object)
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

    public <T extends Component> boolean add(T component) {
        // if component null, or if already exists not added (prevent accidental overwrite, instead use replace)
        if (component == null || this.has(component.getClass())) { return false; }
        components.put(component.getClass(), component);
        return true;
    }
    
    // replace with new component, return old one
    @SuppressWarnings("unchecked")
    public <T extends Component> T replace(T component) {
        if (component == null) { return null; } // cannot add a null component
        T removedComponent = this.remove((Class<T>) component.getClass()); // this cast gives warning but its safe, trust me bro
        this.add(component); // add new component
        return removedComponent;
    }
    public <T extends Component> boolean has(Class<T> componentClass) {
        return components.containsKey(componentClass);
    }
    // return the type of class inferred that extends Component interface
    public <T extends Component> T get(Class<T> componentClass) {
        // casting allows to infer based on type, e.g. get(Transform.class) --> no need to cast it using (Transform) on the hashmap getter 
        return componentClass.cast(components.get(componentClass));
    }
    public <T extends Component> T remove(Class<T> componentClass) {
        return componentClass.cast(components.remove(componentClass)); // returns null if not found
    }
}
