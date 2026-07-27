package com.liamreal.systems;

import java.util.Collection;
import com.liamreal.ecs.Entity;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;

public class MovementSystem {
    public void move(Collection<Entity> entities) {
        // render each entity
        for(Entity entity : entities) {
            // if has transform (position) and sprite renderer, render sprite (where the position is)
            if(entity.has(Transform.class) &&
               entity.has(Velocity.class)) {

                Transform transform =
                    entity.get(Transform.class);
                Velocity velocity =
                    entity.get(Velocity.class);

                // displace position by calculated vector displacement
                transform.addDisplacement(velocity.calculateDisplacement());

            }
        }
    }
}
