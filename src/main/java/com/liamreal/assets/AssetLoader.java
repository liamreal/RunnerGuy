package com.liamreal.assets;

// will be responsible for obtaining the directory whose textures are used
public class AssetLoader {
    private static String gameType = "default";

    public static String getGameType() { return gameType; }
    public static String getAssetsPath() { return String.format("assets/%s", AssetLoader.getGameType()); }

    // to switch between different game types (the idea is to later allow easy switching of assets for recyclable gameplay)
    public static void setGameType(String newGameType) { gameType = newGameType; }

}
