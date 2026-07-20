package com.liamreal.assets;

import java.nio.file.Path;


// will be responsible for obtaining the directory whose textures are used
public class AssetSelector {
    private static String gameType = "default";

    public static String getGameType() { return gameType; }
    public static Path getAssetsPath() { return Path.of(String.format("assets/%s", AssetSelector.getGameType())); }
    public static Path getTexturesPath() { return AssetSelector.getAssetsPath().resolve("textures"); }

    // to switch between different game types (the idea is to later allow easy switching of assets for recyclable gameplay)
    public static void setGameType(String newGameType) { gameType = newGameType; }

}
