package logic.bullet;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import objects.BulletObject;
import objects.GameObject;
import enums.Direction;
import logic.object.ObjectLogic;
import logic.object.ObjectLogicManager;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class BulletLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> bullets = super.getObjects();
    // private int maxEnemiesCanHit = 1; // by default bullet can only hit one enemy (can add item to add piercing)

    public BulletLogicManager() {
        super();
        this.setDefaultMoveDirection(Direction.UP);
    }
    // by default move bullets up
    public void moveBullets() {
        this.moveBullets(this.getDefaultMoveDirection());
    }
    // move every bullet in list
    public void moveBullets(Direction direction) {
		super.moveObjects(direction);
    }

    // spawn bullet at player
    public GameObject spawnBullet(GameObject playerObject) {
        BulletLogic newBullet = new BulletLogic(new BulletObject(playerObject));
        bullets.add(newBullet);
        return newBullet.getGameObject();
    }

    public CopyOnWriteArraySet<GameObject> collideEnemy(ObjectLogicManager enemyLogicManager) {
        return this.collide(enemyLogicManager);
    }

    // this one simply kills, can instead get all collided objects and spawn explosions at them, or summon new objects and make them go backwards
    public CopyOnWriteArraySet<GameObject> killEnemy(ObjectLogicManager enemyLogicManager) {
        return this.collideEnemy(enemyLogicManager);
    }


    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getBullets() {
        return this.bullets;
    }
}
