package com.liamreal.ecs;

import java.util.function.BiConsumer;
import com.liamreal.physics.Collision;

public final class SystemUtils {

    // private constructor since this is final class
    private SystemUtils(){}

    // execute a method on pair of entities
    // from:
    //      https://medium.com/@pratik.941/mastering-functional-interfaces-in-java-8-a-comprehensive-guide-6a5aeeb565cb
    public static void execute(BiConsumer<Entity, Entity> method, Entity thisEntity, Entity otherEntity) 
    {
        // perform same method for both entity orders
        method.accept(thisEntity, otherEntity);
        method.accept(otherEntity, thisEntity);
    }
    // using Collision object instead, gets the 2 entities from collision
    public static void execute(BiConsumer<Entity, Entity> method, Collision collision) 
    {
        SystemUtils.execute(method, collision.getThisEntity(), collision.getOtherEntity());
    }
}