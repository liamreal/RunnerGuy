package com.liamreal.factory;

import com.liamreal.user.Config;
import com.liamreal.assets.TextureManager;
import com.liamreal.components.graphics.SpriteRenderer;
import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.display.GameDisplay;
import com.liamreal.ecs.Entity;
import com.liamreal.ecs.EntityManager;
import com.liamreal.graphics.SpriteSheet;
import com.liamreal.physics.shapes.Circle;

public class EnemyFactory {
    private static Config config = Config.getInstance();
    public static Entity createBasicEnemy(EntityManager entityManager, TextureManager textureManager) {
        return EnemyFactory.createEnemy(entityManager, textureManager, "basic");
    }
    public static Entity createAdvancedEnemy(EntityManager entityManager, TextureManager textureManager) {
        return EnemyFactory.createEnemy(entityManager, textureManager, "advanced");
    }
    private static Entity createEnemy(EntityManager entityManager, TextureManager textureManager, String enemyType) {    
        Entity enemy = entityManager.createEntity();
        enemy.add(new Transform(GameDisplay.getDisplayX()/2.0, 0));
        enemy.add(new Velocity(config.getGeneralMoveSpeed()));
        enemy.add(new SpriteRenderer(new SpriteSheet(
                textureManager.getAsset(String.format("%s.png", enemyType)),
                GameDisplay.getSpriteWidth(), 
                GameDisplay.getSpriteHeight()
            ),
            true
        ));
        enemy.add(new Collider(new Circle(enemy.get(Transform.class).getPosition(), EnemyFactory.calculateEnemyColliderLength())));
        return enemy;
    }

    private static int calculateEnemyColliderLength() {
        // make collider 90% of half of sprite width, if less than 1 it will make collider at least 1 (making it positive)
        return Math.max((int) (GameDisplay.getSpriteWidth()/2.0 * 0.9), 1);
    }
}