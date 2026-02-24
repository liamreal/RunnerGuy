package com.liamreal.logic.object;
import com.liamreal.util.Vector3f;
import com.liamreal.util.DurationHandler;
import com.liamreal.util.Point3f;
import java.util.concurrent.CopyOnWriteArrayList;
import com.liamreal.enums.Direction;
import com.liamreal.objects.GameObject;
import com.liamreal.user.Config;

public class ObjectLogic {
    // game object this logic class will control
    private GameObject gameObject;
    private int moveSpeed;

    // specify game object and moveSpeed of object
    public ObjectLogic(GameObject gameObject, int moveSpeed) {
        this.gameObject = gameObject;
        this.moveSpeed = moveSpeed;
    }
    // otherwise grab move speed from config and call above constructor
    public ObjectLogic(GameObject gameObject) {
        this(gameObject, Config.moveSpeed);
    }

    public GameObject getGameObject() {
        return gameObject;
    }
    public int getMoveSpeed() {
        return moveSpeed;
    }
    public int getHealth() {
        return getGameObject().getHealth();
    }
    public void setMoveSpeed(int newMoveSpeed) {
        this.moveSpeed = newMoveSpeed;
    }
    public void setHealth(int newHealth) {
        getGameObject().setHealth(newHealth);
    }
	public boolean hasLivedMinLifeTime() {
		return this.getGameObject().hasLivedMinLifeTime();
	}
    public boolean isAlive() {
        return this.getGameObject().isAlive();
    }
    
    // various generic collision checks
    public GameObject collide(GameObject otherObject) {
        if (gameObject.collide(otherObject)) {
            return gameObject;
        }
        return null;
    }
    public GameObject collide(ObjectLogic otherLogic) {
        return this.collide(otherLogic.getGameObject());
    }
    // maximum number of collisions allowed by our current object logic with n other logics (max = -1 means no limit)
    public CopyOnWriteArrayList<GameObject> collide(ObjectLogicManager otherLogicManager, int maxNumCollisions) {
        // count how many collisions so far (so can compare to max specified)
        int collisionCount = 0;
        CopyOnWriteArrayList<GameObject> collidedObjects = new CopyOnWriteArrayList<GameObject>();
        for (ObjectLogic otherLogic : otherLogicManager.getObjects()) {
            // if collide with another
            if (this.collide(otherLogic) != null){
                // if no cap on max (i.e. max = -1) or if not yet reached max number of collisions
                if (maxNumCollisions == -1 || collisionCount < maxNumCollisions) {
                    collidedObjects.add(otherLogic.getGameObject());
                    collisionCount++;
                }
            }
        }
        // return list of collided objects (if none collided, should be empty)
        return collidedObjects;
    }
    // by default if no limit specified assume there is no limit (i.e. pass into same func name with -1)
    public CopyOnWriteArrayList<GameObject> collide(ObjectLogicManager otherLogicManager) {
        return this.collide(otherLogicManager, -1);
    }

    public GameObject getMaxHealthGameObject(ObjectLogicManager objectLogicManager) {
        // list that will hold our objects we will obtain from ObjectLogicManager
        CopyOnWriteArrayList<GameObject> gameObjects = objectLogicManager.getGameObjects();
        // returns max health object
        return getMaxHealthGameObject(gameObjects);
    }
    // find max health out of objects in a list of objects
    public GameObject getMaxHealthGameObject(CopyOnWriteArrayList<GameObject> gameObjects) {
        // for now max health object is not found (list could be empty)
        GameObject maxHealthObject = null;
        for (GameObject object : gameObjects) {
            GameObject currentObject = object;
            // if null automatically assign this object
            if (maxHealthObject == null) { maxHealthObject = currentObject; }
            // otherwise check health of two objects and update max health one
            else {
                if (currentObject.getHealth() > maxHealthObject.getHealth()) {
                    maxHealthObject = currentObject;
                }
            }
        }
        // return found max health object (or null if not found)
        return maxHealthObject;
    }
    // get closest object
    public GameObject getClosestGameObject(CopyOnWriteArrayList<GameObject> gameObjects) {
        // sort objects by closest relative to this object
        CopyOnWriteArrayList<GameObject> closestObjects = this.getGameObject().sortByClosest(gameObjects);
        if (closestObjects == null) { return null; } // return null if function returned null (which it should if original list was null or empty)
        return closestObjects.get(0); // otherwise get first object (closest)
    }


    // otherwise can specify direction based on enum, returns Direction, used to check then if out of bounds
    public Direction move(Direction direction) {
        // get objects re-referenced below
        Point3f centre = this.getGameObject().getCentre();
        int moveSpeed = this.getMoveSpeed();
        switch(direction) {
            // put down as first direction as most of time will be this
            case DOWN:
                // WEIRD!!! im not sure why but for some reason the DOWN and UP are swapped?? but when you go down coords for y go up...
                centre.ApplyVector(new Vector3f(0,-moveSpeed,0));
                return Direction.DOWN;
            case UP:
                centre.ApplyVector(new Vector3f(0,moveSpeed,0));
                return Direction.UP;
            case LEFT:
                centre.ApplyVector(new Vector3f(-moveSpeed,0,0));
                return Direction.LEFT;
            case RIGHT:
                centre.ApplyVector(new Vector3f(moveSpeed,0,0));
                return Direction.RIGHT;
            // if not valid throw exception
            default:
                // not a valid direction in cases
                String errorMessage = String.format("Direction %s not in %s", direction, Direction.getAllDirections().toString());
                throw new IllegalArgumentException(errorMessage);
        }
    }

}
