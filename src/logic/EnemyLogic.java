package logic;

import controllers.Controller;
import display.GameDisplay;
import enums.Direction;
import user.Config;
import util.GameObject;
import util.Point3f;
import util.Vector3f;
import objects.EnemyObject;

public class EnemyLogic extends ObjectLogic {
    private GameObject enemyObject = super.getGameObject();

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
