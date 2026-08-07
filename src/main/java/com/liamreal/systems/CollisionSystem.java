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
    public Vector2f collide(Entity thisEntity, Entity otherEntity) {
        Transform thisTransform = thisEntity.get(Transform.class);
        Transform otherTransform = otherEntity.get(Transform.class);
        Velocity thisVelocity = thisEntity.get(Velocity.class);
        Velocity otherVelocity = otherEntity.get(Velocity.class);

        Collider thisCollider = thisEntity.get(Collider.class);
        Vector2f thisPosition = thisTransform.getPosition();
        Collider otherCollider = otherEntity.get(Collider.class);
        Vector2f otherPosition = otherTransform.getPosition();

        thisCollider.setPosition(thisPosition);
        otherCollider.setPosition(otherPosition);

        double distance = thisPosition.euclideanDistance(otherPosition);

        if (distance <= thisCollider.getLength() + otherCollider.getLength()) {
            System.out.println(String.format("\n%d collided with %d: \n d = %.2f", thisEntity.getId(), otherEntity.getId(), distance));

            Vector2f thisNewVelocityDirection = this.calculateNewVelocity(thisEntity, otherEntity);
            return thisNewVelocityDirection;
            // Vector2f otherNewVelocity = this.calculateNewVelocity(otherEntity, thisEntity);
            
            // thisVelocity.setDirection(thisNewVelocity);
            // otherVelocity.setDirection(otherNewVelocity);
            // // directly update movement here to account for MovementSystem reapplying later
            // Vector2f thisDisplacement = thisVelocity.calculateDisplacement();
            // Vector2f otherDisplacement = otherVelocity.calculateDisplacement();
            // // thisTransform.addDisplacement(thisDisplacement);
            // // thisCollider.setPosition(thisPosition);
            // // otherTransform.addDisplacement(otherDisplacement);
            // // otherCollider.setPosition(otherPosition);
        }
        return thisVelocity.getDirection();
    }

    // calculate and return new velocity for THIS object
    Vector2f calculateNewVelocity(Entity thisEntity, Entity otherEntity) {
        Velocity thisVelocity = thisEntity.get(Velocity.class);
        Velocity otherVelocity = otherEntity.get(Velocity.class);
        Vector2f thisPosition = thisEntity.get(Transform.class).getPosition();
        Vector2f otherPosition = otherEntity.get(Transform.class).getPosition();

        double distance = thisPosition.euclideanDistance(otherPosition);
        Vector2f velocityDifference = otherVelocity.getDirection().MinusVector(thisVelocity.getDirection());
        Vector2f positionDifference = thisPosition.MinusVector(otherPosition);

        
        double numerator = velocityDifference.dot(positionDifference);
        double denominator = distance * distance;
        
        
        Vector2f deltaVelocity = positionDifference.byScalar(numerator/denominator);
        Vector2f newVelocity = thisVelocity.getDirection().PlusVector(deltaVelocity).Normal();

        return newVelocity;


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
