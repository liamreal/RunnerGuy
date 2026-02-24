package com.liamreal.logic.bullet;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import com.liamreal.objects.BulletObject;
import com.liamreal.objects.GameObject;
import com.liamreal.enums.CollisionType;
import com.liamreal.enums.Direction;
import com.liamreal.logic.explosion.ExplosionLogicManager;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.logic.object.ObjectLogicManager;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class BulletLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> bullets = super.getObjects();
    private ExplosionLogicManager explosionLogicManager = null;
    // private int maxEnemiesCanHit = 1; // by default bullet can only hit one enemy (can add item to add piercing)

    public BulletLogicManager() {
        super();
        // set logic managers used by bullet
        this.explosionLogicManager = new ExplosionLogicManager();
        this.setBulletType(CollisionType.EXPLODE);
        this.setDefaultMoveDirection(Direction.UP);
    }


    // get type of bullet (kill, explosion, etc.)
    public CollisionType getBulletType() { return super.getCollisionType(); }
    public ExplosionLogicManager getExplosionLogicManager() { return this.explosionLogicManager; } // get explosion manager from bullet
    public CopyOnWriteArrayList<ObjectLogic> getExplosions() { return this.getExplosionLogicManager().getExplosions(); } // get explosion manager from bullet
    public void setBulletType(CollisionType newBulletType) { super.setCollisionType(newBulletType); }


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

    // call this class method with explosion logic manager
    public CopyOnWriteArraySet<GameObject> collideEnemy(ObjectLogicManager enemyLogicManager) {
        return this.collideEnemy(enemyLogicManager, this.getExplosionLogicManager());
    }
    // call superclass method
    public CopyOnWriteArraySet<GameObject> collideEnemy(ObjectLogicManager enemyLogicManager, ExplosionLogicManager explosionLogicManager) {
        return super.collideEnemy(enemyLogicManager, explosionLogicManager);
    }

    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getBullets() {
        return this.bullets;
    }
}
