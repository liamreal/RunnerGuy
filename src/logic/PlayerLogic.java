package logic;

import user.Config;

import objects.PlayerObject;

public class PlayerLogic extends ObjectLogic {
    // FIX THIS, NOT IMPORTING THE ONE INSTANCE FOR SOME REASON
    private Controller playerController = Controller.getInstance();

    public PlayerLogic(PlayerObject playerObject) {
        // by default if no speed specified, player moves twice as fast as enemies
        super(playerObject, Config.moveSpeed*2);

    }
    public PlayerLogic(PlayerObject playerObject, int moveSpeed) {
        super(playerObject, moveSpeed);
    }

    // player checks for movement using WASD
    @Override
    public move() {

    }

    public static void playerLogic() {
        // do something
    }
}
