package com.liamreal.assets;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// similar to texture, but sounds will be more dynamic and can be inserted at any point, 
// HOWEVER they will need to be passed in the sound file as a string, of which path can be obtained from AssetLoader class
public class SoundPlayer {
    // static method that any class can use to play sound
    public static Clip playSound(String path) {
        return playSound(path, false);
    }
    public static Clip playSound(String path, boolean loopSound) {
        try {
            File file = new File(path);
            // get audio stream for audio from input file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            // save clip from stream
            Clip clip = AudioSystem.getClip();
            // open audio stream clip, start playing clip and close audio stream
            clip.open(audioStream);
            // if want to loop sound loop but also reduce volume or music will get annoying
            if (loopSound) {
                // reduced volume for looping clips
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(-10.0f);
                // loop clip continuously
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                // otherwise just start sound
                clip.start();
            }
            audioStream.close();
            return clip;

        } catch (Exception e) { 
            e.printStackTrace();
        }
        return null;
    }
    // different method to loop sound
    public static Clip loopSound(String path) {
        return playSound(path, true);
    }
    // stop sound
    public static void stopSound(Clip clip) {
        if (clip == null) { return; }
        clip.stop();
    }
}