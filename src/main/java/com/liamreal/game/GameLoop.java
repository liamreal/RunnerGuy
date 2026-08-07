package com.liamreal.game;

import javax.sound.sampled.Clip;
import com.liamreal.assets.AssetConfig;
import com.liamreal.assets.SoundPlayer;
import com.liamreal.assets.TextureManager;
import com.liamreal.components.physics.Transform;
import com.liamreal.display.GameDisplay;
import com.liamreal.ecs.Entity;
import com.liamreal.factory.BackgroundFactory;
import com.liamreal.factory.EnemyFactory;
import com.liamreal.factory.PlayerFactory;
import com.liamreal.view.Viewer;
import com.liamreal.user.Config;
import com.liamreal.util.Vector2f;

public class GameLoop implements Runnable {
    private final Model world;
    private final Viewer canvas;
    private final TextureManager textureManager = new TextureManager();
    private final LevelLoader levelLoader = new LevelLoader(textureManager);

    public GameLoop(Model world, Viewer canvas) {
        this.world = world;
        this.canvas = canvas;
    }

    @Override
    public void run() {
        this.gameLoop();
    }

    // create basic things needed at start of game (background, players for example, enemies and the rest are created in Model itself)
    void initialiseGame() {
        // background is also an entity
		BackgroundFactory.createBackground(world.getBackgroundManager(), textureManager);
        this.createPlayers();
		Entity enemyOne = EnemyFactory.createBasicEnemy(world.getEntityManager(), textureManager);
        enemyOne.get(Transform.class).setPosition(new Vector2f(GameDisplay.getDisplayX()/2, 0));
		Entity enemyTwo = EnemyFactory.createBasicEnemy(world.getEntityManager(), textureManager);
        enemyTwo.get(Transform.class).setPosition(new Vector2f(GameDisplay.getDisplayX()/2, GameDisplay.getDisplayY()-10));
    }

    // in future allow through a list of created controllers and dynamically go 1-4 players using player_%d.png string format for asset
    // for now this works as a proof of concept and small number of players using dedicated player one and two creation methods
    void createPlayers() {
        // create based on number in config
        int numPlayers = Config.getInstance().getNumPlayers();
        // cases for number of players
        switch (numPlayers) {
            case 1:
                PlayerFactory.createPlayerOne(world.getEntityManager(), textureManager);
                break;
            case 2:
                PlayerFactory.createPlayerOne(world.getEntityManager(), textureManager);
                PlayerFactory.createPlayerTwo(world.getEntityManager(), textureManager);
                break;
            default:
                throw new RuntimeException(String.format("Invalid number of players: %s", numPlayers));
        }
    }
    
    private void gameLoop() {
        this.initialiseGame();
        long lastTime = System.nanoTime(); // last time to do consistent move between game ticks instead of basing it off fps
        double delta = 0;
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
                // time calculation for game tick speed
                long now = System.nanoTime();
                delta += (now - lastTime) / GameDisplay.getTimePerTick();
                lastTime = now;

                while (delta >= 1) {
                    // tick to check if level completed
                    levelComplete = this.tick();
                    delta--;
                }

                // prevent 100% CPU usage by putting this thread to sleep
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
