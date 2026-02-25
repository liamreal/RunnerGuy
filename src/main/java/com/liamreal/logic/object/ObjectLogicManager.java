package com.liamreal.logic.object;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import com.liamreal.enums.CollisionType;
import com.liamreal.enums.Direction;
import com.liamreal.logic.OutOfBoundsLogic;
import com.liamreal.logic.explosion.ExplosionLogicManager;
import com.liamreal.objects.GameObject;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> objects  = new CopyOnWriteArrayList<ObjectLogic>();
    private Instant cooldownStartTime = Instant.now(); // for checking elapsed seconds between last enemy and current enemy for example
    private int maxNumObjects = 6; // max number of objects code default, can auto-define a max if not overwritten in subclass constructor, e.g. how max enemies in EnemyLogicManager is
    private double cooldownLength = 1.0/(double)this.maxNumObjects; // shared cooldown length in seconds (can be overwritten in subclass)
    private Direction defaultMoveDirection; // default direction this object will move in, can set it
    private CollisionType collisionType = CollisionType.KILL; // default type of collision, by default killed (unless overwritten in constructor)
    private int defaultObjectHealth = 1;


    public ObjectLogicManager() {}

    
    public void setMaxNumObjects(int newMaxNumObjects) { this.maxNumObjects = newMaxNumObjects; }

    // -- getters --
    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getObjects() {
        return objects;
    }
    // will return a list of GameObject instances instead of ObjectLogic instances
    public CopyOnWriteArrayList<GameObject> getGameObjects() {
        // list that will hold our objects we will obtain from ObjectLogicManager
        CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<>();
        for (ObjectLogic objectLogic : this.objects) {
            gameObjects.add(objectLogic.getGameObject());
        }
        return gameObjects;
    }
    public Instant getCooldownStartTime() { return this.cooldownStartTime; }
    public double getCooldownLength() { return this.cooldownLength; }
    public int getMaxNumObjects() { return this.maxNumObjects; }
    public Direction getDefaultMoveDirection() { return this.defaultMoveDirection; }
    public CollisionType getCollisionType() { return this.collisionType; }
    public int getDefaultObjectHealth() { return this.defaultObjectHealth; }
    // -- setters --
    public void setDefaultMoveDirection(Direction newDirection) { this.defaultMoveDirection = newDirection; }
    public void setCollisionType(CollisionType newCollisionType) { this.collisionType = newCollisionType; }
    public void setDefaultObjectHealth(int newHealth) { this.defaultObjectHealth = newHealth; }
    // PRIVATE because need to only reset within manager
    private void setCooldownStartTime(Instant newTime) { this.cooldownStartTime = newTime; }


    // update all textures for all gameobjects this logic manager collection of logics interacts with
    public void updateTexture() {
        for (GameObject object : this.getGameObjects()) {
            System.out.println(object.getClass());
            object.updateTexture();
        }
    }


    // reset start time of cooldown (used for enemy spawns and later bullet spawns too)
    public void resetCooldown() {
        this.setCooldownStartTime(Instant.now());
    }

    // will go through each of its logic objects, check their game object health and remove if not positive
    public void keepOnlyAlive() {
        CopyOnWriteArrayList<ObjectLogic> objects = this.getObjects();
        for (ObjectLogic object: objects) {
            // if invalid health (0 or less), remove object logic from list
            if (!object.isAlive()){
                objects.remove(object);
            }
        }
    }

    // this one simply kills, can instead get all collided objects and spawn explosions at them, or summon new objects and make them go backwards
    public CopyOnWriteArraySet<GameObject> killObject(ObjectLogicManager otherLogicManager) {
        return this.collide(otherLogicManager);
    }

    // this explodes enemies, returns explosion objects that were spawned where now dead objects were
    public CopyOnWriteArraySet<GameObject> explodeObject(ObjectLogicManager otherLogicManager, ExplosionLogicManager explosionLogicManager) {
        // can also create explosions within object instead of using Model ExplosionLogicManager
        if (explosionLogicManager == null) { throw new IllegalArgumentException(String.format("Cannot explode enemies if ExplosionLogicManager in %s is null!", this.toString())); }
        CopyOnWriteArrayList<GameObject> collidedObjects = new CopyOnWriteArrayList<>(this.collide(otherLogicManager));
        CopyOnWriteArrayList<GameObject> resultObjects = new CopyOnWriteArrayList<>();
        // spawn explosion at each enemy
        for (GameObject object : collidedObjects) {
            // do not spawn an explosion on alive object
            if (!object.isAlive()) { resultObjects.add(explosionLogicManager.spawnExplosion(object)); }
            // if is alive instead just re-add object (this prevents bug with score not being incremented if explosion not spawned, 
            // instead of having to spawn explosion we just add collided object to list and it is counted in score)
            else { resultObjects.add(object); }
        }
        // return as set
        return new CopyOnWriteArraySet<>(resultObjects);
    }

    // collision interactions with enemies
    public CopyOnWriteArraySet<GameObject> collideEnemy(ObjectLogicManager otherLogicManager, ExplosionLogicManager explosionLogicManager) {
        // cases for bullet-enemy collisions
        switch (this.getCollisionType()){
            case KILL:
                return this.killObject(otherLogicManager);
            case EXPLODE:
                if (explosionLogicManager == null) {
                    // explosion logic manager cannot be null
                    String errorMessage = String.format("ExplosionLogicManager in %s cannot be null!!!", this.toString());
                    throw new IllegalArgumentException(errorMessage);
                }
                return this.explodeObject(otherLogicManager, explosionLogicManager);
            default:
                // not a valid bullet type in cases
                String errorMessage = String.format("CollisionType %s not in %s", this.getCollisionType(), CollisionType.getAllCollisionTypes().toString());
                throw new IllegalArgumentException(errorMessage);
        }
    }

    public void damageObjects(GameObject damageSource, ObjectLogicManager objectLogicManager, CopyOnWriteArrayList<GameObject> objectsToDamage) {
        // go through all game objects in list
        for (GameObject objectToDamage: objectsToDamage) {
            // get object to damage health (will be used to decrease bullet health)
            int objectToDamageHealth = objectToDamage.getHealth();
            // decrease object to be damaged by the bullet health and bullet by object to be damaged health
            objectToDamage.decreaseHealth(damageSource.getHealth());
            damageSource.decreaseHealth(objectToDamageHealth);
            // check bullet health, if < 1 breaks out of loop
            if (!damageSource.isAlive()) { break; }
        }
        // keep only all objects that are alive (health > 0), if dead will be removed from list
        // objectLogicManager.keepOnlyAlive();
    }

    // collision for all this logic manager objects colliding with all objects in another logic manager
    public CopyOnWriteArraySet<GameObject> collide(ObjectLogicManager otherLogicManager) {
        CopyOnWriteArrayList<GameObject> allCollidedObjects  = new CopyOnWriteArrayList<GameObject>();
        // check all objects collided with other logic manager
		for (ObjectLogic object : this.getObjects()) {
            // only add game objects to list if they have lived minimum amount of time to be collided with
            if (object.hasLivedMinLifeTime()) {
                // check current object collided other objects
                CopyOnWriteArrayList<GameObject> objectCollidedEnemies = object.collide(otherLogicManager);
                // no collisions for object so skip loop for this damage source
                if (objectCollidedEnemies.isEmpty()) { continue; }
                // sort other objects by closest to current object
                GameObject gameObject = object.getGameObject();
                CopyOnWriteArrayList<GameObject> closestCollidedObjects = gameObject.sortByClosest(objectCollidedEnemies);
                // damage objects
                this.damageObjects(gameObject, otherLogicManager, closestCollidedObjects);
                // add to list of all combined game objects
                allCollidedObjects.addAll(closestCollidedObjects);
            }
        }
        // keep only all objects in this logic manager that are alive (health > 0), if dead will be removed from list within method
        // this.keepOnlyAlive();
        // return as a set (eliminating duplicates)
        return new CopyOnWriteArraySet<GameObject>(allCollidedObjects);
    }

    // public CopyOnWriteArrayList<ObjectLogic> collide(ObjectLogicManager otherLogicManager) {
    //     CopyOnWriteArrayList<ObjectLogic> collidedObjects  = new CopyOnWriteArrayList<ObjectLogic>();
    //     // check all
	// 	for (ObjectLogic object : this.objects) {

    //     }
    // }

    // move every object in list
    public void moveObjects(Direction direction) {
		for (ObjectLogic object : this.objects) {
            // move object in given direction
            object.move(direction);
            // if object goes out of bound (screen size + object size)
            if (OutOfBoundsLogic.isOutOfBounds(object)){
                objects.remove(object);
            }
        }
        this.keepOnlyAlive(); // check if this itself works
    }

}
