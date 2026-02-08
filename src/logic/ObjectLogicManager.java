package logic;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;

import util.GameObject;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> objects  = new CopyOnWriteArrayList<ObjectLogic>();

    public ObjectLogicManager() {}

    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getObjects() {
        return objects;
    }

    public boolean collide(GameObject objectOne, GameObject objectTwo) {
        return true;
    }

}
