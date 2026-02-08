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
    private double bulletCooldownLength = super.getCooldownLength();

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
        // // get time since last enemy and calculate how much time passed
        // Instant lastBulletFireTime = super.getCooldownStartTime();
        // // if time since last enemy has surpassed frequency time, eligible to spawn (may not spawn based on chance tho)
        // if (!CooldownHandler.isOnCooldown(lastBulletFireTime, this.bulletCooldownLength)) {
        //     // reset the start time
        //     super.resetCooldown();
        //     // successful bullet spawn
        //     return true;
        // }
        // // enemy was not spawned or failed to spawn (based on random chance)
        // return false;
    }

    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getBullets() {
        return this.bullets;
    }
}
