package com.liamreal.systems;

import java.util.Collection;
import java.util.List;
import java.awt.Graphics;
import com.liamreal.ecs.Entity;
import com.liamreal.graphics.Animation;
import com.liamreal.components.physics.Transform;
import com.liamreal.display.GameDisplay;
import com.liamreal.components.graphics.SpriteRenderer;

public class RenderSystem {
    // can also pass in one entity (e.g. rendering background)
    public void render(Entity entity, Graphics graphics, Animation animation) {
        this.render(List.of(entity), graphics, animation);
    }
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

                    renderer.drawAnimatedImage(transform.getPosition(), graphics, animation);
            }
        }
    }
    public void render(Collection<Entity> entities, Graphics graphics) {
        // render each entity
        for(Entity entity : entities) {
            // if has transform (position) and sprite renderer, render sprite (where the position is)
            if(entity.has(Transform.class) &&
               entity.has(SpriteRenderer.class)) {

                Transform transform =
                    entity.get(Transform.class);
                SpriteRenderer renderer =
                    entity.get(SpriteRenderer.class);

                    renderer.drawStaticImage(transform.getPosition(), graphics);
                    
            }
        }
    }
}