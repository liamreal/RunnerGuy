package com.liamreal.systems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.ecs.Entity;
import com.liamreal.physics.shapes.Circle;
import com.liamreal.physics.Collision;
import com.liamreal.util.Vector2f;

class CollisionSystemTest {

    private Entity createEntity(int id, double x, double y) {
        Entity entity = new Entity(id);
        entity.add(new Transform(x, y));
        entity.add(new Velocity(0));
        entity.add(new Collider(new Circle(new Vector2f(0, 0), 1)));
        return entity;
    }

    @Test
    void constructor_initialisesWithNoCollisions() {
        CollisionSystem system = new CollisionSystem();

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void addCurrentCollision_addsCollision() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);

        Collision collision = new Collision(entityOne, entityTwo);

        system.addCurrentCollision(collision);

        assertEquals(1, system.getCurrentCollisions().size());
        assertTrue(system.getCurrentCollisions().contains(collision));
    }

    @Test
    void addCurrentCollision_doesNotAddDuplicateCollision() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);

        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityTwo, entityOne);

        system.addCurrentCollision(collisionOne);
        system.addCurrentCollision(collisionTwo);

        System.out.println(collisionOne.equals(collisionTwo));
        System.out.println(collisionOne);
        System.out.println(collisionTwo);
        System.out.println(system.getCurrentCollisions());

        assertEquals(1, system.getCurrentCollisions().size());
    }

    @Test
    void clearCurrentCollisions_removesAllCollisions() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);

        system.collide(entityOne, entityTwo);

        assertEquals(1, system.getCurrentCollisions().size());

        system.clearCurrentCollisions();

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void getCurrentCollisionsAsList_returnsCollisions() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);

        Collision collision = new Collision(entityOne, entityTwo);

        system.addCurrentCollision(collision);

        List<Collision> collisions = system.getCurrentCollisionsAsList();

        assertEquals(1, collisions.size());
        assertTrue(collisions.contains(collision));
    }

    @Test
    void getCurrentCollisionsAsList_returnsEmptyListWhenNoCollisions() {
        CollisionSystem system = new CollisionSystem();

        List<Collision> collisions = system.getCurrentCollisionsAsList();

        assertTrue(collisions.isEmpty());
    }

    @Test
    void collide_addsCollisionWhenEntitiesCollide() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);

        system.collide(entityOne, entityTwo);

        assertEquals(1, system.getCurrentCollisions().size());
    }

    @Test
    void collide_doesNotAddCollisionWhenEntitiesDoNotCollide() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 3, 0);

        system.collide(entityOne, entityTwo);

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void collide_throwsWhenEntityMissingRequiredComponent() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);

        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(1, 0));
        entityTwo.add(new Velocity(0));
        // No Collider

        assertThrows(
            RuntimeException.class,
            () -> system.collide(entityOne, entityTwo)
        );
    }

    @Test
    void checkCollisions_doesNothingWhenThisEntityHasNoCollider() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(0, 0));
        entityOne.add(new Velocity(0));
        // No Collider

        Entity entityTwo = createEntity(2, 1, 0);

        List<Entity> entities = List.of(entityOne, entityTwo);

        system.checkCollisions(entities, 0);

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void checkCollisions_skipsOtherEntityWithoutCollider() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);

        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(1, 0));
        entityTwo.add(new Velocity(0));
        // No Collider

        List<Entity> entities = List.of(entityOne, entityTwo);

        system.checkCollisions(entities, 0);

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void checkCollisions_checksCollisionsAfterCurrentEntity() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);
        Entity entityThree = createEntity(3, 4, 0);

        List<Entity> entities = List.of(
            entityOne,
            entityTwo,
            entityThree
        );

        system.checkCollisions(entities, 0);

        // entityOne collides with entityTwo
        // entityOne does not collide with entityThree
        assertEquals(1, system.getCurrentCollisions().size());
    }

    @Test
    void checkCollisions_resolvesCollisionsBeforeContinuing() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);
        Entity entityThree = createEntity(3, 0, 1);

        List<Entity> entities = List.of(
            entityOne,
            entityTwo,
            entityThree
        );

        for (int entityIndex = 0; entityIndex < entities.size(); entityIndex++) {
            system.checkCollisions(entities, entityIndex);
        }

        List<Collision> collisions = system.getCurrentCollisionsAsList();

        // at least 1 collision
        assertFalse(collisions.isEmpty());

        // collision resolution changed the entity positions
        Vector2f positionOne =
                entityOne.get(Transform.class).getPosition();

        Vector2f positionTwo =
                entityTwo.get(Transform.class).getPosition();

        assertNotEquals(0, positionOne.getX(), 0.0001);
        assertNotEquals(0, positionTwo.getX(), 0.0001);
    }

    @Test
    void checkCollisions_doesNotAddEquivalentPairs() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);
        Entity entityThree = createEntity(3, 0, 1);

        List<Entity> entities = List.of(
            entityOne,
            entityTwo,
            entityThree
        );

        for (int entityIndex = 0; entityIndex < entities.size(); entityIndex++) {
            system.checkCollisions(entities, entityIndex);
        }

        // pair (1, 2) is added then resolved, should not also have pair (2, 1) as (1, 2) == (2, 1)
        // pairs (1, 3) and (2, 3) are not added as 2 and 3 do not collide, and 1 colliding with 2 resolved and moved out of collision with 3
        assertEquals(1, system.getCurrentCollisions().size());
    }

    @Test
    void checkCollisions_doesNotAddNonCollidingPairs() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 3, 0);
        Entity entityThree = createEntity(3, 6, 0);

        List<Entity> entities = List.of(
            entityOne,
            entityTwo,
            entityThree
        );

        for (int entityIndex = 0; entityIndex < entities.size(); entityIndex++) {
            system.checkCollisions(entities, entityIndex);
        }

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void checkCollisions_doesNotCheckEntityAgainstItself() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);

        List<Entity> entities = List.of(entityOne);

        system.checkCollisions(entities, 0);

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void checkCollisions_canBeCalledMultipleTimesWithoutDuplicateCollisions() {
        CollisionSystem system = new CollisionSystem();

        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);

        List<Entity> entities = List.of(entityOne, entityTwo);

        system.checkCollisions(entities, 0);
        system.checkCollisions(entities, 0);

        assertEquals(1, system.getCurrentCollisions().size());
    }
}