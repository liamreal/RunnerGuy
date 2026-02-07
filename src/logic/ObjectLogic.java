package logic;
import util.GameObject;
import util.Vector3f;
import util.Point3f;
import enums.Direction;
import user.Config;

public class ObjectLogic {
    // game object this logic class will control
    private GameObject gameObject;
    private int moveSpeed;

    // constructor requires an associated game object for which to apply logic to
    public ObjectLogic(GameObject gameObject) {
        this.gameObject = gameObject;
        this.moveSpeed = Config.moveSpeed;
    }
    // can also specify moveSpeed of object (otherwise uses config class)
    public ObjectLogic(GameObject gameObject, int moveSpeed) {
        this.gameObject = gameObject;
        this.moveSpeed = moveSpeed;
    }

    // by default moves object down (if no argument)
    public void move() {
        gameObject.getCentre().ApplyVector(new Vector3f(0,this.moveSpeed,0));
    }

    // otherwise can specify direction based on enum
    public void move(Direction direction) {
        Point3f centre = this.gameObject.getCentre();
        switch(direction) {
            case UP:
                centre.ApplyVector(new Vector3f(0,-this.moveSpeed,0));
                break;
            case LEFT:
                centre.ApplyVector(new Vector3f(-this.moveSpeed,0,0));
                break;
            case RIGHT:
                centre.ApplyVector(new Vector3f(this.moveSpeed,0,0));
                break;
            // by default move down
            default:
                centre.ApplyVector(new Vector3f(0,this.moveSpeed,0));
                break;
        }
    }

}
