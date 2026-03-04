package com.liamreal.objects;

import com.liamreal.assets.AssetLoader;
import com.liamreal.assets.SoundPlayer;
import com.liamreal.enums.EnemyType;
import com.liamreal.util.Point3f;


public class EnemyObject extends GameObject {
    private EnemyType enemyType;
    // will have some data specific to player and inherit superclass methods
    public EnemyObject(EnemyType enemyType) {  
        super();
        super.setRandomCentre();
        // set enemy type
        this.setEnemyDifficulty(enemyType);
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
        this.setEnemyDifficulty(enemyType);
	}

    public EnemyType getEnemyType() { return this.enemyType; }
    public void setEnemyDifficulty(EnemyType newEnemyType) { 
        switch (newEnemyType) {
            case BASIC:
                this.setEnemyType(EnemyType.BASIC);
                this.setHealth(1);
                break;
            case ADVANCED:
                this.setEnemyType(EnemyType.ADVANCED);
                this.setHealth(2);
                break;
            default:
                // not a valid enemy type in cases
                String errorMessage = String.format("EnemyType %s not in %s", newEnemyType, EnemyType.getAllEnemyTypes().toString());
                throw new IllegalArgumentException(errorMessage);
        }
        // set sounds
        this.setSounds();
        this.setTexture(enemyType);
    }

    // set necessary sounds
    protected void setSounds() {
        this.setHurtSound(String.format(
            "%s/sounds/enemies/%s/hurt.wav",
            AssetLoader.getAssetsPath(),
            this.getEnemyType()
        ));
        this.setDeathSound(String.format(
            "%s/sounds/enemies/%s/death.wav",
            AssetLoader.getAssetsPath(),
            this.getEnemyType()
        ));
    }

    public void playHurtSound() {
        SoundPlayer.playSound(this.getHurtSound());
    }
    public void playDeathSound() {
        SoundPlayer.playSound(this.getDeathSound());
    }

    // update texture
    public void updateTexture() { this.setTexture(); }
    public void updateSounds() { this.setSounds(); }

    protected void setTexture() { this.setTexture(this.getEnemyType()); }
    protected void setEnemyType(EnemyType newEnemyType) { this.enemyType = newEnemyType; }

    // set text specifically for enemy
    protected void setTexture(EnemyType enemyType) {
        String enemy = enemyType.toString();
        super.setTexture(String.format(
            "%s/textures/enemies/%s/%s.png", 
            AssetLoader.getAssetsPath(),
            enemy,
            enemy
        ));
    }

    
		// EnemiesList.add(new EnemyObject(50,50,new Point3f(((float)Math.random()*50+500 ),0,0)));
}
