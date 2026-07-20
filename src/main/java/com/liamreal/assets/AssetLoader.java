package com.liamreal.assets;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.liamreal.components.graphics.ImageLoader;

// will be responsible for obtaining the directory whose textures are used
public class AssetLoader {

    // make this a singleton

    public Map<String, BufferedImage> buildTextureMap() {
        // will hold our actual images associated with keys
        Map<String, BufferedImage> textureMap = new HashMap<>();
        // build path map of textures
        Map<String, Path> pathMap = PathBuilder.buildPathMap(AssetSelector.getTexturesPath());
        // for each texture found in path, load it and add that loaded image as value to map with same keys
        for (Map.Entry<String, Path> entry : pathMap.entrySet()) {
            textureMap.put(entry.getKey(), ImageLoader.load(entry.getValue()));
        }
        return textureMap;
    }

}
