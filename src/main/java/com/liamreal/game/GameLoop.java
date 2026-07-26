package com.liamreal.game;

import javax.sound.sampled.Clip;

import com.liamreal.assets.AssetConfig;
import com.liamreal.assets.SoundPlayer;
import com.liamreal.assets.TextureManager;
import com.liamreal.factory.BackgroundFactory;
import com.liamreal.user.Config;
import com.liamreal.view.Viewer;

public class GameLoop {
    private final Model world;
    private final Viewer canvas;
    private final TextureManager textureManager;
    private final LevelManager levelManager;
	private static int targetFPS = 100;

    public GameLoop(Model world, Viewer canvas, TextureManager textureManager) {
        this.world = world;
        this.canvas = canvas;
        this.textureManager = textureManager;
        this.levelManager = new LevelManager(textureManager);
    }

    public void start() {
        this.gameLoop();
    }

    private void gameLoop() {
		BackgroundFactory.createBackground(world.getEntityManager(), textureManager);

        

        // only loop if there are levels remaining
        while (levelManager.hasLevels()) {
            levelManager.loadNextLevel();
            boolean levelComplete = false;
            // loop background music clip
            Clip musicClip = SoundPlayer.loopSound(String.format(
                "%s/sounds/background/background.wav",
                AssetConfig.getAssetsPath()
            ));
            while(!levelComplete)   //not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple 
            { 
                //swing has timer class to help us time this but I'm writing my own, you can of course use the timer, but I want to set FPS and display it 
                
                int TimeBetweenFrames =  1000 / targetFPS;
                long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames; 

                
                
                // wait till next time step 
                while (FrameCheck > System.currentTimeMillis()){} 
                    
                    
                levelComplete = this.tick();
            }
            // stop looping music after level is done
            SoundPlayer.stopSound(musicClip);
        }
    }

    
	// for each level check given game logic if completed
	private boolean tick() { 
		// model update   
		boolean isLevelComplete = world.update();
		// view update 
		canvas.updateView();
		// return boolean for if level completed
		return isLevelComplete; 
	}


}
