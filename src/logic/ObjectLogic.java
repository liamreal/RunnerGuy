package logic;
import util.GameObject;
import util.Vector3f;
import util.Point3f;

import java.util.Arrays;

import enums.Direction;
import user.Config;

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

    // by default moves object down (if no argument) using method overloading
    public void move() {
        this.move(Direction.DOWN);
    }

    // otherwise can specify direction based on enum, returns Direction, used to check then if out of bounds
    public Direction move(Direction direction) {
        Point3f centre = this.gameObject.getCentre();
        switch(direction) {
            // put down as first direction as most of time will be this
            case DOWN:
                centre.ApplyVector(new Vector3f(0,this.moveSpeed,0));
                return Direction.DOWN;
            case UP:
                centre.ApplyVector(new Vector3f(0,-this.moveSpeed,0));
                return Direction.UP;
            case LEFT:
                centre.ApplyVector(new Vector3f(-this.moveSpeed,0,0));
                return Direction.LEFT;
            case RIGHT:
                centre.ApplyVector(new Vector3f(this.moveSpeed,0,0));
                return Direction.RIGHT;
            // if not valid throw exception
            default:
                // not a valid direction in cases
                String errorMessage = String.format("Direction %s not in %s", direction, Direction.getAllDirections().toString());
                throw new IllegalArgumentException(errorMessage);
        }
    }

}
