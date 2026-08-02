package com.liamreal.systems;

import java.util.Collection;
import java.util.Set;

import com.liamreal.ecs.Entity;
import com.liamreal.util.Vector2f;
import com.liamreal.components.user.Controllable;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;

public class InputSystem {
    public void update(Collection<Entity> entities) {
        // render each entity
        for(Entity entity : entities) {
            // if has controllable (can be controlled by player)
            if(entity.has(Controllable.class)) {
                // get controllable
                Controllable controllable =
                    entity.get(Controllable.class);
                // update various components based on input
                this.updateMovement(entity, controllable);
            }
        }
    }
    // used to update movement based on controls
    private void updateMovement(Entity entity, Controllable controllable) {
        // for movement check Transform and Velocity (to update Velocity you require Transform)
        if (entity.has(Velocity.class) && entity.has(Transform.class)) {
            Velocity velocity =
                entity.get(Velocity.class);
            // initial direction is none
            Vector2f newDirection = new Vector2f(0, 0);
            // get active controls
            Set<String> activeControls = controllable.getActiveControls();

            // check each direction and apply respective vector
            if (activeControls.contains("up")) { newDirection.PlusVector(new Vector2f(0, -1)); }
            if (activeControls.contains("down")) { newDirection.PlusVector(new Vector2f(0, 1)); }
            if (activeControls.contains("left")) { newDirection.PlusVector(new Vector2f(-1, 0)); }
            if (activeControls.contains("right")) { newDirection.PlusVector(new Vector2f(1, 0)); }

            // apply new direction to Entity
            velocity.setDirection(newDirection);
        }
    }
}
