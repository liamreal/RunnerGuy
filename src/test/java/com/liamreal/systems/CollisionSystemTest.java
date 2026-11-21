package com.liamreal.systems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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

    private List<Entity> createEntityList() {
        // arraylist makes it mutable so can add entities to returned list if need be
        return new ArrayList<>(List.of(createEntity(1, 0, 0), createEntity(2, 1, 0)));
    }

    @Test
    void constructor_initialisesWithNoCollisions() {
        CollisionSystem system = new CollisionSystem();

        assertTrue(system.getCurrentCollisions().isEmpty());
    }
    
    @Test
    void getCurrentCollisionsAsList_returnsEmptyListWhenNoCollisions() {
        CollisionSystem system = new CollisionSystem();

        List<Collision> collisions = system.getCurrentCollisionsAsList();

        assertTrue(collisions.isEmpty());
    }

    @Test
    void collide_addsToCurrentCollisions_whenEntitiesAreColliding() {
        CollisionSystem system = new CollisionSystem();
        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 1, 0);
        Collision collision = new Collision(entityOne, entityTwo);

        system.checkCollisions(List.of(entityOne, entityTwo), 0); // always start at 0 (1st entity in list)

        // convert to list so can get only index and ensure collision data correct
        assertEquals(collision, system.getCurrentCollisionsAsList().get(0));
    }

    @Test
    void collide_doesNotAddToCurrentCollisions_whenEntitiesAreNotColliding() {
        CollisionSystem system = new CollisionSystem();
        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = createEntity(2, 3, 0); // ensure does not collide by moving further away
        List<Entity> entities = List.of(entityOne, entityTwo);

        system.checkCollisions(entities, 0);

        assertTrue(system.getCurrentCollisions().isEmpty()); // empty because not added
    }
    
    @Test
    void collide_doesNothing_whenEitherEntityMissingCollider() {
        CollisionSystem system = new CollisionSystem();
        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(1, 0));
        entityTwo.add(new Velocity(0));
        // No Collider -- can call check collisions below but will be ignored

        // if this entity missing collider, does not continue in loop, so collision not added, regardless which of two missing it
        system.checkCollisions(List.of(entityOne, entityTwo), 0);
        system.checkCollisions(List.of(entityTwo, entityOne), 0);

        assertTrue(system.getCurrentCollisions().isEmpty()); // empty because not added
    }

    @Test
    void collide_throwsException_whenEntityMissingTranform() {
        CollisionSystem system = new CollisionSystem();
        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Velocity(0));
        entityTwo.add(new Collider(new Circle(new Vector2f(0, 0), 1)));
        // No Transform -- throw once collide called

        assertThrows(
            RuntimeException.class,
            () -> system.checkCollisions(List.of(entityOne, entityTwo), 0)
        );
    }

    @Test
    void collide_throwsException_whenEntityMissingVelocity() {
        CollisionSystem system = new CollisionSystem();
        Entity entityOne = createEntity(1, 0, 0);
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(1, 0));
        entityTwo.add(new Collider(new Circle(new Vector2f(0, 0), 1)));
        // No Velocity -- throw once collide called

        assertThrows(
            RuntimeException.class,
            () -> system.checkCollisions(List.of(entityOne, entityTwo), 0)
        );
    }

    @Test
    void clearCurrentCollisions_removesAllCollisions() {
        CollisionSystem system = new CollisionSystem();
        system.checkCollisions(createEntityList(), 0); // will collide since both colliders length 1 and 1 unit apart

        system.clearCurrentCollisions();

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void checkCollisions_checksCollisionsAfterCurrentEntity() {
        CollisionSystem system = new CollisionSystem();
            List<Entity> entities = createEntityList();
            entities.add(createEntity(3, 4, 0)); // add new entity that does not collide

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
        List<Entity> entities = List.of(
            entityOne,
            entityTwo
        );

        for (int entityIndex = 0; entityIndex < entities.size(); entityIndex++) {
            system.checkCollisions(entities, entityIndex);
        }

        // at least 1 collision
        assertFalse(system.getCurrentCollisionsAsList().isEmpty());
    }

    @Test
    void checkCollisions_doesNotAddEquivalentPairs() {
        CollisionSystem system = new CollisionSystem();
        List<Entity> entities = createEntityList();
        entities.add(createEntity(3, 0, 1));
        
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
        // none colliding
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

        assertTrue(system.getCurrentCollisions().isEmpty()); // none collide so empty
    }

    @Test
    void checkCollisions_doesNotAddCollision_ifOnlyOneEntity() {
        CollisionSystem system = new CollisionSystem();
        Entity entityOne = createEntity(1, 0, 0);
        List<Entity> entities = List.of(entityOne);

        system.checkCollisions(entities, 0);

        assertTrue(system.getCurrentCollisions().isEmpty());
    }

    @Test
    void checkCollisions_canBeCalledMultipleTimesWithoutDuplicateCollisions() {
        CollisionSystem system = new CollisionSystem();
        List<Entity> entities = createEntityList();

        system.checkCollisions(entities, 0);
        system.checkCollisions(entities, 0);

        assertEquals(1, system.getCurrentCollisions().size()); // only 1 collision due to it being a set
    }
}