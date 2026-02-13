package logic.explosion;

import java.util.concurrent.CopyOnWriteArrayList;

import enums.Direction;
import logic.object.ObjectLogic;
import logic.object.ObjectLogicManager;
import objects.ExplosionObject;
import objects.GameObject;

public class ExplosionLogicManager extends ObjectLogicManager {
    // list of explosions
	private CopyOnWriteArrayList<ObjectLogic> explosions = super.getObjects();

    public ExplosionLogicManager() {
        super();
        super.setDefaultMoveDirection(Direction.DOWN);
    }
    // by default move explosions in default direction set in constructor 
    public void moveExplosions() {
        this.moveExplosions(super.getDefaultMoveDirection());
    }
    // move every explosion in list
    public void moveExplosions(Direction direction) {
		super.moveObjects(direction);
    }


    
    // spawn explosion at player
    public GameObject spawnExplosion(GameObject gameObject) {
        ExplosionLogic newExplosion = new ExplosionLogic(new ExplosionObject(gameObject));
        explosions.add(newExplosion);
        return newExplosion.getGameObject();
    }


    
    // obtain list of explosions
    public CopyOnWriteArrayList<ObjectLogic> getExplosions() {
        return this.explosions;
    }


}
