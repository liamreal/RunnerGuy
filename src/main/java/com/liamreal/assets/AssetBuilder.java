package com.liamreal.assets;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// will be responsible for building assets (textures or sounds)
public class AssetBuilder<T> {
    // create asset map with string key and generic T value
    private Map<String, T> assetMap = new HashMap<>();
    private Path path;
    private Function<Path, T> loader;

    // upon initialising, build assets given path
    public AssetBuilder(
            Path path,
            Function<Path, T> loader
    ) {
        this.path = path;
        this.loader = loader;
        this.buildAssetMap();
    }

    // build asset map based on arguments (the Path and the Function to use to load those files)
    private void buildAssetMap() {
        // build path map from parameter path
        Map<String, Path> pathMap = PathBuilder.buildPathMap(this.path);
        // for each asset found in path, load it and add that loaded asset as value to asset map with same string keys as path map
        for (Map.Entry<String, Path> entry : pathMap.entrySet()) {
            T assetLoader = this.loader.apply(entry.getValue());
            // if asset is null, does not exist, so should not be updated and should use "default", as originally initialised in AssetBuilder
            if (assetLoader != null) { 
                // update asset since we know it exists
                this.assetMap.put(entry.getKey(), assetLoader); // apply loader function to path of asset to load it
            }
        }
    }
    // rebuild asset map
    public void reload(String newAssetType) {
        // update asset type and path
        AssetConfig.setAssetType(newAssetType);
        this.setPath(AssetConfig.getAssetsPath());
        // rebuild asset map based on new path
        this.buildAssetMap();
    }
    // obtain full asset map and individual assets
    public Map<String, T> getAssetMap() {
        return this.assetMap;
    }
    public T getAsset(String key) {
        return this.assetMap.get(key);
    }
    private void setPath(Path newPath) { this.path = newPath; }
}
