package com.liamreal.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.liamreal.user.Config;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.liamreal.assets.AssetConfig;
import com.liamreal.assets.TextureManager;

public class LevelLoaderTest {
    private MockedStatic<Config> mockedConfig;
    private Config mockConfig;

    @BeforeEach
    public void setup() {
        mockConfig = mock(Config.class);

        mockedConfig = mockStatic(Config.class);

        mockedConfig.when(Config::getInstance)
                    .thenReturn(mockConfig);
    }

    @AfterEach
    public void tearDown() {
        mockedConfig.close();
    }

    // helper function to create list levels
    private LevelLoader createLoaderWithLevels(List<String> levels) {

        when(mockConfig.getLevels())
                .thenReturn(levels);

        TextureManager textureManager = mock(TextureManager.class);

        return new LevelLoader(textureManager);
    }
    
    @Test
    public void testHasLevels() {
        List<String> levels = new ArrayList<>();
        levels.add("level1");
        LevelLoader loader = this.createLoaderWithLevels(levels);
        assertTrue(loader.hasLevels());
    }

    @Test
    public void testHasNoLevels() {
        List<String> levels = new ArrayList<>();
        LevelLoader loader = this.createLoaderWithLevels(levels);
        assertFalse(loader.hasLevels());
    }

    @Test
    public void testLoadNextLevelRemovesFirstLevel() {
        // this will be updated by LevelLoader.loadNextLevel(), but need to make sure updated correctly
        AssetConfig.setAssetType("test");
        List<String> levels = new ArrayList<>();
        levels.add("level1");
        levels.add("level2");
        LevelLoader loader = this.createLoaderWithLevels(levels);        
        
        loader.loadNextLevel();
        assertEquals("level1", AssetConfig.getAssetType());
        assertTrue(loader.hasLevels()); // should still have a level; "level2"
        
        loader.loadNextLevel();
        assertEquals("level2", AssetConfig.getAssetType());
        assertFalse(loader.hasLevels()); // should still have a level; "level2"
    }

    @Test
    public void testLoadAllLevels() {
        List<String> levels = new ArrayList<>();
        levels.add("level1");
        levels.add("level2");
        levels.add("level3");
        LevelLoader loader = this.createLoaderWithLevels(levels);

        while (loader.hasLevels()) {
            loader.loadNextLevel();
        }

        // should have exhausted all levels
        assertFalse(loader.hasLevels());
    }

    @Test
    public void testLoadNoLevels() {
        // will not be updated since no levels
        AssetConfig.setAssetType("test");
        List<String> levels = new ArrayList<>();

        LevelLoader loader = this.createLoaderWithLevels(levels);

        assertFalse(loader.hasLevels());
        assertEquals("test", AssetConfig.getAssetType());

        // should not load anything when no levels exist
        loader.loadNextLevel();

        assertFalse(loader.hasLevels());
        assertEquals("test", AssetConfig.getAssetType());
    }
}