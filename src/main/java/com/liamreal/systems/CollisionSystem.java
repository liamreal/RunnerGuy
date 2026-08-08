package com.liamreal.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.liamreal.components.physics.Collider;
import com.liamreal.components.physics.Transform;
import com.liamreal.components.physics.Velocity;
import com.liamreal.ecs.Entity;
import com.liamreal.physics.Collision;
import com.liamreal.util.Vector2f;

public class CollisionSystem {
    // public void collide(Collection<Entity> entities) {
    //     // turn collection into list and filter out entities that do not have required components
    //     List<Entity> validEntities = new ArrayList<>(entities).stream().filter(entity -> this.hasRequiredComponents(entity)).collect(Collectors.toList());
    //     // render each entity
    //     for (int i = 0; i < validEntities.size() - 1; i++) {
    //         Entity thisEntity = validEntities.get(i);
    //         // then look at others can collide with
    //         for (int j = i + 1; j < validEntities.size(); j++) {
    //             // jth entity which ith collides with
    //             Entity otherEntity = validEntities.get(j);


    //             this.collide(thisEntity, otherEntity);

    //         }
    //     }
    // }

    // collision between two entities
    public void collide(Entity thisEntity, Entity otherEntity) {

        Collision collision = new Collision(thisEntity, otherEntity);


        collision.check();
    }



}
