package com.liamreal.physics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.ecs.Entity;
import com.liamreal.util.Vector2f;

public class Collision {
    // this entity
    public Entity thisEntity;
    public Transform thisTransform;
    public Vector2f thisPosition;
    public Velocity thisVelocity;
    public Collider thisCollider;
    // other entity
    public Entity otherEntity;
    public Transform otherTransform;
    public Vector2f otherPosition;
    public Velocity otherVelocity;
    public Collider otherCollider;

    public Collision(Entity thisEntity, Entity otherEntity) {
        this.thisEntity = thisEntity;
        this.otherEntity = otherEntity;
        // if do not have required components, error
        if (!Collision.hasRequiredComponents(thisEntity) || !Collision.hasRequiredComponents(otherEntity)) {
            throw new RuntimeException(String.format("Cannot create Collision for entities %d and %d: missing required components", thisEntity.getId(), otherEntity.getId()));
        }
        // get all components required for collision calculations
        this.getRequiredComponents();
        this.setColliderPositions();
    }

    public Entity getThisEntity() { return this.thisEntity; }
    public Entity getOtherEntity() { return this.otherEntity; }

    // get required components
    private void getRequiredComponents() {
        thisTransform = thisEntity.get(Transform.class);
        thisVelocity = thisEntity.get(Velocity.class);
        thisCollider = thisEntity.get(Collider.class);
        otherTransform = otherEntity.get(Transform.class);
        otherVelocity = otherEntity.get(Velocity.class);
        otherCollider = otherEntity.get(Collider.class);
    }

    private void setColliderPositions() {
        thisPosition = thisTransform.getPosition();
        thisCollider.setPosition(thisPosition);
        otherPosition = otherTransform.getPosition();
        otherCollider.setPosition(otherPosition);
    }

    // for set usage, otherwise treats Collision(1, 2) same as Collision(2, 1)
    @Override
    public int hashCode() {
        int id1 = this.getThisEntity().getId();
        int id2 = this.getOtherEntity().getId();

        // order-independent hash (using addition), by chatgpt
        return Integer.hashCode(id1) + Integer.hashCode(id2);
    }

    // for comparing if 2 collisions objects same, just compare entity ids
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        Collision other = (Collision) obj;
        // make list for each collision containing the two colliding entities
        List<Integer> thisCollisionEntities = new ArrayList<>(List.of(this.getThisEntity().getId(), this.getOtherEntity().getId()));
        List<Integer> otherCollisionEntities = new ArrayList<>(List.of(other.getThisEntity().getId(), other.getOtherEntity().getId()));
        // sort so order doesnt matter (i.e. this vs other == other vs this)
        Collections.sort(thisCollisionEntities);
        Collections.sort(otherCollisionEntities);
        // if both collisions have both of same entities, in any order, they are same collision
        return thisCollisionEntities.equals(otherCollisionEntities);
    }
    

    public boolean check() {
        Vector2f impactVector = thisPosition.MinusVector(otherPosition);
        double distance = impactVector.length();

        // check if overlapping each other
        double sumLengths = thisCollider.getLength() + otherCollider.getLength();
        if (distance <= sumLengths) {
            double overlap = distance - sumLengths;
            this.resolve(impactVector, overlap, sumLengths);
            // objects collided so true
            return true;
        }
        // did not collide so false
        return false;
    }

    // calculate new velocities for two entities using overlap between each other and impact vector
    void resolve(Vector2f impactVector, double overlap, double sumLengths) {
        // separate the entities
        this.separateEntities(impactVector, overlap);

        // get difference in velocity between the two colliding entities
        Vector2f velocityDifference = this.calculateVelocityDifference();

        // this means stuck so fallback code to get unstuck moves them in opposite directions
        if (velocityDifference.length() <= 0.1) {
            this.forceSeparateEntities();
            return;
        }

        // part of the conservation of momentum formula (the division part with difference between two velocities and positions)
        double divisionResult = this.calculateDifferenceDivision(velocityDifference, impactVector, sumLengths);
        // given the result of division, can calculate the difference in velocity and add it to current velocities
        this.updateNewVelocities(impactVector, divisionResult);
    }

    private void separateEntities(Vector2f impactVector, double overlap) {
        // set magnitude of vector for each direction to half of overlap of 2 circles
        Vector2f direction = impactVector.Normal().byScalar(overlap * 0.5);
        thisTransform.setPosition(thisPosition.PlusVector(direction));
        otherTransform.setPosition(otherPosition.MinusVector(direction));
    }

    private Vector2f calculateVelocityDifference() {
        return otherVelocity.getDirection().MinusVector(thisVelocity.getDirection());
    }

    private void forceSeparateEntities() {
        thisTransform.setPosition(thisTransform.getPosition().PlusVector((new Vector2f(1, 1)).byScalar(1.5)));
        otherTransform.setPosition(otherTransform.getPosition().PlusVector((new Vector2f(-1, -1)).byScalar(1.5)));
    }

    private double calculateDifferenceDivision(Vector2f velocityDifference, Vector2f impactVector, double distance) {
        // introducing lower/upper limits for numerator to allow for predictable interaction with movable entities with zero Velocity component
        double numerator = Math.min(Math.max(velocityDifference.dot(impactVector), distance*2), distance*distance);
        double denominator = distance * distance;
        // catch / 0 case if entities in same exact position
        if (denominator == 0) { return 0; }
        return numerator/denominator;
    }

    private void updateNewVelocities(Vector2f impactVector, double divisionResult) {
        Vector2f deltaVA = impactVector.copy();
        // System.out.println(String.format("%.2f / %.2f = %.2f", numerator, denominator, divisionResult));
        deltaVA = deltaVA.byScalar(2 * (divisionResult));

        Vector2f deltaVB = impactVector.copy();
        deltaVB = deltaVB.byScalar(-2 * (divisionResult)); // negative because other direction

        // update both this and other as they interacted with each other
        thisVelocity.setDirection(thisVelocity.getDirection().PlusVector(deltaVA));
        otherVelocity.setDirection(otherVelocity.getDirection().PlusVector(deltaVB));
    }

    // to verify Entity has components required for this system
    public static boolean hasRequiredComponents(Entity entity) {
        if (!entity.has(Transform.class) ||
            !entity.has(Velocity.class) ||
            !entity.has(Collider.class)) {
            return false;
        }
        return true;
    }

    public String toString() {
        // collision of this entity id with other entity id
        return String.format("Collision(%d, %d)", this.getThisEntity().getId(), this.getOtherEntity().getId());
    }
}
