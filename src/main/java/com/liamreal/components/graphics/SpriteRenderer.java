package com.liamreal.components.graphics;

import com.liamreal.ecs.Component;
import com.liamreal.graphics.SpriteSheet;
import com.liamreal.graphics.Animation;
import com.liamreal.util.Vector2f;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class SpriteRenderer implements Component {
    private SpriteSheet spriteSheet;
    private final boolean isAnimated;
    public SpriteRenderer(SpriteSheet spriteSheet, boolean isAnimated) {
        this.spriteSheet = spriteSheet;
        this.isAnimated = isAnimated;
    }
    // update texture when requested
    public void updateImage(BufferedImage image) { spriteSheet.updateImage(image); } 
    // get current animation, if not isAnimated returns 0
    private int getCurrentAnimationPosition(Animation animation) {
        if (this.isAnimated) {
            int spriteWidth = this.spriteSheet.getSpriteWidth();
            return (int) ((animation.getAnimationTime()%16/4))*spriteWidth;
        }
        return 0;
    }
    // draw methods
    public void draw(Vector2f position, Graphics g, Animation animation) {
        int currentPositionInAnimation = this.getCurrentAnimationPosition(animation);
        this.drawFrame(position, g, currentPositionInAnimation);
    }
    private void drawFrame(Vector2f position, Graphics g, int currentPositionInAnimation) {
        int spriteWidth = this.spriteSheet.getSpriteWidth();
        int spriteHeight = this.spriteSheet.getSpriteHeight();
        int positionX = (int) position.getX();
        int positionY = (int) position.getY();
        g.drawImage(
            this.spriteSheet.getImage(), 
            positionX, 
            positionY,
            positionX + spriteWidth,
            positionY + spriteHeight,
            currentPositionInAnimation,
            0,
            currentPositionInAnimation + spriteWidth - 1,
            spriteHeight,
            null
        );
    }
}
