package com.liamreal.factory;

import static org.junit.jupiter.api.Assertions.*;
import com.liamreal.assets.TextureManager;
import com.liamreal.ecs.Entity;
import com.liamreal.ecs.EntityManager;
import com.liamreal.user.Config;
import com.liamreal.components.graphics.SpriteRenderer;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.components.user.Controllable;
import com.liamreal.display.GameDisplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerFactoryTest {
    private Config config;
    private EntityManager entityManager;
    private TextureManager textureManager;

    @BeforeEach
    void setUp() {
        entityManager = new EntityManager();
        textureManager = new TextureManager();
        config = Config.getInstance();
    }

    // only need to test player one as player two function delegates to same internal method using method overloading
    @Test
    public void testCreatesPlayerComponentsAllCorrect() {
        Entity player = PlayerFactory.createPlayerOne(entityManager, textureManager);

        // verify player added to entity manager
        assertEquals(1, entityManager.getEntityCount());
        assertTrue(entityManager.getEntityMap().values().contains(player));

        Transform transform = player.get(Transform.class);
        
        // verify player spawns in center of screen
        assertEquals(GameDisplay.getDisplayX()/2.0, transform.getPosition().getX());
        assertEquals(GameDisplay.getDisplayY()/2.0, transform.getPosition().getY());

        Velocity velocity = player.get(Velocity.class);
        
        // verify same speed as config specification
        assertEquals(config.getPlayerMoveSpeed(), velocity.getSpeed());

        // for sprite rendering we do not have access to any more data, this functionality would be covered by SpriteRenderer tests
        SpriteRenderer spriteRenderer = player.get(SpriteRenderer.class);
        assertNotNull(spriteRenderer);

        // similar for Controllable, althought ControllerFactory will do some more testing in this particular aspect
        Controllable controllable = player.get(Controllable.class);
        assertNotNull(controllable);
    }
}