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

    private void addCollisionComponents(Entity entity) {
        entity.add(new Transform(0, 0));
        entity.add(new Velocity(0));
        entity.add(new Collider(new Circle(new Vector2f(), 1)));
    }

    // simple tests to ensure that both combinations of entity method calls are called
    @Test
    void executeWithEntitiesExecutesMethodForBothEntityOrders() {
        Entity thisEntity = new Entity(1);
        Entity otherEntity = new Entity(2);

        List<String> calls = new ArrayList<>();

        BiConsumer<Entity, Entity> method =
                (first, second) -> calls.add(first.getId() + " -> " + second.getId());

        SystemUtils.execute(method, thisEntity, otherEntity);

        assertEquals(
                List.of("1 -> 2", "2 -> 1"),
                calls
        );
    }

    @Test
    void executeWithCollisionExecutesMethodForBothEntityOrders() {
        Entity thisEntity = new Entity(1);
        this.addCollisionComponents(thisEntity);
        Entity otherEntity = new Entity(2);
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