package com.liamreal.assets;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetConfigTest {
    
    @BeforeEach
    void setUp() {
        // reset static state before every test
        AssetConfig.setAssetType("default");
    }

    @Test
    void getAssetType_defaultReturnsDefault() {
        assertEquals("default", AssetConfig.getAssetType());
    }

    @Test
    void setAssetType_changesAssetType() {
        AssetConfig.setAssetType("new_one");

        assertEquals("new_one", AssetConfig.getAssetType());
    }

    @Test
    void getAssetsPath_returnsCorrectPath() {
        Path expected = Path.of("assets/default");

        assertEquals(expected, AssetConfig.getAssetsPath());
    }

    @Test
    void getAssetsPath_afterChangingAssetType_returnsNewPath() {
        AssetConfig.setAssetType("new_one");

        Path expected = Path.of("assets/new_one");

        assertEquals(expected, AssetConfig.getAssetsPath());
    }
}