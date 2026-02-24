package com.liamreal.logic.explosion;

import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.objects.GameObject;

public class ExplosionLogic extends ObjectLogic {
    // if you want explosion to move with object, specify speed based on object
    public ExplosionLogic(GameObject explosionObject, int moveSpeed) {
        super(explosionObject, moveSpeed);
    }
    public ExplosionLogic(GameObject explosionObject) {
        // by default bullet has triple speed of enemy (and 1.5x player)
        this(explosionObject, 0);

    }
}
