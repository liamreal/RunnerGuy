package com.liamreal.systems;

import java.util.Collection;
import com.liamreal.ecs.Entity;
import com.liamreal.util.Vector2f;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import java.lang.Math;

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

                Vector2f vel = velocity.calculateDisplacement();
                double speed = Math.sqrt(vel.getX()*vel.getX() + vel.getY()*vel.getY());
                System.out.println(String.format("pos: %s, vel: %.2f", transform.getPosition().toString(), speed));

            }
        }
    }
}
