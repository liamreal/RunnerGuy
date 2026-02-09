package logic;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import enums.EnemyType;
import objects.BulletObject;
import objects.EnemyObject;
import user.Config;
import util.GameObject;
import enums.Direction;
import util.CooldownHandler;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class BulletLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> bullets = super.getObjects();
    private int maxEnemiesCanHit = 1; // by default bullet can only hit one enemy (can add item to add piercing)

    public BulletLogicManager() {
        super();
    }
    // by default move bullets up
    public void moveBullets() {
        this.moveBullets(Direction.UP);
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
    

    public CopyOnWriteArrayList<GameObject> collideEnemy(EnemyLogicManager enemyLogicManager) {
        CopyOnWriteArrayList<GameObject> collidedEnemies  = new CopyOnWriteArrayList<GameObject>();
        // check all bullets collided with enemies
		for (ObjectLogic bullet : this.bullets) {
            // check current bullet collided enemies
            CopyOnWriteArrayList<GameObject> bulletColliedEnemies = bullet.collide(enemyLogicManager);

        }
    }

    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getBullets() {
        return this.bullets;
    }
}
