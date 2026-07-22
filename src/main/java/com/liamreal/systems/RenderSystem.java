package com.liamreal.systems;

import java.util.Collection;
import java.awt.Graphics;
import com.liamreal.ecs.Entity;
import com.liamreal.graphics.Animation;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.graphics.SpriteRenderer;

public class RenderSystem {
    public void render(Collection<Entity> entities, Graphics graphics, Animation animation) {
        // render each entity
        for(Entity entity : entities) {
            // if has transform (position) and sprite renderer, render sprite (where the position is)
            if(entity.has(Transform.class) &&
               entity.has(SpriteRenderer.class)) {

                Transform transform =
                    entity.get(Transform.class);
                SpriteRenderer renderer =
                    entity.get(SpriteRenderer.class);

                // render image
                renderer.draw(transform.getPosition(), graphics, animation);
            }
        }
    }
}