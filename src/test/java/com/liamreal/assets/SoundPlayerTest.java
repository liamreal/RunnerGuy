package com.liamreal.assets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SoundPlayerTest {
    @Test
    public void testPlaySoundNull() {
        assertNull(SoundPlayer.playSound(""));
    }
    @Test
    public void testPlaySoundExisting() {
        assertNotNull(SoundPlayer.playSound("src/test/resources/test.wav"));
    }
}
