package com.liamreal.objects;

import com.liamreal.display.TextureLoader;
import com.liamreal.util.Point3f;

public class BulletObject extends GameObject {
    // will have some data specific to bullet and inherit superclass methods
    public BulletObject(GameObject playerObject) {  
        super(32, 64, findBulletCentre(playerObject));
        this.setTexture();
	}

    // helper method to calculate player centre so can call superclass constructor
    private static Point3f findBulletCentre(GameObject playerObject) {
        // get player centre
        Point3f playerCentre = playerObject.getCentre();
        // spawn in front of player (up above him)
        Point3f bulletCentre = new Point3f(playerCentre.getX()+(playerObject.getWidth()/4), playerCentre.getY()-(playerObject.getHeight()/2), 0);
        return bulletCentre;
    }

    // set text specifically for player
    protected void setTexture() {
        super.setTexture(String.format(
            "%s/textures/projectiles/bullet.png", 
            TextureLoader.getAssetsPath()
        ));
    }

}
