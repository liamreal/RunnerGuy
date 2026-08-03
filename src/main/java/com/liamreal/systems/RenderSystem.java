package com.liamreal.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.awt.Graphics;
import com.liamreal.ecs.Entity;
import com.liamreal.graphics.Animation;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.graphics.SpriteRenderer;

public class RenderSystem {
    private List<Entity> filterEntities(Collection<Entity> entities) {
        List<Entity> entityList = new ArrayList<>(entities);
        Collections.sort(entityList, (e1, e2) -> {
            return this.comparePosition(e1, e2);
        });
        return entityList;
    }
    // used for sorting for drawing, kind of messy but comparison of only Y is only used by RenderSystem for drawing
    private Integer comparePosition(Entity e1, Entity e2) {
        // if first entity has transform but second does not, first comes first
        if (e1.has(Transform.class) && !e2.has(Transform.class)) { return -1; }
        // if second entity has transform but first does not, second comes first
        else if (!e1.has(Transform.class) && e2.has(Transform.class)) { return 1; }
        // if both have transform, compare y-position
        else if (e1.has(Transform.class) && e2.has(Transform.class)) { 
            Double y1 = e1.get(Transform.class).getPosition().getY();
            Double y2 = e2.get(Transform.class).getPosition().getY();
            return Double.compare(y1, y2);
        } 
        // otherwise order irrelevant, do not change
        else {
            return 0;
        }
    }
    public void render(Collection<Entity> entities, Graphics graphics, Animation animation) {
        // sort entities based on y-position (higher Y means rendered later)
        List<Entity> sortedEntities = this.filterEntities(entities);
        // render each entity
        for(Entity entity : sortedEntities) {
            // transform already checked, so only check if has sprite renderer, render sprite (where the position is)
            if(entity.has(SpriteRenderer.class)) {

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