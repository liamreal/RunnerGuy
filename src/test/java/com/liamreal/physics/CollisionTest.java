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
            double speed,
            double colliderLength
        ) {

        Entity entity = new Entity(id);
        Transform transform = new Transform(position);
        Velocity velocity = new Velocity(0);
        Collider collider = new Collider(new Circle(position, colliderLength));

        entity.add(transform);
        entity.add(velocity);
        entity.add(collider);

        return entity;
    }

    // shorter one for where values dont matter
    private Entity createEntity(
            int id
        ) {
        return this.createEntity(id, new Vector2f(), 0, 0);
    }

    // ===============================
    // === hasRequiredComponents() ===
    // ===============================

    @Test
    void hasRequiredComponents_returnsTrueWhenAllComponentsExist() {
        Entity entity = this.createEntity(1);
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
        entity.add(new Collider(new Circle(new Vector2f(), 1)));

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
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);

        Collision collision = new Collision(entityOne, entityTwo);

        assertSame(entityOne, collision.thisEntity);
        assertSame(entityTwo, collision.otherEntity);
    }

    @Test
    void constructor_getsRequiredComponents() {
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);

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
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);

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
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
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
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);

        Collision collision = new Collision(entityOne, entityTwo);

        assertFalse(collision.equals(null)); // testing collision obj equals null
    }

    @Test
    void equals_returnsFalseForDifferentClass() {
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);

        Collision collision = new Collision(entityOne, entityTwo);

        assertFalse(collision.equals("not a collision")); // testing collision obj equals string obj
    }

    @Test
    public void equals_returnsTrueForSameEntityPair() {
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);
        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityOne, entityTwo);
        assertEquals(collisionOne, collisionTwo);
        assertEquals(collisionTwo, collisionOne);
    }

    @Test
    public void equals_returnsTrueWhenEntitiesAreDifferentOrder() {
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);
        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityTwo, entityOne);
        assertEquals(collisionOne, collisionTwo);
        assertEquals(collisionTwo, collisionOne);
    }

    @Test
    void equals_returnsFalseForDifferentEntityPair() {
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);
        Entity entityThree = this.createEntity(3, new Vector2f(100, 100), 2, 1);

        Collision collisionOne = new Collision(entityOne, entityTwo);
        Collision collisionTwo = new Collision(entityOne, entityThree);

        assertNotEquals(collisionOne, collisionTwo);
    }


    // =========================================================
    // ======================== check() ========================
    // =========================================================

    @Test
    void check_returnsFalseWhenEntitiesAreFarApart() {
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(30, 30), 5, 2);

        Collision collision = new Collision(entityOne, entityTwo);

        assertFalse(collision.check());
    }

    @Test
    void check_returnsTrueWhenEntitiesOverlap() {
        Entity entityOne = this.createEntity(1, new Vector2f(50, 60), 20, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(50, 60), 20, 5);

        Collision collision = new Collision(entityOne, entityTwo);

        assertTrue(collision.check());
    }

    @Test
    void check_returnsTrueWhenEntitiesAreExactlyTouching() {
        Entity entityOne = this.createEntity(1, new Vector2f(0, 0), 0, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(10, 0), 0, 5);

        Collision collision = new Collision(entityOne, entityTwo);

        assertTrue(collision.check());
    }

    @Test
    void check_returnsFalseWhenEntitiesAreJustOutsideCollisionDistance() {
        Entity entityOne = this.createEntity(1, new Vector2f(0, 0), 0, 5);
        Entity entityTwo = this.createEntity(2, new Vector2f(11, 0), 0, 5);

        Collision collision = new Collision(entityOne, entityTwo);

        assertFalse(collision.check());
    }
    

    // =========================================================
    // ======================= resolve() =======================
    // =========================================================

    @Test
    void resolve_forceSeparatesEntitiesWhenVelocityDifferenceIsSmall() {
        Entity entityOne = this.createEntity(1, new Vector2f(0, 0), 0, 1);
        Entity entityTwo = this.createEntity(2, new Vector2f(1, 0), 0, 1);

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
        Entity entityOne = this.createEntity(1, new Vector2f(0, 0), 0, 1);
        Entity entityTwo = this.createEntity(2, new Vector2f(1, 0), 0, 1);

        Collision collision = new Collision(entityOne, entityTwo);

        assertDoesNotThrow(collision::check);
        assertTrue(collision.check());
    }

    @Test
    void check_handlesEntitiesAtSamePosition() {
        Entity entityOne = this.createEntity(1);
        Entity entityTwo = this.createEntity(2);

        Collision collision = new Collision(entityOne, entityTwo);

        assertDoesNotThrow(collision::check);
        assertTrue(collision.check());
    }
}