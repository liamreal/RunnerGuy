package logic;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;

import enums.Direction;
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
