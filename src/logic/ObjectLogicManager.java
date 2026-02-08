package logic;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;

import enums.Direction;
import util.GameObject;

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
    public Instant getCooldownStartTime() { return this.cooldownStartTime; }
    public double getCooldownLength() { return this.cooldownLength; }
    public int getMaxNumObjects() { return maxNumObjects; }

    // reset start time of cooldown (used for enemy spawns and later bullet spawns too)
    public void resetCooldown() {
        this.cooldownStartTime = Instant.now();
    }


    // move every object in list
    public void moveObjects(Direction direction) {
		for (ObjectLogic object : objects) {
            // move object in given direction
            object.move(direction);
            // if object goes out of bound (screen size + object size)
            if (OutOfBoundsLogic.isOutOfBounds(object)){
                objects.remove(object);
            }
        }
    }

}
