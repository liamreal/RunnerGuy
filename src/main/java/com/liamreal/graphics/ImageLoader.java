package com.liamreal.graphics;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.awt.image.BufferedImage;

public class ImageLoader {
    private static final Logger logger =
            Logger.getLogger(ImageLoader.class.getName());
    // load image and return Spritesheet 
    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            // if image fails to load, warn and return null
            logger.warning(
                String.format("Missing or invalid image file: %s", path)
            );
            return null;
        }
    }
    // load image and return Spritesheet 
    public static BufferedImage load(Path path) {
        return ImageLoader.load(path.toString());
    }
}