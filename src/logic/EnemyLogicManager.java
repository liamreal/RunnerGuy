package logic;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import enums.EnemyType;
import objects.EnemyObject;
import util.GameObject;
import enums.Direction;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class EnemyLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> enemies = super.getObjects();
    private EnemyType enemyType = EnemyType.BASIC;
    private double enemySpawnFrequencySeconds = 0.5;

    public EnemyLogicManager() {
        super();
        // // start with 2 basic enemies
        // enemies.add(new EnemyLogic(new EnemyObject(enemyType)));
        // enemies.add(new EnemyLogic(new EnemyObject(enemyType)));
    }

    // getters and setters
    public EnemyType getEnemyType() { return this.enemyType; }
    public int getMaxNumEnemies() { return super.getMaxNumObjects(); }
    public void setEnemyType(EnemyType newEnemyType) { this.enemyType = newEnemyType;}
    public void setMaxNumEnemies(int newMaxNumEnemies) { super.setMaxNumObjects(newMaxNumEnemies); }



    // by default move enemies down
    public void moveEnemies() {
        this.moveEnemies(Direction.DOWN);
    }
    // move every enemy in list
    public void moveEnemies(Direction direction) {
		super.moveObjects(direction);
    }

    // spawns an enemy by adding a new EnemyLogic to list
    public boolean spawnEnemyAttempt() {
        // get time since last enemy and calculate how much time passed
        Instant lastEnemySpawnTime = super.getCooldownStartTime();
        Duration durationSinceLastEnemy = Duration.between(lastEnemySpawnTime, Instant.now());
        double secondsSinceLastEnemy = durationSinceLastEnemy.getSeconds() + durationSinceLastEnemy.getNano() / 1000000000.0;
        // if time since last enemy has surpassed frequency time, eligible to spawn (may not spawn based on chance tho)
        if (secondsSinceLastEnemy > this.enemySpawnFrequencySeconds) {
            // reset the start time
            this.resetCooldown();
            // 50% chance to spawn 1 enemy every 1/4 of a second, picking from [0,2) <--- EXCLUDES 2!!!
            int randomSpawnChance = ThreadLocalRandom.current().nextInt(0, 2);
            // if hit correct chance to spawn enemy and less than max number of enemies
            if (randomSpawnChance == 1 && this.enemies.size() < this.getMaxNumEnemies()) {
                // add enemy of specified type
                this.enemies.add(new EnemyLogic(new EnemyObject(this.getEnemyType())));
                return true;
            }
        }
        // enemy was not spawned or failed to spawn (based on random chance)
        return false;
    }

    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getEnemies() {
        return enemies;
    }
}
