package com.liamreal.assets;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Map;
import com.liamreal.components.graphics.ImageLoader;

public class TextureManager {
    private final AssetBuilder<BufferedImage> assets;
    // build assets on construction
    public TextureManager() {
        assets = new AssetBuilder<>(
            this.getTexturesPath(), 
            ImageLoader::load
        );
    }
    // call to update textures
    public void reload(String newAssetType) { this.assets.reload(newAssetType); }
    // getters
    private Path getTexturesPath() { return AssetConfig.getAssetsPath().resolve("textures"); }
    public Map<String, BufferedImage> getTextureMap() { return this.assets.getAssetMap(); }
    public BufferedImage getTexture(String key) { return this.assets.getAsset(key); }
}
