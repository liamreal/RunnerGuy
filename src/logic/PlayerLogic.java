package logic;

import controllers.Controller;
import enums.Direction;
import user.Config;
import util.Point3f;
import util.Vector3f;
import objects.PlayerObject;

public class PlayerLogic extends ObjectLogic {
    // FIX THIS, NOT IMPORTING THE ONE INSTANCE FOR SOME REASON
    private Controller playerController = Controller.getInstance();

    // specify player speed
    public PlayerLogic(PlayerObject playerObject, int moveSpeed) {
        super(playerObject, moveSpeed);
    }
    public PlayerLogic(PlayerObject playerObject) {
        // by default if no speed specified, player moves twice as fast as enemies, calls constructor above which calls super class
        this(playerObject, Config.moveSpeed*2);

    }

    // // player checks for movement using WASD
    // // @Override
    // public Direction move(Direction direction) {
    //     // get objects re-referenced below
    //     Point3f centre = this.getGameObject().getCentre();
    //     int moveSpeed = this.getMoveSpeed();
    //     switch(direction) {
    //         // put down as first direction as most of time will be this
    //         case DOWN:
    //             centre.ApplyVector(new Vector3f(0,this.moveSpeed,0));
    //             return Direction.DOWN;
    //         case UP:
    //             centre.ApplyVector(new Vector3f(0,-this.moveSpeed,0));
    //             return Direction.UP;
    //         case LEFT:
    //             centre.ApplyVector(new Vector3f(-this.moveSpeed,0,0));
    //             return Direction.LEFT;
    //         case RIGHT:
    //             centre.ApplyVector(new Vector3f(this.moveSpeed,0,0));
    //             return Direction.RIGHT;
    //         // if not valid throw exception
    //         default:
    //             // not a valid direction in cases
    //             String errorMessage = String.format("Direction %s not in %s", direction, Direction.getAllDirections().toString());
    //             throw new IllegalArgumentException(errorMessage);
    //     }
    // }

    public static void playerLogic() {
        // do something
    }
}
