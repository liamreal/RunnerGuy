package com.liamreal.logic.enemy;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import com.liamreal.enums.EnemyType;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.logic.object.ObjectLogicManager;
import com.liamreal.objects.EnemyObject;
import com.liamreal.user.Config;
import com.liamreal.util.CooldownHandler;
import com.liamreal.enums.Direction;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class EnemyLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> enemies = super.getObjects();
    private EnemyType enemyType = EnemyType.BASIC;
    private int defaultEnemyHealth = super.getDefaultObjectHealth();
    private double enemySpawnFrequencySeconds = super.getCooldownLength();

    public EnemyLogicManager() {
        super();
        // by default enemies move down
        this.setDefaultMoveDirection(Direction.DOWN);
        // set max num enemies based on config
        this.setMaxNumEnemies(Config.getInstance().getMaxNumEnemies());
        // // start with 2 basic enemies
        // enemies.add(new EnemyLogic(new EnemyObject(enemyType)));
        // enemies.add(new EnemyLogic(new EnemyObject(enemyType)));
    }

    // getters and setters
    public EnemyType getEnemyType() { return this.enemyType; }
    public int getMaxNumEnemies() { return super.getMaxNumObjects(); }
    public int getDefaultEnemyHealth() { return this.defaultEnemyHealth; }
    public void setEnemyType(EnemyType newEnemyType) { this.enemyType = newEnemyType;}
    public void setDefaultEnemyHealth(int newEnemyHealth) { this.defaultEnemyHealth = newEnemyHealth; }
    public void setEnemyDifficulty(EnemyType newEnemyType) { 
        switch (newEnemyType) {
            case BASIC:
                this.setEnemyType(EnemyType.BASIC);
                this.setDefaultEnemyHealth(1);
                break;
            case ADVANCED:
                this.setEnemyType(EnemyType.ADVANCED);
                this.setDefaultEnemyHealth(2);
                break;
            default:
                // not a valid enemy type in cases
                String errorMessage = String.format("EnemyType %s not in %s", newEnemyType, EnemyType.getAllEnemyTypes().toString());
                throw new IllegalArgumentException(errorMessage);
        }
    }
    public void setMaxNumEnemies(int newMaxNumEnemies) { 
        super.setMaxNumObjects(newMaxNumEnemies);
    }



    // by default move enemies down
    public void moveEnemies() {
        this.moveEnemies(this.getDefaultMoveDirection());
    }
    // move every enemy in list
    public void moveEnemies(Direction direction) {
		super.moveObjects(direction);
    }




    // spawns an enemy by adding a new EnemyLogic to list
    public boolean spawnEnemyAttempt() {
        // get time since last enemy and calculate how much time passed
        Instant lastEnemySpawnTime = super.getCooldownStartTime();
        // if time since last enemy has surpassed frequency time, eligible to spawn (may not spawn based on chance tho)
        if (!CooldownHandler.isOnCooldown(lastEnemySpawnTime, this.enemySpawnFrequencySeconds)) {
            // reset the start time
            super.resetCooldown();
            // 50% chance to spawn 1 enemy every 1/maxNumEnemies of a second e.g. if 4 enemies every 1/4 of a second, 
            // it picks whether to spawn or not from range of [0,2) <--- EXCLUDES 2!!!
            int randomSpawnChance = ThreadLocalRandom.current().nextInt(0, 2);
            // if hit correct chance to spawn enemy and less than max number of enemies
            if (randomSpawnChance == 1 && this.enemies.size() < this.getMaxNumEnemies()) {
                // add enemy of specified type
                EnemyLogic newEnemy = new EnemyLogic(new EnemyObject(this.getEnemyType()));
                newEnemy.setHealth(this.getDefaultEnemyHealth());
                this.enemies.add(newEnemy);
                return true;
            }
        }
        // enemy was not spawned or failed to spawn (based on random chance)
        return false;
    }

    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getEnemies() {
        return this.enemies;
    }
}
