package com.liamreal.game;

import javax.sound.sampled.Clip;
import com.liamreal.assets.AssetConfig;
import com.liamreal.assets.SoundPlayer;
import com.liamreal.assets.TextureManager;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.ecs.Entity;
import com.liamreal.factory.BackgroundFactory;
import com.liamreal.factory.EnemyFactory;
import com.liamreal.factory.PlayerFactory;
import com.liamreal.util.Vector2f;
import com.liamreal.view.Viewer;

public class GameLoop {
    private final Model world;
    private final Viewer canvas;
    private final TextureManager textureManager = new TextureManager();
    private final LevelLoader levelLoader = new LevelLoader(textureManager);
	private static int targetFPS = 100;

    public GameLoop(Model world, Viewer canvas) {
        this.world = world;
        this.canvas = canvas;
    }

    public void start() {
        this.gameLoop();
    }

    // create basic things needed at start of game (background, players for example, enemies and the rest are created in Model itself)
    void initialiseGame() {
        // background is also an entity
		BackgroundFactory.createBackground(world.getBackgroundManager(), textureManager);
		PlayerFactory.createPlayerOne(world.getEntityManager(), textureManager);
		PlayerFactory.createPlayerTwo(world.getEntityManager(), textureManager);
		EnemyFactory.createBasicEnemy(world.getEntityManager(), textureManager);

        Entity velocityEntity = world.getEntityManager().createEntity();
        velocityEntity.add(new Transform(0, 0));
        velocityEntity.add(new Velocity(1));
        Velocity velocity = velocityEntity.get(Velocity.class);
        velocity.setDirection(new Vector2f(1, 1));
    }
    
    private void gameLoop() {
        this.initialiseGame();
        // only loop if there are levels remaining
        while (levelLoader.hasLevels()) {
            levelLoader.loadNextLevel();
            boolean levelComplete = false;
            // loop background music clip
            Clip musicClip = SoundPlayer.loopSound(String.format(
                "%s/sounds/background/background.wav",
                AssetConfig.getAssetsPath()
            ));
            while(!levelComplete)   //not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple 
            { 
                // time calculation for game framerate
                int TimeBetweenFrames =  1000 / targetFPS;
                long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames; 

                // wait till next time step 
                while (FrameCheck > System.currentTimeMillis()){} 
                
                // tick to check if level completed
                levelComplete = this.tick();
            }
            // stop looping music after level is done
            SoundPlayer.stopSound(musicClip);
        }
    }

	// for each level check given game logic if completed
	boolean tick() { 
		// model update   
		boolean isLevelComplete = world.update();
		// view update 
		canvas.updateView();
		// return boolean for if level completed
		return isLevelComplete; 
	}
}
