package com.liamreal.objects;

import com.liamreal.assets.AssetLoader;
import com.liamreal.assets.SoundPlayer;
import com.liamreal.util.Point3f;

public class BulletObject extends GameObject {
    // will have some data specific to bullet and inherit superclass methods
    public BulletObject(GameObject playerObject) {  
        super(32, 32, findBulletCentre(playerObject));
        this.setTexture();
        this.setSounds();
	}

    // helper method to calculate player centre so can call superclass constructor
    private static Point3f findBulletCentre(GameObject playerObject) {
        // get player centre
        Point3f playerCentre = playerObject.getCentre();
        // spawn in front of player (up above him)
        Point3f bulletCentre = new Point3f(playerCentre.getX()+(playerObject.getWidth()/4), playerCentre.getY()-(playerObject.getHeight()/2), 0);
        return bulletCentre;
    }

    public void setSounds() {
        this.setFireSound(String.format(
            "%s/sounds/projectiles/fire.wav",
            AssetLoader.getAssetsPath()
        ));
    }

    // update texture
    public void updateTexture() { this.setTexture(); }
    public void updateSounds() { this.setSounds(); }

    public void playFireSound() {
        SoundPlayer.playSound(this.getFireSound());
    }

    // set text specifically for player
    protected void setTexture() {
        super.setTexture(String.format(
            "%s/textures/projectiles/bullet.png", 
            AssetLoader.getAssetsPath()
        ));
    }

}
