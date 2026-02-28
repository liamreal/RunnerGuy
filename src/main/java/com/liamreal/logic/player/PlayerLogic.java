package com.liamreal.logic.player;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;
import com.liamreal.controllers.Controller;
import com.liamreal.display.GameDisplay;
import com.liamreal.enums.Direction;
import com.liamreal.enums.Interaction;
import com.liamreal.enums.PlayerType;
import com.liamreal.enums.ItemType;
import com.liamreal.logic.bullet.BulletLogicManager;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.logic.object.ObjectLogicManager;
import com.liamreal.user.Config;
import com.liamreal.util.CooldownHandler;
import com.liamreal.util.Point3f;
import com.liamreal.objects.GameObject;
import java.util.HashMap;
import com.liamreal.objects.PlayerObject;
import com.liamreal.assets.AssetLoader;
import com.liamreal.assets.SoundPlayer;

public class PlayerLogic extends ObjectLogic {
    private Controller playerController = Controller.getInstance();
    private Config config = Config.getInstance();
    private GameObject playerObject = this.getGameObject();
    private Instant enemyHitCooldownStartTime = Instant.now(); // for checking when player hit last enemy
    private double enemyHitCooldownLength = 1.0; // player will be invincible during this time
    private Instant bulletFireCooldownStartTime = Instant.now(); // for checking when player last fired bullet
    private double bulletFireCooldownLength = 0.5; // player will be unable to fire bullets during this time
    private Instant itemUseCooldownStartTime = Instant.now(); // for checking when player use last item
    private double itemUseCooldownLength = 5.0; // player will have this item effect until over or picks up another item
    private PlayerType playerType;
    private ItemType itemType = null;
    // using boolean supplier allows you to get player input via a lambda (suggest by ChatGPT)
    private HashMap<Direction, BooleanSupplier> playerMoves;
    private HashMap<Interaction, BooleanSupplier> playerInteractions;

    

    // specify player speed and type (player one or two)
    public PlayerLogic(PlayerObject playerObject, int moveSpeed) {
        super(playerObject, moveSpeed);
        this.setHealth(config.getPlayerHealth());
        this.setPlayerType(playerObject.getPlayerType());
    }
    public PlayerLogic(PlayerObject playerObject) {
        // by default if no speed specified, player moves twice as fast as enemies, calls constructor above which calls super class
        this(playerObject, Config.getInstance().getPlayerMoveSpeed());
    }

    // set player type of current player object
    private final void setPlayerType(PlayerType playerType) {
        switch (playerType) {
            // cases for player
            case ONE:
                playerMoves = new HashMap<>();
                // storing BooleanSupplier as map values allows lambda functions to be called for player input (suggested by ChatGPT)
                playerMoves.put(Direction.DOWN, playerController::isKeySPressed);
                playerMoves.put(Direction.UP, playerController::isKeyWPressed);
                playerMoves.put(Direction.LEFT, playerController::isKeyAPressed);
                playerMoves.put(Direction.RIGHT, playerController::isKeyDPressed);
                playerInteractions = new HashMap<>();
                playerInteractions.put(Interaction.SHOOT, playerController::isKeySpacePressed);
                break;
            // case for second player
            case TWO:
                playerMoves = new HashMap<>();
                playerMoves.put(Direction.DOWN, playerController::isKeyKPressed);
                playerMoves.put(Direction.UP, playerController::isKeyIPressed);
                playerMoves.put(Direction.LEFT, playerController::isKeyJPressed);
                playerMoves.put(Direction.RIGHT, playerController::isKeyLPressed);
                playerInteractions = new HashMap<>();
                playerInteractions.put(Interaction.SHOOT, playerController::isKeyNPressed);
                break;
            default:
                // not a valid direction in cases
                String errorMessage = String.format("PlayerType %s not in %s", this.playerType, PlayerType.getAllPlayerTypes().toString());
                throw new IllegalArgumentException(errorMessage);
        }
    }

    public int getHealth() { return playerObject.getHealth(); }
    private Instant getEnemyHitCooldownStartTime() { return this.enemyHitCooldownStartTime; }
    private Instant getBulletFireCooldownStartTime() { return this.bulletFireCooldownStartTime; }
    private Instant getItemUseCooldownStartTime() { return this.itemUseCooldownStartTime; }
    private double getEnemyHitCooldownLength() { return this.enemyHitCooldownLength; }
    private double getBulletFireCooldownLength() { return this.bulletFireCooldownLength; }
    private double getItemUseCooldownLength() { return this.itemUseCooldownLength; }
    private ItemType getItemType() { return this.itemType; }
    public void setHealth(int newHealth) { playerObject.setHealth(newHealth); }
    private void setEnemyHitCooldownStartTime(Instant newStartTime) { this.enemyHitCooldownStartTime = newStartTime; }
    private void setBulletFireCooldownStartTime(Instant newStartTime) { this.bulletFireCooldownStartTime = newStartTime; }
    private void setItemUseCooldownStartTime(Instant newStartTime) { this.itemUseCooldownStartTime = newStartTime; }
    private void setItemType(ItemType newItemType) { this.itemType = newItemType; }

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
    // reset start time of last time player used item
    public void resetItemUseCooldown() {
        this.setItemUseCooldownStartTime(Instant.now());
    }


    // check item
    public void updateItem() {
        Instant lastItemUseTime = this.getItemUseCooldownStartTime();
        // // for debugging that is updating item for player specifically (and NOT superclass method)
        // System.out.println(lastItemUseTime);
        // System.out.println(this.getItemUseCooldownLength());
        if (!CooldownHandler.isOnCooldown(lastItemUseTime, this.getItemUseCooldownLength())) {
            this.setItemType(null); // remove item if past cooldown
        }
    }

    // restore health item
    private void useHealthItem() {
        this.setHealth(this.getHealth() + 1);
        this.setItemType(null); // reset item type to null
    }

    // for items which have one time use and are not on continuous cooldown (such as a health restore item)
    private void itemDirectUse() {
        switch (this.getItemType()) {
            // if health item, restore health 
            case HEALTH:
                this.useHealthItem();
                break;
            // otherwise not a direct use item (such as a bullet) or null
            default:
                break;
        }
    }

    // various item collision checks
    public GameObject collideItem(ObjectLogicManager itemLogicManager) {
        CopyOnWriteArrayList<GameObject> collidedItems = super.collide(itemLogicManager);
        // if multiple collisions, pick item object that was closest
        GameObject collidedItemObject = super.getClosestGameObject(collidedItems);
        if (collidedItemObject != null) {
            // SoundPlayer.playSound()

            // set new item type
            this.setItemType(collidedItemObject.getItemType()); // get item type of GameObject
            this.resetItemUseCooldown();
            // kill item by setting health to 0
            collidedItemObject.setHealth(0);
            this.itemDirectUse();
        }
        // return collided object (or null if collide method returns nothing)
        return collidedItemObject;
    }

    // various enemy collision checks
    public GameObject collideEnemy(ObjectLogicManager enemyLogicManager) {
        CopyOnWriteArrayList<GameObject> collidedEnemies = super.collide(enemyLogicManager);
        // if multiple collisions, pick enemy object that was closest
        GameObject collidedEnemyObject = super.getClosestGameObject(collidedEnemies);
        if (collidedEnemyObject != null) {
            // check last time player hit enemy
            Instant lastEnemyHitTime = this.getEnemyHitCooldownStartTime();
            if (!CooldownHandler.isOnCooldown(lastEnemyHitTime, this.getEnemyHitCooldownLength())) {
                SoundPlayer.playSound(String.format(
                    "%s/sounds/enemies/%s/hurt.wav",
                        AssetLoader.getAssetsPath(),
                        collidedEnemyObject.getEnemyType().toString()
                    )
                );

                this.resetEnemyHitCooldown();
                // decrease player health by object health
                playerObject.decreaseHealth(collidedEnemyObject.getHealth());
                // // test for collision, moves collided enemy to specific coords
                // collidedEnemyObject.setCentre(new Point3f(GameDisplay.getDisplayX()/2,GameDisplay.getDisplayY()/2,0));
            }
        }
        // return collided object (or null if collide method returns nothing)
        return collidedEnemyObject;
    }

    public GameObject spawnBullet(BulletLogicManager bulletLogicManager) {
        // check player pressing space
        GameObject playerObject = this.getGameObject();
        boolean playerSpace = playerInteractions.get(Interaction.SHOOT).getAsBoolean();
        if (playerSpace) {
            // only allow to fire if they have item to shoot bullets
            if (this.getItemType() == ItemType.BULLET) {
                // check last time player fired bullet
                Instant lastBulletFireTime = this.getBulletFireCooldownStartTime();
                if (!CooldownHandler.isOnCooldown(lastBulletFireTime, this.getBulletFireCooldownLength())) {
                    // SoundPlayer.playSound()

                    // spawn bullet based on player who pressing space
                    GameObject bulletSpawned = bulletLogicManager.spawnBullet(playerObject);
                    this.resetBulletFireCooldown();
                    // bullet fired success result
                    return bulletSpawned;
                }
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
        // possible player movement directions obtained from boolean supplier hashmap, as booleans
        boolean playerDown = playerMoves.get(Direction.DOWN).getAsBoolean();
        boolean playerUp = playerMoves.get(Direction.UP).getAsBoolean();
        boolean playerLeft = playerMoves.get(Direction.LEFT).getAsBoolean();
        boolean playerRight = playerMoves.get(Direction.RIGHT).getAsBoolean();
		  
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
