package com.liamreal.logic.enemy;

import com.liamreal.enums.Direction;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.user.Config;
import com.liamreal.objects.EnemyObject;

public class EnemyLogic extends ObjectLogic {
    // specify enemy speed
    public EnemyLogic(EnemyObject enemyObject, int moveSpeed) {
        super(enemyObject, moveSpeed);
    }
    public EnemyLogic(EnemyObject enemyObject) {
        // by default enemy has config speed
        this(enemyObject, Config.moveSpeed);

    }

    // move enemy down
    public void move() {
        super.move(Direction.DOWN);
    }

}
