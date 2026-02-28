package com.liamreal.assets;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// method idea obtained from ChatGPT, instead of texture, sounds will be more dynamic and can be inserted at any point, 
// HOWEVER they will need to be passed in the sound file as a string, of which path can be obtained from AssetLoader class
public class SoundPlayer {
    // static method that any class can use to play sound
    public static void playSound(String path) {
        try {
            File file = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            audioStream.close();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    // different method to loop sound
    public static Clip loopSound(String path) {
        try {
            File file = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            return clip;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        // if failed to play sound return null
        return null;
    }
    // stop sound
    public static void stopSound(Clip clip) {
        if (clip == null) { return; }
        clip.stop();
    }
}