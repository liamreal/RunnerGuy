package com.liamreal.objects;

import com.liamreal.display.TextureLoader;
import com.liamreal.enums.EnemyType;
import com.liamreal.util.Point3f;


public class EnemyObject extends GameObject {
    private EnemyType enemyType;
    // will have some data specific to player and inherit superclass methods
    public EnemyObject(EnemyType enemyType) {  
        super();
        super.setRandomCentre();
        // set enemy type
        this.setEnemyType(enemyType);
	}
    // superclass constructors we dont want to use for child class throw error for
    public EnemyObject(String textureLocation, int width, int height, Point3f centre) { 
        super(textureLocation, width, height, centre);
        throw new RuntimeException("No EnemyType provided in EnemyObject constructor!");
	}
    public EnemyObject(int width,int height, Point3f centre) { 
        this(width, height, centre, EnemyType.BASIC);
        throw new RuntimeException("No EnemyType provided in EnemyObject constructor!");
	} 
    public EnemyObject(int width,int height, Point3f centre, EnemyType enemyType) { 
        super(width, height, centre);
        // set enemy texture
        this.setEnemyType(enemyType);
	}

    public EnemyType getEnemyType() { return this.enemyType; }
    public void setEnemyType(EnemyType newEnemyType) { 
        this.enemyType = newEnemyType; 
        this.setTexture(enemyType);
    }

    // update texture
    public void updateTexture() { this.setTexture(); }

    protected void setTexture() { this.setTexture(this.getEnemyType()); }

    // set text specifically for enemy
    protected void setTexture(EnemyType enemyType) {
        String enemy = enemyType.toString();
        super.setTexture(String.format(
            "%s/textures/enemies/%s/%s.png", 
            TextureLoader.getAssetsPath(),
            enemy,
            enemy
        ));
    }

    
		// EnemiesList.add(new EnemyObject(50,50,new Point3f(((float)Math.random()*50+500 ),0,0)));
}
