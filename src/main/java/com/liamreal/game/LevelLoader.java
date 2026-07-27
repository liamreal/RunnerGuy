package com.liamreal.game;

import com.liamreal.assets.AssetConfig;
import com.liamreal.assets.TextureManager;
import com.liamreal.user.Config;

import java.util.List;

public class LevelLoader {
    private final TextureManager textureManager;
    private final List<String> levels;

    public LevelLoader(TextureManager textureManager) {
        this.textureManager = textureManager;
        this.levels = Config.getInstance().getLevels();
    }

    public void loadNextLevel() {
        // if no levels, exit
        if (!hasLevels()) { return; }
        // remove and use levels until none left
        String currentLevel = levels.removeFirst();
        // update assets (textures, sounds) accordingly with new level
        AssetConfig.setAssetType(currentLevel);
        textureManager.update();
    }

    public boolean hasLevels() { return !levels.isEmpty(); }
}
