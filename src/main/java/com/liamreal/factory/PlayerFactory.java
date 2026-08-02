package com.liamreal.factory;

import com.liamreal.user.Config;
import com.liamreal.Main;
import com.liamreal.assets.TextureManager;
import com.liamreal.components.graphics.SpriteRenderer;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.components.user.Controllable;
import com.liamreal.display.GameDisplay;
import com.liamreal.ecs.Entity;
import com.liamreal.ecs.EntityManager;
import com.liamreal.graphics.SpriteSheet;

public class PlayerFactory {
    private static Config config = Config.getInstance();
    public static void createPlayerOne(EntityManager entityManager, TextureManager textureManager) {    
        Entity player = entityManager.createEntity();
        player.add(new Transform(GameDisplay.getDisplayX()/2.0, GameDisplay.getDisplayY()/2.0));
        player.add(new Velocity(config.getPlayerMoveSpeed()));
        player.add(new SpriteRenderer(new SpriteSheet(
                textureManager.getAsset("player_one.png"),
                32, 
                32
            ),
            true
        ));
        // add controls based on pre-determined player one controls
        player.add(new Controllable(Main.playerOneController));
    }
}
