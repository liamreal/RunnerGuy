package com.liamreal.graphics;

public class Animation {
    private int currentAnimationTime;

    public Animation() {
        this.currentAnimationTime = 0;
    }

    public int getAnimationTime() { return this.currentAnimationTime; }
    public void incrementAnimationTime() { this.currentAnimationTime++; }
}
