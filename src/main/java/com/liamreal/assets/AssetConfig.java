package com.liamreal.assets;

import java.nio.file.Path;

// will be responsible for building assets (textures or sounds)
public class AssetConfig {
    // type of asset currently active (KEEP THIS "default" AS DEFAULT VALUE -- ensures there are fallback textures)
    private static String assetType = "default";
    public static void setAssetType(String newAssetType) { AssetConfig.assetType = newAssetType; }
    public static String getAssetType() { return AssetConfig.assetType; }
    public static Path getAssetsPath() { return Path.of(String.format("assets/%s", AssetConfig.getAssetType())); }
}
