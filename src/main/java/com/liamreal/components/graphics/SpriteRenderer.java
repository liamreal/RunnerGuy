package com.liamreal.components.graphics;

import com.liamreal.ecs.Component;
import com.liamreal.util.Vector2f;
import java.awt.Graphics;

public class SpriteRenderer implements Component {
    private SpriteSheet spriteSheet;
    public SpriteRenderer(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }
    // draw methods
    public void drawImage(Vector2f position, Graphics g, Animation animation) {
        int spriteWidth = this.spriteSheet.getSpriteWidth();
        int currentPositionInAnimation= (int) ((animation.getAnimationTime()%16/4))*spriteWidth;
        int positionX = (int) position.getX();
        int positionY = (int) position.getY();
        g.drawImage(
            this.spriteSheet.getImage(), 
            positionX, 
            positionY,
            positionX + spriteWidth,
            positionY + this.spriteSheet.getSpriteHeight(),
            currentPositionInAnimation,
            0,
            currentPositionInAnimation + spriteWidth - 1,
            spriteWidth,
            null
        );
    }
}
