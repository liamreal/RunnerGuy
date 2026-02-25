package com.liamreal.objects;

import com.liamreal.display.TextureLoader;

public class ExplosionObject extends GameObject {
    // will have some data specific to player and inherit superclass methods
    public ExplosionObject(GameObject gameObject) {
        // spawn explosion at other object location  
        super(gameObject.getCentre());
        this.setMinLifeTime(0.4);
        this.setHealth(0); // explosions have no health, will die after minimum lifetime
        this.setTexture();
	}

    // update texture
    public void updateTexture() { this.setTexture(); }
    
    // set texture specifically for explosion
    protected void setTexture() {
        super.setTexture(String.format(
            "%s/textures/explosions/explosion.png", 
            TextureLoader.getAssetsPath()
        ));
    }
}
