package logic;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import enums.PlayerType;
import objects.GameObject;
import objects.PlayerObject;

// same as other logic managers
public class PlayerLogicManager extends ObjectLogicManager {
    // players list (starts empty)
	private CopyOnWriteArrayList<PlayerLogic> players = new CopyOnWriteArrayList<>();

    // constructor requires number of players
    public PlayerLogicManager(int numPlayers) {
        super();
        // number of players
        switch (numPlayers) {
            case 1:
                players.add(new PlayerLogic(new PlayerObject(PlayerType.ONE)));
                break;
            case 2:
                players.add(new PlayerLogic(new PlayerObject(PlayerType.ONE)));
                players.add(new PlayerLogic(new PlayerObject(PlayerType.TWO)));
                break;
            default:
                // not a valid number of players in cases
                String errorMessage = String.format("Invalid number of players %d, total players are %s --- %d max players!!!", numPlayers, PlayerType.getAllPlayerTypes().toString(), PlayerType.getAllPlayerTypes().size());
                throw new IllegalArgumentException(errorMessage);
        }
    }
    // move every player in list
    public void movePlayers() {
        for (PlayerLogic player : this.players) {
            player.move();
        }
    }

    public CopyOnWriteArraySet<GameObject> collideEnemy(ObjectLogicManager enemyLogicManager) {
        CopyOnWriteArraySet<GameObject> enemiesCollided = new CopyOnWriteArraySet<>();
        for (PlayerLogic player : this.players) {
            enemiesCollided.add(player.collideEnemy(enemyLogicManager));
        }
        // return all enemies collided by players
        return enemiesCollided;
    }

    // this one simply kills, can instead get all collided objects and spawn explosions at them, or summon new objects and make them go backwards
    public CopyOnWriteArraySet<GameObject> killEnemy(EnemyLogicManager enemyLogicManager) {
        return this.collideEnemy(enemyLogicManager);
    }

    public CopyOnWriteArrayList<GameObject> spawnBullet(BulletLogicManager bulletLogicManager) {
        CopyOnWriteArrayList<GameObject> bulletsSpawned = new CopyOnWriteArrayList<>();
        // let each player spawn bullet
        for (PlayerLogic player : this.players) {
            GameObject newBullet = player.spawnBullet(bulletLogicManager);
            // adds bullet if not null (i.e. if one was spawned)
            if (newBullet != null) { bulletsSpawned.add(newBullet); }
        }
        // returns empty list if none spawned
        return bulletsSpawned;
    }

    // obtain list of objects being managed
    public CopyOnWriteArrayList<PlayerLogic> getPlayers() {
        return this.players;
    }
}
