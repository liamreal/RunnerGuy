package logic;

import controllers.Controller;
import display.GameDisplay;
import enums.Direction;
import user.Config;
import util.GameObject;
import util.Point3f;
import objects.PlayerObject;

public class PlayerLogic extends ObjectLogic {
    private Controller playerController = Controller.getInstance();
    private GameObject playerObject = super.getGameObject();

    // specify player speed
    public PlayerLogic(PlayerObject playerObject, int moveSpeed) {
        super(playerObject, moveSpeed);
    }
    public PlayerLogic(PlayerObject playerObject) {
        // by default if no speed specified, player moves twice as fast as enemies, calls constructor above which calls super class
        this(playerObject, Config.moveSpeed*2);

    }

    // player checks for movement using position, size and keystrokes
    public void move() {
        // player position
        Point3f playerCentre = playerObject.getCentre();
        float playerX = playerCentre.getX();
        float playerY = playerCentre.getY();
        // player dimensions
        int playerWidth = playerObject.getWidth();
        int playerHeight = playerObject.getHeight();
        // possible player movement directions
        boolean playerDown = playerController.isKeySPressed();
        boolean playerUp = playerController.isKeyWPressed();
        boolean playerLeft = playerController.isKeyAPressed();
        boolean playerRight = playerController.isKeyDPressed();
		  
		if(playerDown){
			// prevent further movement of player down (outside screen)
			if (playerY + playerHeight > GameDisplay.getDisplayY()) {
				System.out.println("OUT OF BOUNDS Y down - not moving further down");
			}
			// otherwise go down
			else {
				super.move(Direction.DOWN);
			}
		}
		if(playerUp)
		{
			// prevent further movement of player up (outside screen)
			if (playerY < 0) {
				System.out.println("OUT OF BOUNDS Y up - not moving further up");
			}
			// otherwise go up
			else {
				super.move(Direction.UP);
			}
		}
		if(playerLeft)
			{	
				// prevent further movement of player left (outside screen)
				if (playerX < 0) {
					System.out.println("OUT OF BOUNDS X left - not moving further left");
				}
				// otherwise go left
				else {
					super.move(Direction.LEFT);
				}
			}
		if(playerRight)
		{

			// prevent further movement of player right (outside screen)
			if (playerX + playerWidth > GameDisplay.getDisplayX()) {
				System.out.println("OUT OF BOUNDS Y right - not moving further right");
			}
			// otherwise go right
			else {
					super.move(Direction.RIGHT);
			}
		}
    }
}
