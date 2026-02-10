package util;

import java.util.concurrent.CopyOnWriteArraySet;

import objects.GameObject;

public class Score {
    private int score = 0;
    public Score() {}
    // get score variable
    public int getScore() { return this.score; }
    // add score based on objects hit
    public void incrementScore(CopyOnWriteArraySet<GameObject> objectsHit) {
        score += objectsHit.size();
    }
}
