package util;

import java.util.concurrent.CopyOnWriteArraySet;

public class Score {
    private int score = 0;

    public Score() {}

    public int getScore() { return this.score; }

    public void incrementScore(CopyOnWriteArraySet<GameObject> objectsHit) {
        score += objectsHit.size();
    }

    
}
