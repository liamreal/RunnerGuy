package com.liamreal.game;

import com.liamreal.assets.AssetConfig;
import com.liamreal.assets.TextureManager;
import com.liamreal.user.Config;

import java.util.List;

public class LevelManager {
    private final TextureManager textureManager;
    private final List<String> levels;

    public LevelManager(TextureManager textureManager) {
        this.textureManager = textureManager;
        this.levels = Config.getInstance().getLevels();
    }

    public void loadNextLevel() {
        // if no levels, exit
        if (!hasLevels()) { return; }
        // otherwise get next level
        String currentLevel = levels.get(0);
        // update assets accordingly
        AssetConfig.setAssetType(currentLevel);
        textureManager.update();
    }

    public boolean hasLevels() { return !levels.isEmpty(); }
}
