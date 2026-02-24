package com.liamreal.logic.explosion;

import java.util.concurrent.CopyOnWriteArrayList;

import com.liamreal.enums.Direction;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.logic.object.ObjectLogicManager;
import com.liamreal.objects.ExplosionObject;
import com.liamreal.objects.GameObject;

public class ExplosionLogicManager extends ObjectLogicManager {
    // list of explosions
	private CopyOnWriteArrayList<ObjectLogic> explosions = super.getObjects();

    public ExplosionLogicManager() {
        super();
        this.setDefaultMoveDirection(Direction.DOWN);
    }
    // by default move explosions in default direction set in constructor 
    public void moveExplosions() {
        this.moveExplosions(this.getDefaultMoveDirection());
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
