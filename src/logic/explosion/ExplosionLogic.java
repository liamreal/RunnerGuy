package logic.explosion;

import logic.object.ObjectLogic;
import objects.GameObject;

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
