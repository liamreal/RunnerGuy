package com.liamreal.factory;

import com.liamreal.user.Config;
import com.liamreal.Main;
import com.liamreal.controllers.Controller;
import com.liamreal.assets.TextureManager;
import com.liamreal.components.graphics.SpriteRenderer;
import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.components.user.Controllable;
import com.liamreal.display.GameDisplay;
import com.liamreal.ecs.Entity;
import com.liamreal.ecs.EntityManager;
import com.liamreal.graphics.SpriteSheet;
import com.liamreal.physics.shapes.Circle;

public class PlayerFactory {
    private static Config config = Config.getInstance();
    public static Entity createPlayerOne(EntityManager entityManager, TextureManager textureManager) {
        return PlayerFactory.createPlayer(entityManager, textureManager, "one", Main.playerOneController);
    }
    public static Entity createPlayerTwo(EntityManager entityManager, TextureManager textureManager) {
        return PlayerFactory.createPlayer(entityManager, textureManager, "two", Main.playerTwoController);
    }
    private static Entity createPlayer(EntityManager entityManager, TextureManager textureManager, String playerType, Controller controller) {    
        Entity player = entityManager.createEntity();
        player.add(new Transform(GameDisplay.getDisplayCentre()));
        player.add(new Velocity(config.getPlayerMoveSpeed()));
        player.add(new SpriteRenderer(new SpriteSheet(
                textureManager.getAsset(String.format("player_%s.png", playerType)),
                GameDisplay.getSpriteWidth(), 
                GameDisplay.getSpriteHeight()
            ),
            true
        ));
        // add controls based on pre-determined player one controls
        player.add(new Controllable(controller));
        player.add(new Collider(new Circle(player.get(Transform.class).getPosition(), PlayerFactory.calculatePlayerColliderLength())));
        return player;
    }

    private static int calculatePlayerColliderLength() {
        // make collider 90% of half of player sprite width, if less than 1 it will make collider at least 1 (making it positive)
        return Math.max((int) (GameDisplay.getSpriteWidth()/2.0 * 0.9), 1);
    }
}
