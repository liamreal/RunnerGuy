package com.liamreal.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liamreal.components.combat.Damage;
import com.liamreal.components.combat.Health;
import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.ecs.Entity;
import com.liamreal.physics.Collision;
import com.liamreal.physics.shapes.Circle;
import com.liamreal.time.TimeProvider;
import com.liamreal.util.Vector2f;

class CombatSystemTest {
    private TimeProvider timeProvider;
    
    @BeforeEach
    void setUp() {
        timeProvider = mock(TimeProvider.class);
    }

    private Entity createEntity(int id, int damage, int health, TimeProvider timeProvider) {
        Entity entity = new Entity(id);
        entity.add(new Transform(0, 0));
        entity.add(new Velocity(0));
        entity.add(new Collider(new Circle(new Vector2f(), 1)));
        entity.add(new Damage(damage));
        entity.add(new Health(health, timeProvider));
        return entity;
    }

    @Test
    void damageDamagesOtherEntityWhenThisEntityHasDamageAndOtherHasHealth() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS // damage()
                );
        Entity thisEntity = new Entity(1);
        thisEntity.add(new Damage(1));
        Entity otherEntity = new Entity(2);
        otherEntity.add(new Health(50, timeProvider));

        CombatSystem combatSystem = new CombatSystem();

        combatSystem.damage(thisEntity, otherEntity);

        assertEquals(49, otherEntity.get(Health.class).getHealth());
    }

    @Test
    void damageDoesNothingWhenThisEntityDoesNotHaveDamage() {
        Entity thisEntity = new Entity(1);
        Entity otherEntity = new Entity(2);
        otherEntity.add(new Health(50, timeProvider));

        CombatSystem combatSystem = new CombatSystem();

        combatSystem.damage(thisEntity, otherEntity);

        assertEquals(50, otherEntity.get(Health.class).getHealth());
    }

    @Test
    void damageDoesNothingWhenOtherEntityDoesNotHaveHealth() {
        Entity thisEntity = new Entity(1);
        thisEntity.add(new Damage(1));
        Entity otherEntity = new Entity(2);

        CombatSystem combatSystem = new CombatSystem();

        combatSystem.damage(thisEntity, otherEntity);

        assertFalse(otherEntity.has(Health.class));
    }

    @Test
    void updateDamagesBothEntitiesWithRequiredComponents() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor 1
                        0L,  // constructor 2
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS,  // damage() 1
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS   // damage() 2
                );
        // create 2 objects with both properties
        Entity thisEntity = this.createEntity(1, 1, 20, timeProvider);
        Entity otherEntity = this.createEntity(2, 2, 50, timeProvider);

        Collision collision = new Collision(thisEntity, otherEntity);
        List<Collision> collisions = new ArrayList<>();
        collisions.add(collision);

        CombatSystem combatSystem = new CombatSystem();

        combatSystem.update(collisions);

        // both should have health reduced by each others damage, i.e. this health minus other damage, other damage minus this health
        assertEquals(18, thisEntity.get(Health.class).getHealth());
        assertEquals(49, otherEntity.get(Health.class).getHealth());
    }




}