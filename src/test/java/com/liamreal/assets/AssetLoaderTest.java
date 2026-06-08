package com.liamreal.assets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssetLoaderTest {
    @Test
    public void testGameTypeSet() {
        AssetLoader.setGameType("default");
        assertEquals("default", AssetLoader.getGameType());
    }
    @Test
    public void testGameTypePathExists() {
        AssetLoader.setGameType("default");
        assertEquals("assets/default", AssetLoader.getAssetsPath());
    }
}
