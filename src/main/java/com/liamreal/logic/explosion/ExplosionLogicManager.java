// 22371791 Liam Kerrin
package com.liamreal.logic.explosion;

import java.util.concurrent.CopyOnWriteArrayList;

import com.liamreal.assets.AssetLoader;
import com.liamreal.assets.SoundPlayer;
import com.liamreal.enums.Direction;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.logic.object.ObjectLogicManager;
import com.liamreal.objects.ExplosionObject;
import com.liamreal.objects.GameObject;

public class ExplosionLogicManager extends ObjectLogicManager {
    // list of explosions
	private CopyOnWriteArrayList<ObjectLogic> explosions = super.getObjects();
    private String explosionSound;

    public ExplosionLogicManager() {
        super();
        this.setDefaultMoveDirection(Direction.DOWN);
        this.setExplosionSound();
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
        SoundPlayer.playSound(this.getExplosionSound()); // explosion spawned so play sound
        return newExplosion.getGameObject();
    }


    private String getExplosionSound() {
        return this.explosionSound;
    }
    private void setExplosionSound() {
        this.explosionSound = String.format(
            "%s/sounds/explosions/explosion.wav",
            AssetLoader.getAssetsPath()
        );
    }

    
    // obtain list of explosions
    public CopyOnWriteArrayList<ObjectLogic> getExplosions() {
        return this.explosions;
    }


}
