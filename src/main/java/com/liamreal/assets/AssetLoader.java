package com.liamreal.assets;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.liamreal.components.graphics.ImageLoader;

// make this a singleton
// will be responsible for obtaining the directory whose textures are used
public class AssetLoader {
    // will hold our actual images associated with keys
    private Map<String, BufferedImage> textureMap = new HashMap<>();
    private static final AssetLoader instance = new AssetLoader();

    public static AssetLoader getInstance() {
        return instance;
    }

    // in constructor, will build texture map and then once game is changed will update it via string formatting
    public void buildTextureMap() {
        // build path map of textures
        Map<String, Path> pathMap = PathBuilder.buildPathMap(AssetSelector.getTexturesPath());
        // for each texture found in path, load it and add that loaded image as value to map with same keys
        for (Map.Entry<String, Path> entry : pathMap.entrySet()) {
            this.textureMap.put(entry.getKey(), ImageLoader.load(entry.getValue()));
        }
    }

}
