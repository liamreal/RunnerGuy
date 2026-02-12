package logic.bullet;

import enums.Direction;
import logic.object.ObjectLogic;
import user.Config;
import objects.BulletObject;

public class BulletLogic extends ObjectLogic {
    // specify bullet speed
    public BulletLogic(BulletObject bulletObject, int moveSpeed) {
        super(bulletObject, moveSpeed);
    }
    public BulletLogic(BulletObject bulletObject) {
        // by default bullet has triple speed of enemy (and 1.5x player)
        this(bulletObject, Config.moveSpeed*3);

    }
}
