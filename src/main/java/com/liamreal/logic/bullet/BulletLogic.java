package com.liamreal.logic.bullet;

import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.user.Config;
import com.liamreal.objects.BulletObject;

public class BulletLogic extends ObjectLogic {
    // specify bullet speed
    public BulletLogic(BulletObject bulletObject, int moveSpeed) {
        super(bulletObject, moveSpeed);
    }
    public BulletLogic(BulletObject bulletObject) {
        // by default bullet has triple speed of enemy (and 1.5x player)
        this(bulletObject, Config.getInstance().getBulletMoveSpeed());

    }
}
