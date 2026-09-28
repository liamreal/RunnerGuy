package com.liamreal.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.liamreal.ecs.Entity;
import com.liamreal.util.Vector2f;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;

public class MovementSystem {
    public void move(Collection<Entity> entities, CollisionSystem collisionSystem) {
		// only iterate through applicable entities
		List<Entity> moveableEntities = MovementSystem.filterMovableEntities(entities);

		// select each entity and check collisions if it has a collider
        for (int entityIndex = 0; entityIndex < moveableEntities.size(); entityIndex++) {
			Entity thisEntity = moveableEntities.get(entityIndex);
			Transform thisTransform = thisEntity.get(Transform.class);

            // if want collisions
            if (collisionSystem != null) { collisionSystem.checkCollisions(moveableEntities, entityIndex); }

			Vector2f displacement = this.displace(thisEntity);
			thisTransform.addDisplacement(displacement);
        }
    }

    // if you want to ignore collisions, system will be nullified
    public void move(Collection<Entity> entities) { this.move(entities, null); }

    private Vector2f displace(Entity entity) {
        Velocity velocity =
            entity.get(Velocity.class);

        // displace position by calculated vector displacement
        return velocity.calculateDisplacement();
    }

    
	public static boolean hasRequiredComponents(Entity entity) {
		return entity.has(Transform.class) && entity.has(Velocity.class);
	}

    public static List<Entity> filterMovableEntities(Collection<Entity> entities) {
        return new ArrayList<>(entities).stream().filter(entity -> MovementSystem.hasRequiredComponents(entity)).collect(Collectors.toList());
    }
}
