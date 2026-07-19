package com.liamreal.components.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage image;
    private int spriteWidth;
    private int spriteHeight;

    public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight) {
        if (image == null) { throw new IllegalArgumentException("SpriteSheet image cannot be null"); }
        if (spriteWidth < 1 || spriteHeight < 1) { throw new IllegalArgumentException("SpriteSheet must have positive spriteWidth and spriteHeight"); }
        this.image = image;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    public BufferedImage getImage() { return this.image; }
    public int getSpriteWidth() { return this.spriteWidth; }
    public int getSpriteHeight() { return this.spriteHeight; }
}
