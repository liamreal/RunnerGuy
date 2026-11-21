package com.liamreal.ecs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.Test;

import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.physics.Collision;
import com.liamreal.physics.shapes.Circle;
import com.liamreal.util.Vector2f;

class SystemUtilsTest {

    // helper method for adding specific things to entity
    private void addCollisionComponents(Entity entity) {
        entity.add(new Transform(0, 0));
        entity.add(new Velocity(0));
        entity.add(new Collider(new Circle(new Vector2f(), 1)));
    }

    // simple tests to ensure that both combinations of entity method calls are called
    @Test
    void execute_WithTwoEntities_AcceptsMethodInBothOrders() {
        Entity thisEntity = new Entity(1);
        Entity otherEntity = new Entity(2);
        List<String> calls = new ArrayList<>();
        // create a method that does something from the two arguments (here calls is local variable so in scope)
        BiConsumer<Entity, Entity> method =
                (first, second) -> calls.add(first.getId() + " -> " + second.getId());

        // run our method in test that is supposed to accept the BiConsumer method both ways
        SystemUtils.execute(method, thisEntity, otherEntity);

        assertEquals(
                List.of("1 -> 2", "2 -> 1"),
                calls
        );
    }

    // same as test above except passing in collision instead using method overloading, making sure it is unwrapped correctly
    @Test
    void execute_WithCollision_AcceptsMethodInBothOrders() {
        Entity thisEntity = new Entity(1);
        Entity otherEntity = new Entity(2);
        this.addCollisionComponents(thisEntity);
        this.addCollisionComponents(otherEntity);
        Collision collision = new Collision(thisEntity, otherEntity);
        List<String> calls = new ArrayList<>();
        BiConsumer<Entity, Entity> method =
                (first, second) -> calls.add(first.getId() + " -> " + second.getId());

        SystemUtils.execute(method, collision);

        assertEquals(
                List.of("1 -> 2", "2 -> 1"),
                calls
        );
    }
}