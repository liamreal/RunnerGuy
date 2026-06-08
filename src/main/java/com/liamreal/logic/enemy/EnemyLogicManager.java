// 22371791 Liam Kerrin
package com.liamreal.logic.enemy;

import java.util.concurrent.CopyOnWriteArrayList;
import com.liamreal.enums.EnemyType;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.logic.object.ObjectLogicManager;
import com.liamreal.objects.EnemyObject;
import com.liamreal.user.Config;
import com.liamreal.enums.Direction;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class EnemyLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> enemies = super.getObjects();
    private EnemyType enemyType = EnemyType.BASIC;

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
    protected EnemyType getEnemyType() { return this.enemyType; }
    protected EnemyType getEnemyDifficulty() { return this.getEnemyType(); }
    public int getMaxNumEnemies() { return super.getMaxNumObjects(); }
    public int getDefaultEnemyHealth() { return super.getDefaultObjectHealth(); }
    public void setDefaultEnemyHealth(int newEnemyHealth) { super.setDefaultObjectHealth(newEnemyHealth); }
    protected void setEnemyType(EnemyType newEnemyType) { this.enemyType = newEnemyType; }
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


    public boolean spawnEnemyAttempt() {
        EnemyLogic newEnemy = new EnemyLogic(new EnemyObject(this.getEnemyType()));
        return super.spawnObjectAttempt(newEnemy);
    }


    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getEnemies() {
        return this.enemies;
    }
}
