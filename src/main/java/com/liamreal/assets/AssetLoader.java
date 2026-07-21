package com.liamreal.assets;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// make this a singleton
// will be responsible for obtaining the directory whose textures are used
public class AssetLoader {
    // build map based on arguments (the Path and the Function to use to load those files)
    public <T> Map<String, T> buildAssetMap(Path path, Function<Path, T> loader) {
        // create asset map with string key and generic T value
        Map<String, T> assetMap = new HashMap<>();
        // build path map from parameter path
        Map<String, Path> pathMap = PathBuilder.buildPathMap(path);
        // for each asset found in path, load it and add that loaded asset as value to asset map with same string keys as path map
        for (Map.Entry<String, Path> entry : pathMap.entrySet()) {
            assetMap.put(entry.getKey(), loader.apply(entry.getValue())); // apply loader function to path of asset to load it
        }
        return assetMap;
    }
}
