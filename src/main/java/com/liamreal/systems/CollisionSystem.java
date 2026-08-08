package com.liamreal.systems;

import java.util.List;
import com.liamreal.components.physics.Collider;
import com.liamreal.ecs.Entity;
import com.liamreal.physics.Collision;

public class CollisionSystem {
    // collision between two entities
    public void collide(Entity thisEntity, Entity otherEntity) {
        Collision collision = new Collision(thisEntity, otherEntity);
        collision.check();
    }

    // check if any collisions with all other objects, if so, check collisions between them
	public void checkCollisions(List<Entity> entities, int entityIndex) {
		Entity thisEntity = entities.get(entityIndex);
		// if no collider, exit
		if (!thisEntity.has(Collider.class)) { return; }
		// then look at others can collide with
		for (int j = entityIndex + 1; j < entities.size(); j++) {
			Entity otherEntity = entities.get(j);
			// if same entity skip
			if (thisEntity.equals(otherEntity)) { continue; }
			// skip if no collider
			if (!otherEntity.has(Collider.class)) { continue; }

			// adjust both entity directions
			this.collide(thisEntity, otherEntity);
		}
	}



}
