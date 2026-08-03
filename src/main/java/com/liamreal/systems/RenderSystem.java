package com.liamreal.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

import java.awt.Graphics;
import com.liamreal.ecs.Entity;
import com.liamreal.graphics.Animation;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.graphics.SpriteRenderer;

public class RenderSystem {
    private List<Entity> filterEntities(Collection<Entity> entities) {
        // filter only entities that have transform
        List<Entity> entityList = new ArrayList<>();
        for (Entity e : entities) {
            if (e.has(Transform.class)) {
                entityList.add(e);
            }
        }
        // now sort, knowing all have transform
        Collections.sort(entityList, (e1, e2) -> {
            return this.compareY(e1, e2);
        });
        return entityList;
    }
    // used for sorting for drawing, kind of messy but comparison of only Y is only used by RenderSystem for drawing
    private int compareY(Entity e1, Entity e2) {
        // if missing Transform, something went wrong, throw error
        if (!e1.has(Transform.class) || !e2.has(Transform.class)) { 
			throw new RuntimeException(
				"One or both entities are missing Transform properties for RenderSystem despite being filtered!"
			);
        }
        // knowing have Transform component, compare both
        Transform t1 = e1.get(Transform.class);
        Transform t2 = e2.get(Transform.class);
        return t1.compareY(t2);
        
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