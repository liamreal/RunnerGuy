package com.liamreal.user;

import java.util.List;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liamreal.display.GameDisplay;

// default values picked from and used in classes, if do not wish to use these are able to overwrite in respective subclass
// need to change to read these values from a file
public class Config {
    private static Config INSTANCE;
    private int numPlayers = 2;
    private int generalMoveSpeed = 1;
    private int bulletMoveSpeed = 3;
    private int playerMoveSpeed = 2;
    private int playerHealth = 3;
    private int maxNumEnemies = 20;
    private int maxNumItems = 1;
    private int resolutionX = GameDisplay.getDisplayX();
    private int resolutionY = GameDisplay.getDisplayY();
    private List<String> levels;
    public static final String filePath = "config.json";

    private Config() {}

    // singleton pattern for config since only need one
    public static Config getInstance() {
        // if instance not yet created
        if (INSTANCE == null) {
            try {
                // use jackson to map .json file to class fields
                ObjectMapper mapper = new ObjectMapper();
                INSTANCE = mapper.readValue(
                    new File("config.json"), Config.class
                );
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(String.format("Failed to read Config file at filepath: %s", Config.filePath), e);
            }
        }
        return INSTANCE;
    }

    // getters for various fields of class
    public int getNumPlayers() { return this.numPlayers; }
    public int getBulletMoveSpeed() { return this.bulletMoveSpeed; }
    public int getGeneralMoveSpeed() { return this.generalMoveSpeed; }
    public int getPlayerMoveSpeed() { return this.playerMoveSpeed; }
    public int getPlayerHealth() { return this.playerHealth; }
    public int getMaxNumEnemies() { return this.maxNumEnemies; }
    public int getMaxNumItems() { return this.maxNumItems; }
    public List<String> getLevels() { return this.levels; }
    public int getResolutionX() { return this.resolutionX; }
    public int getResolutionY() { return this.resolutionY; }

}