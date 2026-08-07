package com.liamreal.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.ecs.Entity;
import com.liamreal.util.Vector2f;

public class CollisionSystem {
    public void collide(Collection<Entity> entities) {
        // turn collection into list and filter out entities that do not have required components
        List<Entity> validEntities = new ArrayList<>(entities).stream().filter(entity -> this.hasRequiredComponents(entity)).collect(Collectors.toList());
        // render each entity
        for (int i = 0; i < validEntities.size() - 1; i++) {
            Entity thisEntity = validEntities.get(i);
            // then look at others can collide with
            for (int j = i + 1; j < validEntities.size(); j++) {
                // jth entity which ith collides with
                Entity otherEntity = validEntities.get(j);


                this.collide(thisEntity, otherEntity);

            }
        }
    }

    // collision between two entities
    private void collide(Entity thisEntity, Entity otherEntity) {
        Transform thisTransform = thisEntity.get(Transform.class);
        Transform otherTransform = otherEntity.get(Transform.class);
        Velocity thisVelocity = thisEntity.get(Velocity.class);
        Velocity otherVelocity = otherEntity.get(Velocity.class);

        Collider thisCollider = thisEntity.get(Collider.class);
        Collider otherCollider = otherEntity.get(Collider.class);

        Vector2f thisPosition = thisTransform.getPosition();
        Vector2f otherPosition = otherTransform.getPosition();

        double distance = thisPosition.euclideanDistance(otherPosition);

        if (distance <= thisCollider.getLength() + otherCollider.getLength()) {
            System.out.println(String.format("\n%d collided with %d: \n d = %.2f", thisEntity.getId(), otherEntity.getId(), distance));
        }

        // thisCollider.updatePosition(thisTransform.getPosition());
        // otherCollider.updatePosition(otherTransform.getPosition());
        


    }

    // to verify Entity has components required for this system
    private boolean hasRequiredComponents(Entity entity) {
        if (!entity.has(Transform.class) ||
            !entity.has(Velocity.class) ||
            !entity.has(Collider.class)) {
            return false;
        }
        return true;
    }

}
