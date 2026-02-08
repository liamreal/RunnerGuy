package logic;

import java.time.Duration;
import java.time.Instant;

import controllers.Controller;
import display.GameDisplay;
import enums.Direction;
import user.Config;
import util.CooldownHandler;
import util.GameObject;
import util.Point3f;
import objects.EnemyObject;
import objects.PlayerObject;

public class PlayerLogic extends ObjectLogic {
    private Controller playerController = Controller.getInstance();
    private GameObject playerObject = super.getGameObject();
    private Instant enemyHitCooldownStartTime = Instant.now(); // for checking when player hit last enemy
    private double enemyHitCooldownLength = 1.0; // player will be invincible during this time
    private Instant bulletFireCooldownStartTime = Instant.now(); // for checking when player last fired bullet
    private double bulletFireCooldownLength = 0.5; // player will be unable to fire bullets during this time
    

    // specify player speed
    public PlayerLogic(PlayerObject playerObject, int moveSpeed) {
        super(playerObject, moveSpeed);
        this.setHealth(Config.playerHealth);
    }
    public PlayerLogic(PlayerObject playerObject) {
        // by default if no speed specified, player moves twice as fast as enemies, calls constructor above which calls super class
        this(playerObject, Config.moveSpeed*2);

    }

    public int getHealth() { return playerObject.getHealth(); }
    private Instant getEnemyHitCooldownStartTime() { return this.enemyHitCooldownStartTime; }
    private Instant getBulletFireCooldownStartTime() { return this.bulletFireCooldownStartTime; }
    public void setHealth(int newHealth) { playerObject.setHealth(newHealth); }
    private void setEnemyHitCooldownStartTime(Instant newStartTime) { this.enemyHitCooldownStartTime = newStartTime; }
    private void setBulletFireCooldownStartTime(Instant newStartTime) { this.bulletFireCooldownStartTime = newStartTime; }

    // // find time (in seconds) since last cooldown application
    // protected double findTimeSinceLastCooldown(Instant cooldownStartTime) {     
    //     // get time since last enemy and calculate how much time passed
    //     Instant lastCooldownStart = cooldownStartTime;
    //     Duration durationSinceLastCooldown = Duration.between(lastCooldownStart, Instant.now());
    //     double secondsSinceLastCooldown = durationSinceLastCooldown.getSeconds() + durationSinceLastCooldown.getNano() / 1000000000.0;
    //     return secondsSinceLastCooldown;
    // }
    // protected boolean isOnCooldown(Instant cooldownStartTime, double cooldownLength) {
    //     return this.findTimeSinceLastCooldown(cooldownStartTime) <= cooldownLength;
    // }

    // reset start time of last time enemy hit player
    public void resetEnemyHitCooldown() {
        this.setEnemyHitCooldownStartTime(Instant.now());
    }
    // reset start time of last time player fired bullet
    public void resetBulletFireCooldown() {
        this.setBulletFireCooldownStartTime(Instant.now());
    }

    // various enemy collision checks (MODIFY TO DO STUFF TO HEALTH/COOLDOWN AND OTHER)
    public GameObject collideEnemy(EnemyLogicManager enemyLogicManager) {
        GameObject collidedEnemyObject = super.collide(enemyLogicManager);
        if (collidedEnemyObject != null) {
            // check last time player hit enemy
            Instant lastEnemyHitTime = this.getEnemyHitCooldownStartTime();
            if (!CooldownHandler.isOnCooldown(lastEnemyHitTime, this.enemyHitCooldownLength)) {
                this.resetEnemyHitCooldown();
                // decrease player health by object health
                playerObject.decreaseHealth(collidedEnemyObject.getHealth());
            }
        }
        // return collided object (or null if collide method returns nothing)
        return collidedEnemyObject;
    }

    public GameObject spawnBullet(BulletLogicManager bulletLogicManager) {
        // check player pressing space
        GameObject playerObject = this.getGameObject();
        boolean playerSpace = playerController.isKeySpacePressed();
        if (playerSpace) {
            // check last time player fired bullet
            Instant lastBulletFireTime = this.getBulletFireCooldownStartTime();
            if (!CooldownHandler.isOnCooldown(lastBulletFireTime, this.bulletFireCooldownLength)) {
                // spawn bullet based on player who pressing space
                GameObject bulletSpawned = bulletLogicManager.spawnBullet(playerObject);
                this.resetBulletFireCooldown();
                // bullet fired success result
                return bulletSpawned;
            }
        }
        // no bullet spawned
        return null;
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
