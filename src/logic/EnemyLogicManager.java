package logic;

import java.util.concurrent.CopyOnWriteArrayList;

import enums.EnemyType;
import objects.EnemyObject;
import util.GameObject;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class EnemyLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> enemies = super.getObjects();
    private EnemyType enemyType = EnemyType.BASIC;

    public EnemyLogicManager() {
        super();
        // start with 2 basic enemies
        enemies.add(new EnemyLogic(new EnemyObject(enemyType)));
        enemies.add(new EnemyLogic(new EnemyObject(enemyType)));
    }

    // getters and setters for enemy type
    public EnemyType getEnemyType() {
        return this.enemyType;
    }
    public void setEnemyType(EnemyType newEnemyType) {
        this.enemyType = newEnemyType;
    }

    public void moveEnemies() {
		for (ObjectLogic enemy : enemies) {
            enemy.move();
            // if enemy goes out of bound
            if (OutOfBoundsLogic.isOutOfBounds(enemy)){
                enemies.remove(enemy);
            }
        }
    }

    public boolean spawnEnemy() {
        return true;
    }



    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getEnemies() {
        return enemies;
    }

}
