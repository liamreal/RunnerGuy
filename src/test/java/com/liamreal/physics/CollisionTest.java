package com.liamreal.physics;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.ecs.Entity;
import com.liamreal.physics.shapes.Circle;
import com.liamreal.util.Vector2f;

public class CollisionTest {
    
    // helper method to easily create entity
    private Entity createEntity(
            int id,
            Vector2f position,
            Vector2f velocity,
            double colliderLength
        ) {

        Entity entity = new Entity(id);

        Transform transform = new Transform(new Vector2f());
        transform.setPosition(position);

        Velocity velocityComponent = new Velocity(0);
        velocityComponent.setDirection(velocity);

        Collider collider = new Collider(new Circle(new Vector2f(), colliderLength));

        entity.add(transform);
        entity.add(velocityComponent);
        entity.add(collider);

        return entity;
    }

    // ===============================
    // === hasRequiredComponents() ===
    // ===============================

    @Test
    void hasRequiredComponents_returnsTrueWhenAllComponentsExist() {
        Entity entity = new Entity(2);
        entity.add(new Transform(0, 0));
        entity.add(new Velocity(0));
        entity.add(new Collider(new Circle(new Vector2f(0, 0), 0)));
        assertTrue(Collision.hasRequiredComponents(entity));
    }

    @Test
    void hasRequiredComponents_returnsFalseWhenTransformMissing() {
        Entity entity = new Entity(1);

        entity.add(new Velocity(0));
        entity.add(new Collider(new Circle(new Vector2f(), 0)));

        assertFalse(Collision.hasRequiredComponents(entity));
    }

    @Test
    void hasRequiredComponents_returnsFalseWhenVelocityMissing() {
        Entity entity = new Entity(1);

        Transform transform = new Transform(new Vector2f());
        transform.setPosition(new Vector2f(0, 0));

        entity.add(transform);
        entity.add(new Collider(new Circle(new Vector2f(), 0)));

        assertFalse(Collision.hasRequiredComponents(entity));
    }

    @Test
    void hasRequiredComponents_returnsFalseWhenColliderMissing() {
        Entity entity = new Entity(1);

        Transform transform = new Transform(new Vector2f());
        transform.setPosition(new Vector2f(0, 0));

        entity.add(transform);
        entity.add(new Velocity(0));

        assertFalse(Collision.hasRequiredComponents(entity));
    }

    
    // ===============================
    // ========= Constructor =========
    // ===============================


    @Test
    void constructor_storesEntities() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertSame(entityOne, collision.thisEntity);
        assertSame(entityTwo, collision.otherEntity);
    }

    @Test
    void constructor_getsRequiredComponents() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertSame(
                entityOne.get(Transform.class),
                collision.thisTransform
        );

        assertSame(
                entityOne.get(Velocity.class),
                collision.thisVelocity
        );

        assertSame(
                entityOne.get(Collider.class),
                collision.thisCollider
        );

        assertSame(
                entityTwo.get(Transform.class),
                collision.otherTransform
        );

        assertSame(
                entityTwo.get(Velocity.class),
                collision.otherVelocity
        );

        assertSame(
                entityTwo.get(Collider.class),
                collision.otherCollider
        );
    }


    @Test
    void constructor_throwsWhenThisEntityIsMissingComponents() {
        Entity entityOne = new Entity(1);
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> new Collision(entityOne, entityTwo)
        );

        assertTrue(
                exception.getMessage().contains("missing required components")
        );
    }

    @Test
    void constructor_throwsWhenOtherEntityIsMissingComponents() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> new Collision(entityOne, entityTwo)
        );

        assertTrue(
                exception.getMessage().contains("missing required components")
        );
    }




    // ========================================================
    // ======================= equals() =======================
    // ========================================================

    @Test
    void equals_returnsFalseForNull() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertFalse(collision.equals(null)); // testing collision obj equals null
    }

    @Test
    void equals_returnsFalseForDifferentClass() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertFalse(collision.equals("not a collision")); // testing collision obj equals string obj
    }

    @Test
    public void equals_returnsTrueForSameEntityPair() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));
        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityOne, entityTwo);
        assertEquals(collisionOne, collisionTwo);
        assertEquals(collisionTwo, collisionOne);
    }

    @Test
    public void equals_returnsTrueWhenEntitiesAreDifferentOrder() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));
        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityTwo, entityOne);
        assertEquals(collisionOne, collisionTwo);
        assertEquals(collisionTwo, collisionOne);
    }

    @Test
    void equals_returnsFalseForDifferentEntityPair() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));
        Entity entityThree = new Entity(3);
        entityThree.add(new Transform(100, 100));
        entityThree.add(new Velocity(2));
        entityThree.add(new Collider(new Circle(new Vector2f(100, 100), 1)));

        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityOne, entityThree);

        assertNotEquals(collisionOne, collisionTwo);
    }


    // =========================================================
    // ======================== check() ========================
    // =========================================================

    @Test
    void check_returnsFalseWhenEntitiesAreFarApart() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(34, 34));
        entityTwo.add(new Velocity(5));
        entityTwo.add(new Collider(new Circle(new Vector2f(34, 34), 2)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertFalse(collision.check());
    }

    @Test
    void check_returnsTrueWhenEntitiesOverlap() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(50, 60));
        entityOne.add(new Velocity(20));
        entityOne.add(new Collider(new Circle(new Vector2f(50, 60), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(50, 60));
        entityTwo.add(new Velocity(20));
        entityTwo.add(new Collider(new Circle(new Vector2f(50, 60), 5)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertTrue(collision.check());
    }

    @Test
    void check_returnsTrueWhenEntitiesAreExactlyTouching() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(0, 0));
        entityOne.add(new Velocity(0));
        entityOne.add(new Collider(new Circle(new Vector2f(0, 0), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(10, 0));
        entityTwo.add(new Velocity(0));
        entityTwo.add(new Collider(new Circle(new Vector2f(10, 0), 5)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertTrue(collision.check());
    }

    @Test
    void check_returnsFalseWhenEntitiesAreJustOutsideCollisionDistance() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(0, 0));
        entityOne.add(new Velocity(0));
        entityOne.add(new Collider(new Circle(new Vector2f(0, 0), 5)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(11, 0));
        entityTwo.add(new Velocity(0));
        entityTwo.add(new Collider(new Circle(new Vector2f(11, 0), 5)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertFalse(collision.check());
    }
    

    // =========================================================
    // ======================= resolve() =======================
    // =========================================================

    @Test
    void resolve_forceSeparatesEntitiesWhenVelocityDifferenceIsSmall() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(0, 0));
        entityOne.add(new Velocity(0));
        entityOne.add(new Collider(new Circle(new Vector2f(0, 0), 1)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(1, 0));
        entityTwo.add(new Velocity(0));
        entityTwo.add(new Collider(new Circle(new Vector2f(0, 0), 1)));

        Collision collision = new Collision(entityOne, entityTwo);

        // MUST REFLECT INPUT CONDITIONS
        collision.resolve(
                new Vector2f(-1, 0),
                -1,
                2
        );

        Vector2f positionOne =
                entityOne.get(Transform.class).getPosition();

        Vector2f positionTwo =
                entityTwo.get(Transform.class).getPosition();

        assertEquals(2.0, positionOne.getX(), 0.0001);
        assertEquals(1.5, positionOne.getY(), 0.0001);

        assertEquals(-1.0, positionTwo.getX(), 0.0001);
        assertEquals(-1.5, positionTwo.getY(), 0.0001);
    }

    @Test
    void check_handlesZeroVelocityEntities() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(0, 0));
        entityOne.add(new Velocity(0));
        entityOne.add(new Collider(new Circle(new Vector2f(0, 0), 1)));
        Entity entityTwo = new Entity(2);
        entityTwo.add(new Transform(1, 0));
        entityTwo.add(new Velocity(0));
        entityTwo.add(new Collider(new Circle(new Vector2f(0, 0), 1)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertDoesNotThrow(collision::check);
        assertTrue(collision.check());
    }

    @Test
    void check_handlesEntitiesAtSamePosition() {
        Entity entityOne = new Entity(1);
        entityOne.add(new Transform(0, 0));
        entityOne.add(new Velocity(0));
        entityOne.add(new Collider(new Circle(new Vector2f(0, 0), 1)));
        Entity entityTwo = new Entity(1);
        entityTwo.add(new Transform(0, 0));
        entityTwo.add(new Velocity(0));
        entityTwo.add(new Collider(new Circle(new Vector2f(0, 0), 1)));

        Collision collision = new Collision(entityOne, entityTwo);

        assertDoesNotThrow(collision::check);
        assertTrue(collision.check());
    }
}