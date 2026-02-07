package logic;

import java.util.concurrent.CopyOnWriteArrayList;

import enums.EnemyType;
import objects.EnemyObject;

// generic method to manage different logics (will manage for example enemy logic thru a subclass)
public class EnemyLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> enemies = super.getObjects();

    public EnemyLogicManager() {
        super();
        // start with 2 basic enemies
        enemies.add(new EnemyLogic(new EnemyObject(EnemyType.BASIC)));
        enemies.add(new EnemyLogic(new EnemyObject(EnemyType.BASIC)));
    }

    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getEnemies() {
        return enemies;
    }

}
