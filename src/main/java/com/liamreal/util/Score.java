package com.liamreal.util;

import java.util.concurrent.CopyOnWriteArraySet;
import com.liamreal.objects.GameObject;

public class Score {
    private int score = 0;
    public Score() {}
    // get score variable
    public int getScore() { return this.score; }
    public void resetScore() { this.score = 0; }
    // add score based on objects hit
    public void incrementScore(CopyOnWriteArraySet<GameObject> objectsHit) {
        score += objectsHit.size();
    }
}
