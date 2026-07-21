package com.liamreal.assets;

import java.nio.file.Path;

// will be responsible for building assets (textures or sounds)
public class AssetConfig {
    // type of asset currently active
    private static String assetType = "default"; // KEEP AS "default", should only be switched in game loop
    public static void setAssetType(String newAssetType) { AssetConfig.assetType = newAssetType; }
    public static String getAssetType() { return AssetConfig.assetType; }
    public static Path getAssetsPath() { return Path.of(String.format("assets/%s", AssetConfig.getAssetType())); }
}
