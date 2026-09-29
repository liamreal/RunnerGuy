package com.liamreal.systems;

import java.util.Collection;
import com.liamreal.ecs.Entity;
import com.liamreal.ecs.SystemUtils;
import com.liamreal.physics.Collision;
import com.liamreal.components.combat.Damage;
import com.liamreal.components.combat.Health;

public class CombatSystem {

    void damage(Entity thisEntity, Entity otherEntity) {
        if (thisEntity.has(Damage.class) && otherEntity.has(Health.class)) {
            Damage thisDamage = thisEntity.get(Damage.class);
            Health otherHealth = otherEntity.get(Health.class);
            otherHealth.damage(thisDamage.getDamage()); // damage others health by this damage
        }
    }

    public void update(Collection<Collision> collisions) {
        for(Collision collision : collisions) {
            // execute damage operation on collision
            SystemUtils.execute(this::damage, collision);
        }
    }



}
