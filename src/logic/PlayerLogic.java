package logic;

import user.Config;

import objects.PlayerObject;

public class PlayerLogic extends ObjectLogic {
    // FIX THIS, NOT IMPORTING THE ONE INSTANCE FOR SOME REASON
    // private Controller playerController = Controller.getInstance();

    // specify player speed
    public PlayerLogic(PlayerObject playerObject, int moveSpeed) {
        super(playerObject, moveSpeed);
    }
    public PlayerLogic(PlayerObject playerObject) {
        // by default if no speed specified, player moves twice as fast as enemies, calls constructor above which calls super class
        this(playerObject, Config.moveSpeed*2);

    }

    // player checks for movement using WASD
    public void move() {
        
    }

    public static void playerLogic() {
        // do something
    }
}
