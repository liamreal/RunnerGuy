package com.liamreal.factory;

import com.liamreal.assets.TextureManager;
import com.liamreal.components.graphics.SpriteRenderer;
import com.liamreal.components.physics.Transform;
import com.liamreal.display.GameDisplay;
import com.liamreal.ecs.Entity;
import com.liamreal.ecs.EntityManager;
import com.liamreal.graphics.SpriteSheet;

public class BackgroundFactory {
    public static void createBackground(EntityManager entityManager, TextureManager textureManager) {    
        Entity background = entityManager.createEntity();
        background.add(new Transform(0, 0));
        background.add(new SpriteRenderer(new SpriteSheet(
            textureManager.getAsset("background.png"),
             GameDisplay.getDisplayX(), 
             GameDisplay.getDisplayY()
        )));

        System.out.println(textureManager.getAsset("background.png").getWidth());
        System.out.println(textureManager.getAsset("background.png").getHeight());
        System.out.println(GameDisplay.getDisplayX());
        System.out.println(GameDisplay.getDisplayY());
    }
}
