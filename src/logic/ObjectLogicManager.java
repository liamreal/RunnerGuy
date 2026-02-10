package logic;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import enums.Direction;
import logic.ObjectLogic;
import objects.GameObject;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> objects  = new CopyOnWriteArrayList<ObjectLogic>();
    private Instant cooldownStartTime = Instant.now(); // for checking elapsed seconds between last enemy and current enemy for example
    private double cooldownLength = 0.5; // shared cooldown length in seconds (can be overwritten in subclass)
    private int maxNumObjects = 6; // max number of objects code default, can auto-define a max if not overwritten in subclass constructor, e.g. how max enemies in EnemyLogicManager is

    public ObjectLogicManager() {}

    
    public void setMaxNumObjects(int newMaxNumObjects) { this.maxNumObjects = newMaxNumObjects; }

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
    public int getMaxNumObjects() { return maxNumObjects; }
    private void setCooldownStartTime(Instant newTime) { this.cooldownStartTime = newTime; }


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
        objectLogicManager.keepOnlyAlive();
    }

    // collision for all this logic manager objects colliding with all objects in another logic manager
    public CopyOnWriteArraySet<GameObject> collide(ObjectLogicManager otherLogicManager) {
        CopyOnWriteArrayList<GameObject> allCollidedObjects  = new CopyOnWriteArrayList<GameObject>();
        // check all objects collided with other logic manager
		for (ObjectLogic object : this.getObjects()) {
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
        // keep only all objects in this logic manager that are alive (health > 0), if dead will be removed from list within method
        this.keepOnlyAlive();
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
    }

}
