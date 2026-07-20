package com.liamreal.components.graphics;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.awt.image.BufferedImage;

public class ImageLoader {
    // load image and return Spritesheet 
    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not load image: %s", path), e);
        }
    }
    // load image and return Spritesheet 
    public static BufferedImage load(Path path) {
        return ImageLoader.load(path.toString());
    }
}