package com.liamreal.components.graphics;

import com.liamreal.display.GameDisplay;
import com.liamreal.ecs.Component;
import com.liamreal.graphics.SpriteSheet;
import com.liamreal.graphics.Animation;
import com.liamreal.util.Vector2f;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class SpriteRenderer implements Component {
    private SpriteSheet spriteSheet;
    public SpriteRenderer(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }
    // update texture when requested
    public void updateImage(BufferedImage image) { spriteSheet.updateImage(image); } 
    // draw methods
    public void drawAnimatedImage(Vector2f position, Graphics g, Animation animation) {
        int spriteWidth = this.spriteSheet.getSpriteWidth();
        int currentPositionInAnimation= (int) ((animation.getAnimationTime()%16/4))*spriteWidth;
        this.drawImage(position, g, currentPositionInAnimation);
    }
    public void drawStaticImage(Vector2f position, Graphics g){
        this.drawImage(position, g, 0);
    }
    private void drawImage(Vector2f position, Graphics g, int currentPositionInAnimation) {
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
