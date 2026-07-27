package com.liamreal.systems;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Graphics;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.liamreal.ecs.Entity;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.graphics.SpriteRenderer;
import com.liamreal.graphics.Animation;

public class RenderSystemTest {
    @Test
    public void testRenderEntityWithRequiredComponents() {
        RenderSystem renderSystem = new RenderSystem();

        Entity entity = new Entity(1);
        Transform transform = new Transform(10, 20);
        SpriteRenderer renderer = mock(SpriteRenderer.class);

        entity.add(transform);
        entity.add(renderer);

        Graphics graphics = mock(Graphics.class);
        Animation animation = mock(Animation.class);

        renderSystem.render(
            List.of(entity),
            graphics,
            animation
        );

        // entity has both Transform and SpriteRenderer so should be drawn
        verify(renderer)
            .draw(transform.getPosition(), graphics, animation);
    }

    @Test
    public void testRenderEntityWithoutSpriteRenderer() {
        RenderSystem renderSystem = new RenderSystem();

        Entity entity = new Entity(1);
        entity.add(new Transform(10, 20));

        Graphics graphics = mock(Graphics.class);
        Animation animation = mock(Animation.class);

        renderSystem.render(
            List.of(entity),
            graphics,
            animation
        );

        // SpriteRenderer component does not exist, so cannot verify draw was not called directly, 
        // instead ensure no error occurred, meaning entity was skipped in render loop
        assertDoesNotThrow(() -> {
            renderSystem.render(
                List.of(entity),
                graphics,
                animation
            );
        });
    }


    @Test
    public void testRenderEntityWithoutTransform() {
        RenderSystem renderSystem = new RenderSystem();

        SpriteRenderer renderer = mock(SpriteRenderer.class);

        Entity entity = new Entity(1);
        entity.add(renderer);

        Graphics graphics = mock(Graphics.class);
        Animation animation = mock(Animation.class);

        renderSystem.render(
            List.of(entity),
            graphics,
            animation
        );

        // since no Transform, despite having SpriteRenderer, draw should not be called (because no position to draw at)
        verify(renderer, never())
            .draw(any(), any(), any());
    }

    @Test
    public void testRenderMultipleEntities() {
        RenderSystem renderSystem = new RenderSystem();

        Entity entity1 = new Entity(1);
        Entity entity2 = new Entity(2);

        Transform transform1 = new Transform(0, 0);
        Transform transform2 = new Transform(50, 50);

        SpriteRenderer renderer1 = mock(SpriteRenderer.class);
        SpriteRenderer renderer2 = mock(SpriteRenderer.class);

        entity1.add(transform1);
        entity1.add(renderer1);

        entity2.add(transform2);
        entity2.add(renderer2);

        Graphics graphics = mock(Graphics.class);
        Animation animation = mock(Animation.class);

        renderSystem.render(
            List.of(entity1, entity2),
            graphics,
            animation
        );

        // first SpriteRenderer is of Entity with id 1, with first Transform component, verify that was drawn
        verify(renderer1)
            .draw(transform1.getPosition(), graphics, animation);
        // then do same for second entity
        verify(renderer2)
            .draw(transform2.getPosition(), graphics, animation);
    }
}