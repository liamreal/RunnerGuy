package logic.bullet;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import objects.BulletObject;
import objects.GameObject;
import enums.BulletType;
import enums.Direction;
import logic.explosion.ExplosionLogicManager;
import logic.object.ObjectLogic;
import logic.object.ObjectLogicManager;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class BulletLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> bullets = super.getObjects();
    private BulletType bulletType = BulletType.EXPLODE; // default type of bullet, by default explode
    private ExplosionLogicManager explosionLogicManager = null; // will be used for explosion logic by bullet
    // private int maxEnemiesCanHit = 1; // by default bullet can only hit one enemy (can add item to add piercing)

    public BulletLogicManager() {
        super();
        // set logic managers used by bullet
        this.explosionLogicManager = new ExplosionLogicManager();
        this.setDefaultMoveDirection(Direction.UP);
    }


    // get type of bullet
    public BulletType getBulletType() { return this.bulletType; }
    public ExplosionLogicManager getExplosionLogicManager() { return this.explosionLogicManager; } // get explosions list from bullet
    public CopyOnWriteArrayList<ObjectLogic> getExplosions() { return this.getExplosionLogicManager().getExplosions(); } // get explosion manager from bullet
    public void setBulletType(BulletType newBulletType) { this.bulletType = newBulletType; }


    // by default move bullets up
    public void moveBullets() {
        this.moveBullets(this.getDefaultMoveDirection());
    }
    // move every bullet in list
    public void moveBullets(Direction direction) {
		super.moveObjects(direction);
        // move also other objects (such as this bullet's explosions)
        this.getExplosionLogicManager().moveExplosions();
    }

    // spawn bullet at player
    public GameObject spawnBullet(GameObject playerObject) {
        BulletLogic newBullet = new BulletLogic(new BulletObject(playerObject));
        bullets.add(newBullet);
        return newBullet.getGameObject();
    }

    public CopyOnWriteArraySet<GameObject> collideEnemy(ObjectLogicManager enemyLogicManager) {
        // cases for bullet-enemy collisions
        switch (this.bulletType){
            case KILL:
                return this.killEnemy(enemyLogicManager);
            case EXPLODE:
                return this.explodeEnemy(enemyLogicManager);
            default:
                // not a valid bullet type in cases
                String errorMessage = String.format("BulletType %s not in %s", this.bulletType, BulletType.getAllBulletTypes().toString());
                throw new IllegalArgumentException(errorMessage);
        }
    }

    // this one simply kills, can instead get all collided objects and spawn explosions at them, or summon new objects and make them go backwards
    public CopyOnWriteArraySet<GameObject> killEnemy(ObjectLogicManager enemyLogicManager) {
        return this.collide(enemyLogicManager);
    }

    // this explodes enemies, returns explosion objects that were spawned where enemies were
    public CopyOnWriteArraySet<GameObject> explodeEnemy(ObjectLogicManager enemyLogicManager) {
        ExplosionLogicManager explosionLogicManager = this.getExplosionLogicManager();
        // can also create explosions within object instead of using Model ExplosionLogicManager
        if (explosionLogicManager == null) { throw new IllegalArgumentException("Cannot explode enemies if ExplosionLogicManager in BulletLogicManager is null!"); }
        CopyOnWriteArrayList<GameObject> collidedEnemies = new CopyOnWriteArrayList<>(this.collide(enemyLogicManager));
        CopyOnWriteArrayList<GameObject> spawnedExplosions = new CopyOnWriteArrayList<>();
        // spawn explosion at each enemy
        for (GameObject enemy : collidedEnemies) {
            spawnedExplosions.add(explosionLogicManager.spawnExplosion(enemy));
        }
        // return as set
        return new CopyOnWriteArraySet<>(spawnedExplosions);
    }


    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getBullets() {
        return this.bullets;
    }
}
