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
    public void collide(Entity thisEntity, Entity otherEntity) {
        Transform thisTransform = thisEntity.get(Transform.class);
        Transform otherTransform = otherEntity.get(Transform.class);

        Collider thisCollider = thisEntity.get(Collider.class);
        Vector2f thisPosition = thisTransform.getPosition();
        Collider otherCollider = otherEntity.get(Collider.class);
        Vector2f otherPosition = otherTransform.getPosition();

        thisCollider.setPosition(thisPosition);
        otherCollider.setPosition(otherPosition);

        Vector2f impactVector = thisPosition.MinusVector(otherPosition);
        double distance = impactVector.length();

        if (distance <= thisCollider.getLength() + otherCollider.getLength()) {
            double overlap = distance - (thisCollider.getLength() + otherCollider.getLength());
            this.calculateNewVelocities(thisEntity, otherEntity, overlap, impactVector.copy());
        }
    }

    // calculate new velocities for two entities using overlap between each other and impact vector
    void calculateNewVelocities(Entity thisEntity, Entity otherEntity, double overlap, Vector2f impactVector) {
        Vector2f direction = impactVector.Normal().byScalar(overlap * 0.5); // set magnitude of vector for each direction to half of overlap of 2 circles
        Transform thisTransform = thisEntity.get(Transform.class);
        Vector2f thisPosition = thisTransform.getPosition();
        Transform otherTransform = otherEntity.get(Transform.class);
        Vector2f otherPosition = otherTransform.getPosition();

        thisTransform.setPosition(thisPosition.PlusVector(direction));
        otherTransform.setPosition(otherPosition.MinusVector(direction));

        Collider thisCollider = thisEntity.get(Collider.class);
        Collider otherCollider = otherEntity.get(Collider.class);

        double distance = thisCollider.getLength() + otherCollider.getLength();
        impactVector = impactVector.Normal().byScalar(distance);



        Velocity thisVelocity = thisEntity.get(Velocity.class);
        Velocity otherVelocity = otherEntity.get(Velocity.class);
        Vector2f velocityDifference = otherVelocity.getDirection().MinusVector(thisVelocity.getDirection());

        // this means stuck so fallback code to get unstuck moves them in opposite directions
        if (velocityDifference.length() <= 0.1) {
            thisTransform.setPosition(thisTransform.getPosition().PlusVector((new Vector2f(1, 1)).byScalar(1.5)));
            otherTransform.setPosition(otherTransform.getPosition().PlusVector((new Vector2f(-1, -1)).byScalar(1.5)));
            return;
        }



        // introducing lower/upper limits for numerator to allow for predictable interaction with movable entities with zero Velocity component
        double numerator = Math.min(Math.max(velocityDifference.dot(impactVector), distance*2), distance*distance);
        double denominator = distance * distance;
        

        Vector2f deltaVA = impactVector.copy();
        double divisionResult;
        // catch / 0 case if entities in same exact position
        if (denominator == 0) { divisionResult = 0; }
        else { divisionResult = numerator/denominator; }
        // System.out.println(String.format("%.2f / %.2f = %.2f", numerator, denominator, divisionResult));
        deltaVA = deltaVA.byScalar(2 * (divisionResult));

        Vector2f deltaVB = impactVector.copy();
        deltaVB = deltaVB.byScalar(-2 * (divisionResult)); // negative because other direction


        // update both this and other as they interacted with each other
        thisVelocity.setDirection(thisVelocity.getDirection().PlusVector(deltaVA));
        otherVelocity.setDirection(otherVelocity.getDirection().PlusVector(deltaVB));
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
